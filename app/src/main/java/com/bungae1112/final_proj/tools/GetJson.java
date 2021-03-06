/*
Author: 20181683 임중혁
Last Modification date: 19.11.23
Function: Define HTTP GET Method fot Retrofit
 */

package com.bungae1112.final_proj.tools;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import com.bungae1112.final_proj.mainActivity.listView.ItemData;

import java.util.HashMap;
import java.util.List;

public interface GetJson {
    String URL = "http://54.180.153.64:3000";

    @GET("/stores")
    Call< JsonDataSet > getData();


}
