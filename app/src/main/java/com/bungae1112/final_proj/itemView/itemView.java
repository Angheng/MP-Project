package com.bungae1112.final_proj.itemView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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

    Bitmap bitmap;

    ImageView itemInfo_StoreImage;

    private Button btnSeat;
    private Button btnReservation;
    private Button btnRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_info);
        ListView listView  = (ListView)findViewById(R.id.item_ListView);

        itemInfo_StoreImage = findViewById(R.id.itemInfo_img);

        itemInfo_StoreName = (TextView) findViewById(R.id.itemInfo_storeName);
        itemInfo_StoreAddress = (TextView) findViewById(R.id.itemInfo_address);
        itemInfo_StorePhoneNum = (TextView) findViewById(R.id.itemInfo_phoneNum);
        itemInfo_StoreSeat = (TextView) findViewById(R.id.textView_SeatNum);
        final ArrayList<String> menuList = new ArrayList<>();
        btnSeat = (Button) findViewById(R.id.btnSeat);
        btnReservation = (Button) findViewById(R.id.btnReservation);
        btnRefresh = (Button) findViewById(R.id.btnRefresh);
        adapter = new itemInfoAdapter();

        //intent를 통한 데이터 받기
        Intent intent = getIntent();

        String imgURL = intent.getStringExtra("imgURL");
        final String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        String telnum = intent.getStringExtra("telnum");
        String menu = intent.getStringExtra("menu");
        String remain = intent.getStringExtra("remain");
        String seat = intent.getStringExtra("seat");

        Store = new item(name,address,telnum,imgURL,seat,remain);
        setInit();

        //list view에 item 추가
        adapter.additem("소주","4000원");
        adapter.additem("맥주","4000원");
        listView.setAdapter(adapter);

        /*
        서민주 - seat dialog
         */
        btnSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.dialog_seats);
                dialog.setTitle("Custom Dialog");

                ImageView iv = (ImageView) dialog.findViewById(R.id.imageView);
                iv.setImageResource(R.drawable.ic_seat_layout);

                dialog.show();

                Button btnClose = (Button) dialog.findViewById(R.id.btnClose);
                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.hide();
                    }
                });
            }
        });
        btnReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(itemView.this, Reservation.class);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String remain = intent.getStringExtra("remain");
                String seat = intent.getStringExtra("seat");
                itemInfo_StoreSeat.setText(remain+" / "+seat);
            }
        });

    }

    //    정보 입력
    private void setInit(){
        String img_url = "http://54.180.153.64:3000/images/" + Store.getStoreName() + ".jpg";
        Glide.with(this).load(img_url).into(itemInfo_StoreImage);
        itemInfo_StoreName.setText(Store.getStoreName());
        itemInfo_StoreAddress.setText(Store.getStoreAddress());
        itemInfo_StorePhoneNum.setText(Store.getStorePhoneNum());
        itemInfo_StoreSeat.setText(Store.getRemainSeat()+" / "+Store.getTotalSeat());

    }
}
