package bg.o.sim.colourizmus.view;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import bg.o.sim.colourizmus.R;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Camera2Fragment extends Fragment {

    private TextureView mTextureView;
    private ImageButton mCaptureButton;

    public Camera2Fragment(){}

    public static Camera2Fragment newInstance() {
        return new Camera2Fragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_camera2, container, false);
        this.mTextureView = rootView.findViewById(R.id.fragment_camera2_preview_surface);
        this.mCaptureButton = rootView.findViewById(R.id.fragment_camera2_capure_button);
        return rootView;
    }


}
