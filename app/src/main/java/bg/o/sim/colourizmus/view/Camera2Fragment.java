package bg.o.sim.colourizmus.view;

import android.app.Fragment;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.lang.annotation.Retention;

import bg.o.sim.colourizmus.R;

import static java.lang.annotation.RetentionPolicy.CLASS;

@RequiresApi(api = Build.VERSION_CODES.M)
public class Camera2Fragment extends Fragment {

    /** Camera states: */
    @Retention(CLASS)
    @IntDef({STATE_PREVIEW, STATE_WAITING_LOCK, STATE_WAITING_PRECAPTURE, STATE_WAITING_NON_PRECAPTURE, STATE_PICTURE_TAKEN})
    @interface CamState{}
    private static final int STATE_PREVIEW = 0;
    private static final int STATE_WAITING_LOCK = 1;
    private static final int STATE_WAITING_PRECAPTURE = 2;
    private static final int STATE_WAITING_NON_PRECAPTURE = 3;
    private static final int STATE_PICTURE_TAKEN = 4;


    private ImageButton mCaptureButton;

    private TextureView mTextureView;
    private TextureView.SurfaceTextureListener mSurfaceTextureCallback;

    private Handler mHandler;
    private HandlerThread mHandlerThread;

    private CameraDevice mCamera;
    private CameraDevice.StateCallback mCameraStateCallback;

    private CameraCaptureSession mCaptureSession;
    private CameraCaptureSession.StateCallback mSessionStateCallback;
    private CameraCaptureSession.CaptureCallback mSessionCaptureCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_camera2, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.mCaptureButton = view.findViewById(R.id.fragment_camera2_capure_button);
        this.mTextureView = view.findViewById(R.id.fragment_camera2_preview_surface);

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
    public void onStop() {
        closeCamera();
        startBackgroundThread();
        super.onStop();
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
    
    
    private void openCamera(int width, int height){
        // TODO: 30.10.17  
    }
    private void closeCamera(){
        // TODO: 30.10.17
    }


    // TODO: 30.10.17
    private void instantiateCallbacks() {
        this.mCameraStateCallback = new CameraDevice.StateCallback() {
            @Override
            public void onOpened(@NonNull CameraDevice camera) {

            }

            @Override
            public void onDisconnected(@NonNull CameraDevice camera) {

            }

            @Override
            public void onError(@NonNull CameraDevice camera, int error) {

            }
        };

        this.mSessionStateCallback = new CameraCaptureSession.StateCallback() {
            @Override
            public void onConfigured(@NonNull CameraCaptureSession session) {

            }

            @Override
            public void onConfigureFailed(@NonNull CameraCaptureSession session) {

            }
        };

        this.mSessionCaptureCallback = new CameraCaptureSession.CaptureCallback() {
        };

        this.mSurfaceTextureCallback = new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {

            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {

            }
        };
    }
}