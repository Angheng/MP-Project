package com.bungae1112.final_proj.mainActivity.reservationView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import com.bungae1112.final_proj.R;

public class Reservation extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation);

        TimePicker tp = (TimePicker)findViewById(R.id.timePicker1);

        EditText et_reservation_name = (EditText)findViewById(R.id.et_reservation_name);
        EditText et_reservation_number = (EditText)findViewById(R.id.et_reservation_number);
        EditText et_reservation_phone = (EditText)findViewById(R.id.et_reservation_phone);

        final String phoneNumber = et_reservation_phone.getText().toString();
        final String number = et_reservation_number.getText().toString();
        final String name = et_reservation_name.getText().toString();

        final int hour = tp.getHour();
        final int min = tp.getMinute();

        Button btn_reserve  =  (Button)findViewById(R.id.btn_reservation);

        btn_reserve.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reservation(name,number,phoneNumber, hour, min);
            }
        });
    }

    void Reservation(String name, String number, String phone, int hour, int min)
    {
        // 번호가 phone 인 name 고객이 hour 시 min 분에 number 명 예약하였음.

        // 예약 Method 실
    }

}
