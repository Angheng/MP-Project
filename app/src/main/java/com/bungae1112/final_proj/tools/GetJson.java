package com.bungae1112.final_proj.tools;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import com.bungae1112.final_proj.mainActivity.listView.ItemData;

import java.util.List;

public interface GetJson {
    String URL = "http://54.180.153.64:3000";

    @GET("/stores/{extra}")
    Call< JsonDataSet > getData( @Path("extra") String extra );


}
