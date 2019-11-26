package com.bungae1112.final_proj.itemView;
public class item {
    private String StoreName;
    private String StoreAddress;
    private String imgURL;
    private String StorePhoneNum;
    private String MenuName;
    private String MenuPrice;
    private String TotalSeat;
    private String RemainSeat;


    public item(){

    }

    public item(String name, String address, String PhoneNum, String imgURL,String totalSeat, String remainSeat){
        this.StoreName = name;
        this.imgURL = imgURL;
        this.StoreAddress = address;
        this.StorePhoneNum = PhoneNum;
        this.TotalSeat = totalSeat;
        this.RemainSeat = remainSeat;
    }


    public void setStoreName(String a){
        this.StoreName = a;
    }
    public void setStoreAddress(String a){
        this.StoreAddress = a;
    }
    public void setStorePhoneNum(String a){
        this.StorePhoneNum = a;
    }
    public void setImgURL(String a){
        this.imgURL = a;
    }
    public void setMenuName(String a){
        this.MenuName = a;
    }
    public void setMenuPrice(String a){
        this.MenuPrice = a;
    }
    public void setTotalSeat(String a) {this.TotalSeat = a;}
    public void setRemainSeat(String a) {this.RemainSeat =a ;}

    public String getStoreName(){
        return StoreName;
    }
    public String getStoreAddress(){
        return StoreAddress;
    }
    public String getImgURL(){
        return imgURL;
    }
    public String getStorePhoneNum(){
        return StorePhoneNum;
    }
    public String getMenuName(){
        return MenuName;
    }
    public String getMenuPrice(){
        return MenuPrice;
    }
    public String getTotalSeat() {return TotalSeat;}
    public String getRemainSeat() {return RemainSeat;}

}
