package com.colourizmus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    public static SharedPreferences userInfo;
    public static String name;
    public static String gender;

    Button proceedBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView welcomeMessage = (TextView) findViewById(R.id.welcome_text_field);
        proceedBtn = (Button) findViewById(R.id.proceedButton);

        userInfo = getSharedPreferences("UserData", MODE_PRIVATE);
        name = userInfo.getString("name", null);
        gender = userInfo.getString("gender", null);


        if (name == null || gender == null || name.isEmpty() || !(gender.equals(getString(R.string.lady)) || gender.equals(getString(R.string.lord)))) {
            Toast.makeText(this, getString(R.string.notSignedIn), Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, SignInActivity.class);
            startActivity(i);
        }

        name = userInfo.getString("name", "oh fuck...");
        gender = userInfo.getString("gender", "oh fuck...");
        //TODO - extract String literal to strings.xml for better flexibility!
        welcomeMessage.setText("Welcome " + gender + " " + name + "!");

        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, ColourCreatorActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(this.toString(), "main.onStart(...)");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(this.toString(), "main.onResume(...)");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(this.toString(), "main.onPause(...)");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(this.toString(), "main.onStop(...)");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(this.toString(), "main.onDestroy(...)");

    }
}
