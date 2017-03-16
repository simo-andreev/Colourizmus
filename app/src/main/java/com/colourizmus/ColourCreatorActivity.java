package com.colourizmus;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class ColourCreatorActivity extends AppCompatActivity {

    SeekBar red, green, blue;
    View colorBox;
    TextView hexadec;

    int r;
    int g;
    int b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_colour_creator);

        Toolbar tb = (Toolbar) findViewById(R.id.custom_action_bar);
        setSupportActionBar(tb);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.mipmap.ic_launcher);

        colorBox = findViewById(R.id.colorBox);
        hexadec = (TextView) findViewById(R.id.hexadecValue);



        SeekBar.OnSeekBarChangeListener chanelListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                r = red.getProgress();
                g = green.getProgress();
                b = blue.getProgress();

                colorBox.setBackgroundColor(Color.rgb(r, g, b));

                StringBuilder sb = new StringBuilder("#");

                sb.append(Integer.toHexString(r));
                sb.append(Integer.toHexString(g));
                sb.append(Integer.toHexString(b));

                sb.append(" | ");

                sb.append(" r:"+r);
                sb.append(" g:"+g);
                sb.append(" b:"+b);


                hexadec.setText(sb);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };


        (red = (SeekBar) findViewById(R.id.redSlider)).setOnSeekBarChangeListener(chanelListener);
        (green = (SeekBar) findViewById(R.id.greenSlider)).setOnSeekBarChangeListener(chanelListener);
        (blue = (SeekBar) findViewById(R.id.blueSlider)).setOnSeekBarChangeListener(chanelListener);

        red.setMax(255);
        green.setMax(255);
        blue.setMax(255);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
