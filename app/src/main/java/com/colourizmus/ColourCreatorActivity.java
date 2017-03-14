package com.colourizmus;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;

public class ColourCreatorActivity extends AppCompatActivity {

    SeekBar red, green, blue, alpha;
    View colorBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colour_creator);

        colorBox = findViewById(R.id.colorBox);

        SeekBar.OnSeekBarChangeListener chanelListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                colorBox.setBackgroundColor(Color.argb(alpha.getProgress(), red.getProgress(), green.getProgress(), blue.getProgress()));
            }
        };


        (alpha = (SeekBar) findViewById(R.id.alphaSlider)).setOnSeekBarChangeListener(chanelListener);
        (red = (SeekBar) findViewById(R.id.redSlider)).setOnSeekBarChangeListener(chanelListener);
        (green = (SeekBar) findViewById(R.id.greenSlider)).setOnSeekBarChangeListener(chanelListener);
        (blue = (SeekBar) findViewById(R.id.blueSlider)).setOnSeekBarChangeListener(chanelListener);

        alpha.setProgress(255);

        alpha.setMax(255);
        red.setMax(255);
        green.setMax(255);
        blue.setMax(255);

    }
}
