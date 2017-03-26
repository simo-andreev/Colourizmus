package com.colourizmus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {

    private RadioGroup genderRadio;
    private Button proceedBtn;
    private EditText nameInput;

    private String name;
    private String gender;
    private SharedPreferences shPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        shPref = getSharedPreferences(getString(R.string.prefs_key_user_data), MODE_PRIVATE);

        nameInput = (EditText) findViewById(R.id.nameInput);
        genderRadio = (RadioGroup) findViewById(R.id.genderRadio);
        proceedBtn = (Button) findViewById(R.id.proceedButton);

        genderRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                //could be ternary oprtr I guess...
                switch (checkedId) {
                    case R.id.LadyRadio:
                        gender = getString(R.string.title_lady);
                        break;
                    case R.id.LordRadio:
                        gender = getString(R.string.title_lord);
                        break;
                    default:
                        gender = "WHAAAAAAAAAAAAAAAA?!";
                }
            }
        });

        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = nameInput.getText().toString();

                if (gender == null || name == null) {
                    Toast.makeText(SignInActivity.this, "Please answer both questions.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (name.isEmpty()) {
                    Toast.makeText(SignInActivity.this, "Please enter thine name.", Toast.LENGTH_SHORT).show();
                    return;
                }
                SharedPreferences.Editor spEdit = shPref.edit();
                spEdit.putString(getString(R.string.prefs_key_name), name);
                spEdit.putString(getString(R.string.prefs_key_gender), gender);
                spEdit.commit();

                Intent i = new Intent(SignInActivity.this, WelcomeActivity.class);
                startActivity(i);
                finish();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.e(this.toString(), "SignIn.onStart(...)");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(this.toString(), "SignIn.onResume(...)");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(this.toString(), "SignIn.onPause(...)");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(this.toString(), "SignIn.onStop(...)");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(this.toString(), "SignIn.onDestroy(...)");

    }
}
