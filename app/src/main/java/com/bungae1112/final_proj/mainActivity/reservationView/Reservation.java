package com.bungae1112.final_proj.mainActivity.reservationView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.*;
import java.net.*;

import android.widget.TimePicker;
import android.widget.Toast;

import com.bungae1112.final_proj.R;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

public class Reservation extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation);

        Intent intent = getIntent();

        TimePicker tp = (TimePicker) findViewById(R.id.timePicker1);

        final EditText et_reservation_name = (EditText) findViewById(R.id.et_reservation_name);
        final EditText et_reservation_number = (EditText) findViewById(R.id.et_reservation_number);
        final EditText et_reservation_phone = (EditText) findViewById(R.id.et_reservation_phone);



        final String store_name = intent.getStringExtra("name");

        final int hour = tp.getHour();
        final int min = tp.getMinute();

        Button btn_reserve = (Button) findViewById(R.id.btn_reservation);

        btn_reserve.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String phoneNumber = et_reservation_phone.getText().toString();
                final String number = et_reservation_number.getText().toString();
                final String name = et_reservation_name.getText().toString();
                final String token = FirebaseInstanceId.getInstance().getId();
                Log.e("token", token);
                Reservation(name, number, phoneNumber, hour, min, store_name);
            }
        });
    }

    void Reservation(final String name, final String amount, final String phone, final int hour, final int min, final String market_name) {
        // 번호가 phone 인 name 고객이 hour 시 min 분에 number 명 예약하였음.
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                final String token = FirebaseInstanceId.getInstance().getId();    // get User's Token

                final String res = ReservePost(market_name, amount, token);

                try{
                    JSONObject jsonObject = new JSONObject(res);

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
            }
        });
        t.start();
    }

    //------------------------------
    public String ReservePost(String store, String amount, String token) {
        try {
            //--------------------------
            //   URL 설정하고 접속하기
            //--------------------------
            URL url = new URL("http://54.180.153.64:3000/reserve");       // URL 설정
            HttpURLConnection http = (HttpURLConnection) url.openConnection();   // 접속
            //--------------------------
            //   전송 모드 설정 - 기본적인 설정이다
            //--------------------------
            http.setDefaultUseCaches(false);
            http.setDoInput(true);                         // 서버에서 읽기 모드 지정
            http.setDoOutput(true);                       // 서버로 쓰기 모드 지정
            http.setRequestMethod("POST");         // 전송 방식은 POST

            // 서버에게 웹에서 <Form>으로 값이 넘어온 것과 같은 방식으로 처리하라는 걸 알려준다
            http.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            //--------------------------
            //   서버로 값 전송
            //--------------------------
            StringBuffer buffer = new StringBuffer();
            buffer.append("store").append("=").append(store).append("&");
            buffer.append("amount").append("=").append(amount).append("&");
            buffer.append("token").append("=").append(FirebaseInstanceId.getInstance().getToken());

            OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
            PrintWriter writer = new PrintWriter(outStream);
            writer.write(buffer.toString());
            writer.flush();
            //--------------------------
            //   서버에서 전송받기
            //--------------------------
            InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "UTF-8");
            BufferedReader reader = new BufferedReader(tmp);
            StringBuilder builder = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {       // 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
                builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
            }
            return builder.toString();
        } catch (MalformedURLException e) {
            //
        } catch (IOException e) {
            //
        } // try
        return "";
    }
}