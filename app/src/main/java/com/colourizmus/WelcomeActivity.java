package com.colourizmus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity {


    private SharedPreferences userInfo;
    private String name;
    private String gender;

    private TextView welcomeMessage;
    private Button proceedBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        welcomeMessage = (TextView) findViewById(R.id.welcome_message);
        proceedBtn = (Button) findViewById(R.id.welcome_pickers_button);

        userInfo = getSharedPreferences(getString(R.string.prefs_key_user_data), MODE_PRIVATE);
        name = userInfo.getString(getString(R.string.prefs_key_name), null);
        gender = userInfo.getString(getString(R.string.prefs_key_gender), null);

        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (name == null || gender == null || name.isEmpty() || !(gender.equals(getString(R.string.title_lady)) || gender.equals(getString(R.string.title_lord)))) {
            Toast.makeText(this, getString(R.string.main_prompt_sign_in), Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, SignInActivity.class);
            startActivity(i);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (name == null || name.isEmpty())
            name = userInfo.getString("name", "oh fuck...");
        if (gender == null || gender.isEmpty())
            gender = userInfo.getString("gender", "oh fuck...");

        welcomeMessage.setText(getString(R.string.msg_welcome_formatted, gender, name));
    }

}
