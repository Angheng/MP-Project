package com.bungae1112.final_proj.mainActivity.sign_upView;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bungae1112.final_proj.R;
import com.bungae1112.final_proj.mainActivity.MainActivity;
import com.bungae1112.final_proj.mainActivity.reservationView.Reservation;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    private InputStream downloadUrl(final URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        return conn.getInputStream();
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        final RadioButton accept = (RadioButton)findViewById(R.id.radio_accept);
        final Button signUpButton = (Button) findViewById(R.id.btn_signUp);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(accept.isChecked()) {
                    EditText et_id = (EditText) findViewById(R.id.et_ID);
                    String id = et_id.getText().toString();

                    EditText et_pw = (EditText) findViewById(R.id.et_PW);
                    String pw = et_pw.getText().toString();

                    EditText et_name = (EditText) findViewById(R.id.et_name);
                    String name = et_name.getText().toString();


                    EditText et_phone = (EditText) findViewById(R.id.et_phone);
                    String phone = et_phone.getText().toString();

                    SignUp(id, pw, name, phone);
                }else{
                    Toast.makeText(getApplicationContext(), "약관에 동의해야 가입하실 수 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public boolean SignUp(final String id, final String password, String name, String phone){
        String tmp = password + "\n" + name + "\n" + "\n" + phone;
        String pwPattern = "((?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9가-힣]).{8,})";
        Matcher matcher = Pattern.compile(pwPattern).matcher(password);


        // 회원정보 유효성 검사
        if(getPassword(id) != null){
            Toast.makeText(getApplicationContext(), "이미 존재하는 ID입니다.", Toast.LENGTH_LONG).show();
            return false;
        }

        if(id.length() < 4){
            Toast.makeText(getApplicationContext(), "아이디의 길이는 4자리 이상으로 해야합니다.", Toast.LENGTH_LONG).show();
            return false;
        }

        if(password.length()<8){
            Toast.makeText(getApplicationContext(), "패스워드길이는 8자리 이상으로 해야합니다.", Toast.LENGTH_LONG).show();
            return false;
        }

        if(!matcher.matches()){
            Toast.makeText(getApplicationContext(), "영문 대,소문자,숫자,특수문자를 모두 1개이상 씩 포함하여야합니다.", Toast.LENGTH_LONG).show();
            return false;
        }

        if(name.length()<1){
            Toast.makeText(getApplicationContext(), "이름을 입력해주세요.", Toast.LENGTH_LONG).show();
            return false;
        }


        if(phone.length()<1){
            Toast.makeText(getApplicationContext(), "전화번호를 입력해주세요.", Toast.LENGTH_LONG).show();
            return false;
        }

        try{
            // 회원가입 Method
//            FileOutputStream fos = openFileOutput(id, Context.MODE_PRIVATE);
//
//
//            PrintWriter writer = new PrintWriter(fos);
//
//            writer.print(tmp);
//            writer.close();

            Thread t = new Thread(new Runnable() { @Override public void run() {
                try{
                    Log.e("Register info", "http://54.180.153.64:3000/users/register?id="+id+"&pass="+password);
                    InputStream is = downloadUrl(new URL("http://54.180.153.64:3000/users/register?id="+id+"&pass="+password));

                    StringBuffer sb = new StringBuffer();
                    byte[] b = new byte[4096];
                    for (int n; (n = is.read(b)) != -1;) {
                        sb.append(new String(b, 0, n));
                    }
                    Log.e("tag", sb.toString());
                    final String s = sb.toString();

                    JSONObject jsonObject = new JSONObject(s);

                    final String msg = jsonObject.getString("msg");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                        }
                    });
                }
                catch (Exception ex)
                {

                }
            }});

            t.start();

            Intent intent = new Intent(getApplicationContext(), Reservation.class);
            intent.putExtra("id", id);
            startActivity(intent);
        }
        catch (Exception e)
        {
            Log.e("Error", e.toString());
        }
//        catch(FileNotFoundException e){
//            e.printStackTrace();
//
//            Toast.makeText(getApplicationContext(), "오류가 발생하였습니다.", Toast.LENGTH_LONG).show();
//            return false;
//        }
        return true;
    }


    public String getPassword(String id){
        String pass = null;

        try{
            FileInputStream fis = openFileInput(id);

            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            pass = reader.readLine();
        } catch (Exception e){
            e.printStackTrace();
        }
        return pass;
    }
    //push test dd
}

