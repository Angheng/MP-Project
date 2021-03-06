/*
Author: 20181683 임중혁
Last Modification date: 19.11.23
Function: Define WebServer's Json Format
 */

package com.bungae1112.final_proj.tools;

import com.bungae1112.final_proj.mainActivity.listView.ItemData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JsonDataSet {

    @SerializedName("result")
    private String result;

    @SerializedName("msg")
    private List<ItemData> rawJsonArr;

    public String getResult() {
        return result;
    }

    public List<ItemData> getRawJsonArr() {
        return rawJsonArr;
    }
}
