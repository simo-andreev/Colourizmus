package com.colourizmus;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.colourizmus.dbManagment.ColourDbAdapter;

import java.util.Random;

public class ColorizmusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colorizmus);


        final ImageView b = (ImageView) findViewById(R.id.ChristusButtinizmus);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                b.setClickable(false);
                final Random r = new Random();

                CountDownTimer cd = new CountDownTimer(7000, 66) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        findViewById(R.id.ChristusLayoutrizmus).setBackgroundColor(Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
                    }

                    @Override
                    public void onFinish() {
                        //TODO TEMPORARY: testing mah dB instertion.
                        ColourDbAdapter dbAdapter =  new ColourDbAdapter(ColorizmusActivity.this);
                        dbAdapter.addCoulourEntry(1, "TEST:" + r.nextFloat());
                        b.setClickable(true);
                    }
                };

                cd.start();
            }
        });
    }

    @Override
    protected void onDestroy() {
        ColourDbAdapter dbAdapter = new ColourDbAdapter(this);
        dbAdapter.getAllFavouriteColours();
        super.onDestroy();
    }
}
