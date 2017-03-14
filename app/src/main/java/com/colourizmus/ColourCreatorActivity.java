package com.colourizmus;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class ColourCreatorActivity extends AppCompatActivity {

    SeekBar red, green, blue, alpha;
    View colorBox;
    TextView hexadec;
    int a;
    int r;
    int g;
    int b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colour_creator);

        colorBox = findViewById(R.id.colorBox);
        hexadec = (TextView) findViewById(R.id.hexadecValue);

        SeekBar.OnSeekBarChangeListener chanelListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                a = alpha.getProgress();
                r = red.getProgress();
                g = green.getProgress();
                b = blue.getProgress();

                colorBox.setBackgroundColor(Color.argb(a, r, g, b));

                StringBuilder sb = new StringBuilder("#");

                sb.append(Integer.toHexString(a));
                sb.append(Integer.toHexString(r));
                sb.append(Integer.toHexString(g));
                sb.append(Integer.toHexString(b));

                sb.append(" | ");

                sb.append("a:"+a);
                sb.append(" r:"+r);
                sb.append(" g:"+g);
                sb.append(" b:"+b);


                hexadec.setText(sb);
            }
        };


        (alpha = (SeekBar) findViewById(R.id.alphaSlider)).setOnSeekBarChangeListener(chanelListener);
        (red = (SeekBar) findViewById(R.id.redSlider)).setOnSeekBarChangeListener(chanelListener);
        (green = (SeekBar) findViewById(R.id.greenSlider)).setOnSeekBarChangeListener(chanelListener);
        (blue = (SeekBar) findViewById(R.id.blueSlider)).setOnSeekBarChangeListener(chanelListener);

        alpha.setMax(255);
        red.setMax(255);
        green.setMax(255);
        blue.setMax(255);

        alpha.setProgress(255);

    }
}
