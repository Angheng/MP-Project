package com.bungae1112.final_proj.itemView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import com.bungae1112.final_proj.R;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class itemView extends AppCompatActivity {
    EditText itemInfo_StoreName;
    EditText itemInfo_StoreAddress;
    EditText itemInfo_StorePhoneNum;
    itemInfoAdapter adapter;
    item Store;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_info);
        ListView listView  = (ListView)findViewById(R.id.item_ListView);

        itemInfo_StoreName = (EditText) findViewById(R.id.itemInfo_storeName);
        itemInfo_StoreAddress = (EditText) findViewById(R.id.itemInfo_address);
        itemInfo_StorePhoneNum = (EditText) findViewById(R.id.itemInfo_phoneNum);
        final ArrayList<String> menuList = new ArrayList<>();

        adapter = new itemInfoAdapter();

        adapter.additem("test","test");

        listView.setAdapter(adapter);
        //예약하기 클릭 시

    }
    //    정보 입력
    private void setInit(){
        itemInfo_StoreName.setText(Store.getStoreName());
        itemInfo_StoreAddress.setText(Store.getStoreAddress());
        itemInfo_StorePhoneNum.setText(Store.getStorePhoneNum());

    }



}
