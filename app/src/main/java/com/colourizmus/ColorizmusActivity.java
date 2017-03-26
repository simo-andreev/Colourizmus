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

import java.util.Random;

public class ColorizmusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colorizmus);


        ImageView b = (ImageView) findViewById(R.id.ChristusButtinizmus);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Random r = new Random();

                CountDownTimer cd = new CountDownTimer(7000, 66) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        findViewById(R.id.ChristusLayoutrizmus).setBackgroundColor(Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
                    }

                    @Override
                    public void onFinish() {
                    }
                };
                cd.start();
            }
        });
    }
}
