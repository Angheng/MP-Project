package com.bungae1112.final_proj.itemView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bungae1112.final_proj.R;
import com.bungae1112.final_proj.mainActivity.reservationView.Reservation;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class itemView extends AppCompatActivity {
    TextView itemInfo_StoreName;
    TextView itemInfo_StoreAddress;
    TextView itemInfo_StorePhoneNum;
    TextView itemInfo_StoreSeat;
    itemInfoAdapter adapter;
    item Store;

    private Button btnReservation;

    private PopupWindow popupReservation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_info);
        ListView listView  = (ListView)findViewById(R.id.item_ListView);

        itemInfo_StoreName = (TextView) findViewById(R.id.itemInfo_storeName);
        itemInfo_StoreAddress = (TextView) findViewById(R.id.itemInfo_address);
        itemInfo_StorePhoneNum = (TextView) findViewById(R.id.itemInfo_phoneNum);
        itemInfo_StoreSeat = (TextView) findViewById(R.id.textView_SeatNum);
        final ArrayList<String> menuList = new ArrayList<>();
        btnReservation = (Button) findViewById(R.id.btnReservation);
        adapter = new itemInfoAdapter();

        //intent를 통한 데이터 받기
        Intent intent = getIntent();
        String imgURL = intent.getStringExtra("imgURL");
        String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        String telnum = intent.getStringExtra("telnum");
        String menu = intent.getStringExtra("menu");
        String remain = intent.getStringExtra("remain");
        String seat = intent.getStringExtra("seat");

        Store = new item(name,address,telnum,imgURL,seat,remain);
        setInit();

        //list view에 item 추가
        adapter.additem("test","test");
        listView.setAdapter(adapter);


        btnReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(itemView.this, Reservation.class));
            }
        });

    }

    //    정보 입력
    private void setInit(){
        itemInfo_StoreName.setText(Store.getStoreName());
        itemInfo_StoreAddress.setText(Store.getStoreAddress());
        itemInfo_StorePhoneNum.setText(Store.getStorePhoneNum());
        itemInfo_StoreSeat.setText(Store.getRemainSeat()+" / "+Store.getTotalSeat());

    }



}
