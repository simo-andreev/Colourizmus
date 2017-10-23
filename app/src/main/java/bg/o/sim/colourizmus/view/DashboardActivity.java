package bg.o.sim.colourizmus.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;

import bg.o.sim.colourizmus.R;

public class DashboardActivity extends Activity{

    private ImageButton mColourCreationButton, mGalleryButton;

    private final View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.dashboard_button_colour_creation:
                    ColourCreationActivity.start(DashboardActivity.this);
                    break;
                case R.id.dashboard_button_gallery:
                    ColourListActivity.start(DashboardActivity.this);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mColourCreationButton = findViewById(R.id.dashboard_button_colour_creation);
        mGalleryButton = findViewById(R.id.dashboard_button_gallery);

        mColourCreationButton.setOnClickListener(mClickListener);
        mGalleryButton.setOnClickListener(mClickListener);
    }
}
