package com.colourizmus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
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

        nameInput = (EditText) findViewById(R.id.signin_input_name);
        genderRadio = (RadioGroup) findViewById(R.id.signin_radio_gender);
        proceedBtn = (Button) findViewById(R.id.signin_proceed);

        genderRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                //could be ternary oprtr I guess...
                //^ well this way I define spceific result for specific data (if is 'a': if is 'b'). Trnry oprtr defines if is 'a' : if is-not 'a'.
                switch (checkedId) {
                    case R.id.sigin_as_lady:
                        gender = getString(R.string.title_lady);
                        break;
                    case R.id.signin_as_lord:
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

                if (name == null || !name.matches("\\w{4,}")) {
                    Toast.makeText(SignInActivity.this, getString(R.string.err_invalid_name), Toast.LENGTH_SHORT).show();
                    nameInput.setError(getString(R.string.err_invalid_name));
                    nameInput.requestFocus();
                    return;
                }
                if (gender == null) {
                    Toast.makeText(SignInActivity.this, getString(R.string.err_no_radio_selected), Toast.LENGTH_SHORT).show();
                    //genderRadio.setError(getString(R.string.err_no_radio_selected)); ¯\_(ツ)_/¯
                    genderRadio.requestFocus(); //I know it doesn't work, but... ermahgerd mah coed symmerthry!
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
}
