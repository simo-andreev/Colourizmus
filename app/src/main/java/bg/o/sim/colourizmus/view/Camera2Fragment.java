package bg.o.sim.colourizmus.view;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.lang.annotation.Retention;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import bg.o.sim.colourizmus.R;
import bg.o.sim.colourizmus.databinding.CardColourListPreviewBinding;
import bg.o.sim.colourizmus.model.CustomColour;
import bg.o.sim.colourizmus.utils.Util;

import static android.Manifest.permission.CAMERA;
import static android.content.Context.CAMERA_SERVICE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static java.lang.annotation.RetentionPolicy.CLASS;

@RequiresApi(api = Build.VERSION_CODES.M)
public class Camera2Fragment extends Fragment {

    private ImageView mColourPreview;

    /** Camera states: */
    @Retention(CLASS)
    @IntDef({STATE_PREVIEW, STATE_WAITING_LOCK, STATE_WAITING_PRECAPTURE, STATE_WAITING_NON_PRECAPTURE, STATE_PICTURE_TAKEN})
    @interface CamState {
    }

    private static final int STATE_PREVIEW = 0;
    private static final int STATE_WAITING_LOCK = 1;
    private static final int STATE_WAITING_PRECAPTURE = 2;
    private static final int STATE_WAITING_NON_PRECAPTURE = 3;
    private static final int STATE_PICTURE_TAKEN = 4;

    /** Max preview dimensions that guaranteed by Camera2 API */
    private static final int MAX_PREVIEW_HEIGHT = 1080;
    private static final int MAX_PREVIEW_WIDTH = 1920;

    private String mCameraId;
    private int mCameraState;
    private boolean mFlashAvailable;

    private ImageButton mCaptureButton;

    private TextureView mTextureView;
    private Surface mPreviewSurface;
    private TextureView.SurfaceTextureListener mSurfaceTextureCallback;

    private Handler mHandler;
    private HandlerThread mHandlerThread;

    private CameraDevice mCamera;
    private CameraDevice.StateCallback mCameraStateCallback;

    private CaptureRequest mCaptureRequest;
    private CaptureRequest.Builder mCaptureRequestBuilder;

    private CameraCaptureSession mCaptureSession;
    private CameraCaptureSession.StateCallback mSessionStateCallback;
    private CameraCaptureSession.CaptureCallback mSessionCaptureCallback;

    private Size mPreviewSize;


    public Camera2Fragment() {
    }

    public static Camera2Fragment newInstance() {
        return new Camera2Fragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_camera2, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.mCaptureButton = view.findViewById(R.id.fragment_camera2_capure_button);
        this.mTextureView = view.findViewById(R.id.fragment_camera2_preview_surface);
        this.mColourPreview = view.findViewById(R.id.fragment_camera2_single_colour_preview);

        instantiateCallbacks();
    }


    @Override
    public void onResume() {
        super.onResume();
        startBackgroundThread();

        if (mTextureView.isAvailable()) {
            openCamera(mTextureView.getWidth(), mTextureView.getHeight());
        } else {
            mTextureView.setSurfaceTextureListener(mSurfaceTextureCallback);
        }
    }

    @Override
    public void onPause() {
        closeCamera();
        closeBackgroundThread();
        super.onPause();
    }


    private void initializeCamera(int width, int height) {
    }


    private void openCamera(int width, int height) {
        if (getActivity().checkCallingOrSelfPermission(CAMERA) != PERMISSION_GRANTED) {
            requestCameraPermission();
            return;
        }

        setUpCameraOutputs(width, height);
//        configureTransform(width, height);

        CameraManager manager = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);

        try {
            manager.openCamera(mCameraId, mCameraStateCallback, mHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }

    private void setUpCameraOutputs(int width, int height) {

        CameraManager manager = (CameraManager) getActivity().getSystemService(CAMERA_SERVICE);

        try {
            for (String cameraId : manager.getCameraIdList()) {
                CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);

                Integer facing = characteristics.get(CameraCharacteristics.LENS_FACING);
                if (facing != null && facing == CameraCharacteristics.LENS_FACING_FRONT) {
                    continue;
                }

                StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);


                Point displaySize = new Point();
                getActivity().getWindowManager().getDefaultDisplay().getSize(displaySize);

                int maxPreviewWidth = displaySize.x > MAX_PREVIEW_WIDTH ? MAX_PREVIEW_WIDTH : displaySize.x;
                int maxPreviewHeight = displaySize.y > MAX_PREVIEW_HEIGHT ? MAX_PREVIEW_HEIGHT : displaySize.y;

                mPreviewSize = chooseOptimalSize(map.getOutputSizes(SurfaceTexture.class), maxPreviewWidth, maxPreviewHeight);


                // Check if the flash is supported.
                Boolean available = characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                mFlashAvailable = available == null ? false : available;

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

    private Size chooseOptimalSize(Size[] outputSizes, int maxWidth, int maxHeight) {

        List<Size> bigEnough = new ArrayList<>();
        List<Size> notBigEnough = new ArrayList<>();

        int viewWidth = mTextureView.getWidth();
        int viewHeight = mTextureView.getHeight();

        for (Size option : outputSizes) {
            if (option.getWidth() <= maxWidth && option.getHeight() <= maxHeight) {
                if (option.getWidth() >= viewWidth && option.getHeight() >= viewHeight) {
                    bigEnough.add(option);
                } else {
                    notBigEnough.add(option);
                }
            }
        }

        // Pick the smallest of those big enough. If there is no one big enough, pick the largest of those not big enough.
        if (bigEnough.size() > 0) {
            return Collections.min(bigEnough, new CameraFragment.SizeAreaComparator());
        } else if (notBigEnough.size() > 0) {
            return Collections.max(notBigEnough, new CameraFragment.SizeAreaComparator());
        } else {
            return outputSizes[0];
        }
    }

    private void closeCamera(){
        // TODO: 30.10.17
    }

    private void createPreviewSession() {
        try {
            mPreviewSurface = new Surface(mTextureView.getSurfaceTexture());

            mCaptureRequestBuilder = mCamera.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);

            List<Surface> targets = new ArrayList<>(1);
            targets.add(mPreviewSurface);

            mCamera.createCaptureSession(targets, mSessionStateCallback, mHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }


    private void startBackgroundThread(){
        this.mHandlerThread = new HandlerThread("cameraFragment.BG.THREAD");
        this.mHandlerThread.start();
        this.mHandler = new Handler(mHandlerThread.getLooper());
    }
    private void closeBackgroundThread() {
        try {
            this.mHandlerThread.quitSafely();
            this.mHandlerThread.join();
            this.mHandlerThread = null;
            this.mHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void requestCameraPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            new ConfirmationDialog().show(getChildFragmentManager(), this.getClass().getName());
        } else {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, Util.REQUEST_CODE_CAMERA_PERMISSION);
        }
    }


    private void instantiateCallbacks() {
        this.mCameraStateCallback = new CameraDevice.StateCallback() {
            @Override
            public void onOpened(@NonNull CameraDevice camera) {
                mCamera = camera;
                createPreviewSession();
            }

            @Override
            public void onDisconnected(@NonNull CameraDevice camera) {
                camera.close();
                mCamera = null;
            }

            @Override
            public void onError(@NonNull CameraDevice camera, int error) {
                camera.close();
                mCamera = null;
                getActivity().finish();
            }
        };

        this.mSessionStateCallback = new CameraCaptureSession.StateCallback() {
            @Override
            public void onConfigured(@NonNull CameraCaptureSession session) {
                try {
                    if (mCamera == null) throw  new Error();
                    mCaptureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                    mCaptureRequestBuilder.addTarget(mPreviewSurface);
                    mCaptureRequest = mCaptureRequestBuilder.build();
                    session.setRepeatingRequest(mCaptureRequest, mSessionCaptureCallback, mHandler);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onConfigureFailed(@NonNull CameraCaptureSession session) {

            }
        };

        this.mSessionCaptureCallback = new CameraCaptureSession.CaptureCallback() {
            private void process(CaptureResult partialResult) {
            }

            @Override
            public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
                Log.i("TAAAG", "CAPTURE CALLBACK ACTIVE!");
            }

        };

        this.mSurfaceTextureCallback = new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                openCamera(width, height);
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
                // TODO: 30.10.17 configueSizeChange();
            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                return true;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {
                // TODO: 30.10.17 grab pallete;
                Log.i("TAAAG", "SURFACE UPDATED!");
                displayPalette(mTextureView.getBitmap());
            }
        };
    }


    private void displayPalette(Bitmap bmp) {
        Palette.Builder paletteBuilder = new Palette.Builder(bmp);
        paletteBuilder.maximumColorCount(6);
        Palette palette = paletteBuilder.generate();

        mColourPreview.setBackgroundColor(palette.getDominantColor(Color.RED));

//        CustomColour[] coloursInPlay = {
//                new CustomColour("does NOT matter", palette.getDominantColor(Color.BLUE)),
//                new CustomColour("does NOT matter", palette.getMutedColor(Color.BLUE)),
//                new CustomColour("does NOT matter", palette.getVibrantColor(Color.BLUE)),
//                new CustomColour("does NOT matter", palette.getDarkMutedColor(Color.BLUE)),
//                new CustomColour("does NOT matter", palette.getDominantColor(Color.BLUE)),
//                new CustomColour("does NOT matter", palette.getDominantColor(Color.BLUE))
//        };



//        CardColourListPreviewBinding.bind(mPalettePreview).setColourList(Arrays.asList(coloursInPlay));
    }



    /** Shows an error message dialog. */
    public static class ErrorDialog extends DialogFragment {

        private static final String ARG_MESSAGE = "message";

        public static ErrorDialog newInstance(String message) {
            ErrorDialog dialog = new ErrorDialog();
            Bundle args = new Bundle();
            args.putString(ARG_MESSAGE, message);
            dialog.setArguments(args);
            return dialog;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Activity activity = getActivity();
            return new AlertDialog.Builder(activity)
                    .setMessage(getArguments().getString(ARG_MESSAGE))
                    .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> activity.finish())
                    .create();
        }

    }

    /** Shows OK/Cancel confirmation dialog about camera permission. */
    public static class ConfirmationDialog extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Fragment parent = getParentFragment();
            return new AlertDialog.Builder(getActivity())
                    .setMessage("Gimmi premissinoz plz!")
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> parent.requestPermissions(new String[]{Manifest.permission.CAMERA},
                            Util.REQUEST_CODE_CAMERA_PERMISSION))
                    .setNegativeButton(android.R.string.cancel,
                            (dialog, which) -> {
                                Activity activity = parent.getActivity();
                                if (activity != null) {
                                    activity.finish();
                                }
                            })
                    .create();
        }
    }

}