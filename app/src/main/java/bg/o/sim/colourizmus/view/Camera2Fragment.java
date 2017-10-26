package bg.o.sim.colourizmus.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.ImageReader;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import bg.o.sim.colourizmus.R;
import bg.o.sim.colourizmus.utils.ImageSaver;
import bg.o.sim.colourizmus.utils.Util;

@RequiresApi(api = Build.VERSION_CODES.M)
public class Camera2Fragment extends Fragment {

    private static final String NAME_THREAD = "HandlerThread";

    /** Id of the desired CameraDevice */
    private String mCamId;

    /** View upon which thine Digitalized Camera Obscura contraption shall cast its devilish magic */
    private TextureView mTextureView;

    /** Directory whence thouth Images shalt be save-a-ted */
    private File mOutputDirectory;

    /** A reference to the opened {@link CameraDevice}. */
    private CameraDevice mCamera;

    /** Captures images from the {@link Camera2Fragment#mCamera}
     *  or reprocesses images previously captured in the session. */
    private CameraCaptureSession mSession;

    /** Harbours direct access to images rendered into a {@link android.view.Surface} */
    private ImageReader mImageReader;

    /** Handles lifeCycle events for a {@link TextureView} instance*/
    private final TextureView.SurfaceTextureListener mTextureListener= new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            openCamera(width, height);
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        }

        /**
         * Invoked when the specified {@link SurfaceTexture} is about to be destroyed.
         * If returns true, no rendering should happen inside the surface texture after this method
         * is invoked. If returns false, the client needs to call {@link SurfaceTexture#release()}.
         * Most applications should return true.
         *
         * @param surface The surface about to be destroyed
         */
        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return true;
        }

        /**
         * Invoked when the specified {@link SurfaceTexture} is updated through
         * {@link SurfaceTexture#updateTexImage()}.
         *
         * @param surface The surface just updated
         */
        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
            // TODO: 26.10.17 - idk what to do wi'ch 'ya
        }
    };

    /** Called uppon, whence {@link CameraDevice} changes its state. */
    private final CameraDevice.StateCallback mStateCallback = new CameraDevice.StateCallback() {

        @Override
        public void onOpened(@NonNull CameraDevice cameraDevice) {
            mCamera = cameraDevice;
            createCameraPreviewSession();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
            cameraDevice.close();
            mCamera = null;
        }

        @Override
        public void onError(@NonNull CameraDevice cameraDevice, int error) {
            cameraDevice.close();
            mCamera = null;
            Util.toastLong(getActivity(), getString(R.string.err_cam_error));
            getActivity().finish();
        }
    };

    /** A {@link Handler} to run tasks outside UI thread */
    private Handler mHandler;

    /** A {@link HandlerThread} to go with the {@link Camera2Fragment#mHandler} */
    private HandlerThread mHandlerThread;


    public Camera2Fragment(){}

    public static Camera2Fragment newInstance() {
        return new Camera2Fragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_camera, container, false);
        mTextureView = root.findViewById(R.id.texture);

        try {
            mOutputDirectory = Util.getPublicImageDir();
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: 26.10.17 handle in a more user-friendly, non-chrash-y way.
        }

        return root;
    }



    @Override
    public void onResume() {
        super.onResume();
        startHandlerThread();

        // When the screen is turned off and turned back on, the SurfaceTexture is already available,
        // and "onSurfaceTextureAvailable" will not be called.
        // In that case, we can open a camera and start preview from here
        // (otherwise, we wait until the surface is ready in the SurfaceTextureListener).

        //check if the SurfaceTexture associated with mTextureView is available for rendering.
        if (mTextureView.isAvailable()){
            // if not locked yet -> manually initiate callback
            mTextureListener.onSurfaceTextureAvailable(
                    mTextureView.getSurfaceTexture(),
                    mTextureView.getWidth(),
                    mTextureView.getHeight()
            );
        } else {
            // else -> register for a callback. Don't worry, I'm in no hurry. No, really ... take your time... (ಠ_ಠ)
            mTextureView.setSurfaceTextureListener(mTextureListener);
        }
    }

    @Override
    public void onPause() {
        closeHandlerThread();
        closeCamera();
        super.onPause();
    }

    //============================================================================================//
    // START CAMERA STUFFSES                                                                      //
    //============================================================================================//

    /** Opens the camera specified by {@link CameraFragment#mCameraId}.
     * @param width
     * @param height*/
    @SuppressLint("MissingPermission") // permission is checked via Util method, but openCamera still is unhappy...
    private void openCamera(int width, int height) {
        if (!Util.havePermissions(getContext(), Manifest.permission.CAMERA)) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);

            // Shouldn't block UI thread while waiting for permission
            // so return and depend on lifecycle callbacks to finish action
            return;
        }

        setUpCameraOutputs(width, height);
        configureTransform(width, height);

        try {
            CameraManager camManager = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);
            camManager.openCamera(mCamId, mStateCallback, mHandler);
        } catch (CameraAccessException e) {
            // TODO: 26.10.17
            e.printStackTrace();
        }
    }

    private void closeCamera() {
        if (mSession != null) {
            mSession.close();
            mSession = null;
        }

        if (mCamera != null) {
            mCamera.close();
            mCamera = null;
        }

        if (mImageReader != null) {
            mImageReader.close();
            mImageReader = null;
        }
    }

    /**
     * Sets up variables related to camera.
     *
     * @param width  The width of available size for camera preview
     * @param height The height of available size for camera preview
     */
    private void prepareOutput(int width, int height) {
        CameraManager camManager = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);
        try {
            for (String cameraId : camManager.getCameraIdList()) {
                CameraCharacteristics characteristics = camManager.getCameraCharacteristics(cameraId);


                // TODO: 26.10.17 - compare to sample impl. Improve this accordingly.
                StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);

                if (characteristics.get(CameraCharacteristics.LENS_FACING) != CameraCharacteristics.LENS_FACING_BACK || map == null) {
                    continue;
                }

                // For still image captures, use the largest available size.
                Size largest = Collections.max(Arrays.asList(map.getOutputSizes(ImageFormat.JPEG)), (o1, o2) -> {
                    // Cast to long to ensure the multiplications won't overflow
                    long size1 = o1.getWidth() * o1.getHeight();
                    long size2 = o2.getWidth() * o2.getHeight();
                    return Long.signum(size1 - size2); // TODO: 26.10.17 remember the signum func. will be useful!!!
                });

                mImageReader = ImageReader.newInstance(largest.getWidth(), largest.getHeight(), ImageFormat.JPEG, 1);
                mImageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
                    @Override
                    public void onImageAvailable(ImageReader reader) {
                        File imageFile = new File(mOutputDirectory, "Colourizmus-" + System.currentTimeMillis());
                        mHandler.post(new ImageSaver(reader.acquireNextImage(), imageFile));
                    }
                }, mHandler);

                // Find out if we need to swap dimension to get the preview size relative to sensor
                // coordinate.
                int displayRotation = activity.getWindowManager().getDefaultDisplay().getRotation();
                //noinspection ConstantConditions
                mSensorOrientation = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
                boolean swappedDimensions = false;
                switch (displayRotation) {
                    case Surface.ROTATION_0:
                    case Surface.ROTATION_180:
                        if (mSensorOrientation == 90 || mSensorOrientation == 270)
                            swappedDimensions = true;
                        break;
                    case Surface.ROTATION_90:
                    case Surface.ROTATION_270:
                        if (mSensorOrientation == 0 || mSensorOrientation == 180)
                            swappedDimensions = true;
                        break;
                }

                Point displaySize = new Point();
                activity.getWindowManager().getDefaultDisplay().getSize(displaySize);
                int rotatedPreviewWidth = width;
                int rotatedPreviewHeight = height;
                int maxPreviewWidth = displaySize.x;
                int maxPreviewHeight = displaySize.y;

                if (swappedDimensions) {
                    rotatedPreviewWidth = height;
                    rotatedPreviewHeight = width;
                    maxPreviewWidth = displaySize.y;
                    maxPreviewHeight = displaySize.x;
                }

                if (maxPreviewWidth > MAX_PREVIEW_WIDTH) {
                    maxPreviewWidth = MAX_PREVIEW_WIDTH;
                }

                if (maxPreviewHeight > MAX_PREVIEW_HEIGHT) {
                    maxPreviewHeight = MAX_PREVIEW_HEIGHT;
                }

                // Danger, W.R.! Attempting to use too large a preview size could  exceed the camera
                // bus' bandwidth limitation, resulting in gorgeous previews but the storage of
                // garbage capture data.
                mPreviewSize = chooseOptimalSize(map.getOutputSizes(SurfaceTexture.class), rotatedPreviewWidth, rotatedPreviewHeight, maxPreviewWidth, maxPreviewHeight, largest);

                // We fit the aspect ratio of TextureView to the size of preview we picked.
                int orientation = getResources().getConfiguration().orientation;
                // TODO: 19.10.17 custon view class needed. Apply at www.ICantBeBothered.fuck.off
//                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
//                    mTextureView.setAspectRatio(
//                            mPreviewSize.getWidth(), mPreviewSize.getHeight());
//                } else {
//                    mTextureView.setAspectRatio(
//                            mPreviewSize.getHeight(), mPreviewSize.getWidth());
//                }

                // Check if the flash is supported.
                Boolean available = characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                mFlashSupported = available == null ? false : available;

                mCameraId = cameraId;
                return;
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            // Currently an NPE is thrown when the Camera2API is used but not supported on the
            // device this code runs.
            ErrorDialog.newInstance("To err is camera. To r@ge is divine!").show(getChildFragmentManager(), this.getClass().getName());
        }
    }

    //============================================================================================//
    // END CAMERA STUFFSES                                                                        //
    //============================================================================================//



    //============================================================================================//
    // START THREAD STUFFSES                                                                      //
    //============================================================================================//

    /**
     * Instantiate the {@link Camera2Fragment#mHandlerThread} along with the {@link Camera2Fragment#mHandler}
     */
    private void startHandlerThread(){
        this.mHandlerThread = new HandlerThread(NAME_THREAD);
        this.mHandlerThread.start();
        this.mHandler = new Handler(mHandlerThread.getLooper());
    }

    /**
     * Safely end the {@link Camera2Fragment#mHandlerThread} and nullify it along with the {@link Camera2Fragment#mHandler}
     */
    private void closeHandlerThread() {
        this.mHandlerThread.quitSafely();
        try {
            // wait for the the thread to quit and nullify
            this.mHandlerThread.join();
            this.mHandlerThread = null;
            this.mHandler = null;
        } catch (InterruptedException e) {
            // TODO: 26.10.17  ... no ... just ... NO.
            throw new Error(e.getMessage());
        }
    }

    //============================================================================================//
    // END THREAD STUFFSES                                                                        //
    //============================================================================================//

}
