package com.bungae1112.final_proj.itemView;
public class item {
    private String StoreName;
    private String StoreAddress;
    private String imgURL;
    private String StorePhoneNum;
    private String MenuName;
    private String MenuPrice;


    public item(){}

    public item(String name, String address, String PhoneNum, String imgURL){
        this.StoreName = name;
        this.imgURL = imgURL;
        this.StoreAddress = address;
        this.StorePhoneNum = PhoneNum;
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

}


//
////    데이터 베이스에서 받아올 형식================================================================
//    String str = "[" +
//            "{'name' : '덤', " +
//            "'phoneNum' : 123, " +
//            "'image' : 'image', " +
//            "'address' : 'eeeee', " +
//            "'menu': [{'name':'고기',price':18000},{'name' : '술','price' : 200000]" +
//            "}]";
//    JSONArray Store;
//    {
//        try {
//            Store = new JSONArray(str);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
////    =============================================================================================


//    public void inititem(String name){
//        try {
//            JSONObject object;
//            for(int i =0;i<Store.length();i++){
//                object = Store.getJSONObject(i);
//                String id = object.getString("name");
//                StoreName = object.getString("address");
//                StorePhoneNum = object.getString("phoneNum");
//
//                if(id == name) break;
//            }
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//    }