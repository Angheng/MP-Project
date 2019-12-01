package com.bungae1112.final_proj.LoginView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bungae1112.final_proj.LoadingView.LoadingActivity;
import com.bungae1112.final_proj.R;
import com.bungae1112.final_proj.mainActivity.MainActivity;
import com.bungae1112.final_proj.mainActivity.sign_upView.SignUp;

import androidx.appcompat.app.AppCompatActivity;


public class LoginView extends AppCompatActivity {
    Button btnLogin;
    Button btnSignup;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignup = (Button) findViewById(R.id.btnSignUp);

        btnLogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginView.this, MainActivity.class));
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginView.this, SignUp.class));
            }
        });
    }



}
