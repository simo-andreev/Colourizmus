package com.colourizmus.view;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.colourizmus.R;
import com.colourizmus.model.ColourDbAdapter;

import java.util.Random;

public class EpilepticaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epileptica);


        final ImageView b = (ImageView) findViewById(R.id.epileptica_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                b.setClickable(false);
                final Random r = new Random();

                CountDownTimer cd = new CountDownTimer(7000, 66) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        findViewById(R.id.activity_epileptica_base).setBackgroundColor(Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
                    }

                    @Override
                    public void onFinish() {
                        //TODO TEMPORARY: testing mah dB instertion.
                        ColourDbAdapter dbAdapter = new ColourDbAdapter(EpilepticaActivity.this);
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
