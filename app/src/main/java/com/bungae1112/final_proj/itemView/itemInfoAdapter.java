package com.bungae1112.final_proj.itemView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bungae1112.final_proj.R;

import java.util.ArrayList;

public class itemInfoAdapter extends BaseAdapter {
    private ArrayList<item> itemInfoMenuList = new ArrayList<item>();


    @Override
    public int getCount() {
        return itemInfoMenuList.size();
    }
    @Override
    public Object getItem(int position) {
        return itemInfoMenuList.get(position) ;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.iteminfo_item, parent, false);
        }

        TextView menuName = (TextView) convertView.findViewById(R.id.itemMenu_name);
        TextView menuPrice = (TextView) convertView.findViewById(R.id.itemMenu_price);

        item menuItem = itemInfoMenuList.get(position);

        menuName.setText(menuItem.getMenuName());
        menuPrice.setText(menuItem.getMenuPrice());

        return convertView;
    }

    public void additem(String menu,String price){
        item it = new item();
        it.setMenuName(menu);
        it.setMenuPrice(price);

        itemInfoMenuList.add(it);
    }
}
