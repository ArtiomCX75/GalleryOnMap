package com.dmytrobohdanov.galleryonmap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    //messages for handle login errors
    private final String EMPTY_FIELD_ERROR_MESSAGE = "fields can't be empty";
    private final String WRONG_LOGIN_MESSAGE = "no such user";
    private final String WRONG_PASS_MESSAGE = "wrong pass";

    private EditText loginField;
    private EditText passwordField;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //fields init
        loginField = (EditText) findViewById(R.id.login_field);
        passwordField = (EditText) findViewById(R.id.login_pass);
        loginButton = (Button) findViewById(R.id.login_button);

        //set listener to login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get login
                String login = loginField.getText().toString();

                //get pass
                String pass = passwordField.getText().toString();

                //check is there text in fields
                if (login == null || login.isEmpty() || pass == null || pass.isEmpty()) {
                    Toast.makeText(getBaseContext(), EMPTY_FIELD_ERROR_MESSAGE, Toast.LENGTH_SHORT).show();
                } else {
                    //check is there is such user
                    if (DataBaseHelper.getInstance(LoginActivity.this).getArrayOfUsernames().contains(login)) {

                        //get encrypted pass
                        pass = Encryptor.getMd5(pass);

                        //get encrypted pass from db
                        String passFromDb = DataBaseHelper.getInstance(LoginActivity.this).getUsersPass(login);

                        //checking password
                        if (pass.equals(passFromDb)) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            //wrong password handle
                            Toast.makeText(getBaseContext(), WRONG_PASS_MESSAGE, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //wrong login handle
                        Toast.makeText(getBaseContext(), WRONG_LOGIN_MESSAGE, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
