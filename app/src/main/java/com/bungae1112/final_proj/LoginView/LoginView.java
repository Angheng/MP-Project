package com.bungae1112.final_proj.LoginView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bungae1112.final_proj.LoadingView.LoadingActivity;
import com.bungae1112.final_proj.R;
import com.bungae1112.final_proj.mainActivity.MainActivity;
import com.bungae1112.final_proj.mainActivity.sign_upView.SignUp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class LoginView extends AppCompatActivity {
    Button btnLogin;
    Button btnSignup;
    private InputStream downloadUrl(final URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        return conn.getInputStream();
    }

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar ab = getSupportActionBar();
        ab.hide();

        Intent intent = new Intent(this, LoadingActivity.class);

        final EditText et_id = (EditText)findViewById(R.id.username);
        final EditText et_pw = (EditText)findViewById(R.id.password);


        startActivity(intent);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignup = (Button) findViewById(R.id.btnSignUp);

        btnLogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // 로그인 성공 여부 판단해야됨
                Thread t = new Thread(new Runnable() { @Override public void run() {
                    try{
                        final String id = et_id.getText().toString();
                        final String pw = et_id.getText().toString();


                        InputStream is = downloadUrl(new URL("http://54.180.153.64:3000/users/login?id="+id+"&pw="+pw));

                        StringBuffer sb = new StringBuffer();
                        byte[] b = new byte[4096];
                        for (int n; (n = is.read(b)) != -1;) {
                            sb.append(new String(b, 0, n));
                        }
                        Log.e("tag", sb.toString());
                        final String s = sb.toString();

                        JSONObject jsonObject = new JSONObject(s);

                        final String msg = jsonObject.getString("msg");
                        Log.e("Msg", msg);
                        if(msg.equals(""))
                        {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(LoginView.this, MainActivity.class));
                                }
                            });
                        }
                        else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                                }
                            });
                        }

                    }
                    catch (Exception ex)
                    {

                    }
                }});
                t.start();


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
