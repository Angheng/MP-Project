/*
Author: 20181683 임중혁
Last Modification date: 19.11.16
Function: Container of Item's data
 */


package com.bungae1112.final_proj.mainActivity.listView;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

public class ItemData
{
    private int no;
    private String name;
    private String category;
    private String remain, seat;
    private String tel, addr, menu;
    private String lat, lng;

    public ItemData(String name, String category, String remain, String max, String tel, String addr, String menu, String lat, String lng) {
        this.name = name;
        this.category = category;
        this.remain = remain;
        this.seat = max;
        this.tel = tel;
        this.addr = addr;
        this.menu = menu;
        this.lat = lat;
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getRemain() {
        return remain;
    }

    public String getSeat() {
        return seat;
    }

    public String getTel() {
        return tel;
    }

    public String getAddr() {
        return addr;
    }

    public String getMenu() {
        return menu;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    @NonNull
    @Override
    public String toString() {
        String msg ="";

        msg += "name: " + getName() + "\n";
        msg += "Category: " + getCategory() + "\n";
        msg += "remain: " + getRemain() + "\n";
        msg += "seat: " + getSeat() + "\n";
        msg += "tel: " + getTel() + "\n";
        msg += "Addr: " + getAddr() + "\n";
        msg += "menu: " + getMenu() + "\n";
        msg += "lat: " + getLat() + "\n";
        msg += "lng: " + getLng() + "\n";

        return msg;
    }
}
