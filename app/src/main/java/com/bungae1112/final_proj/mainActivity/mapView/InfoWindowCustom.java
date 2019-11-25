package com.bungae1112.final_proj.mainActivity.mapView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bungae1112.final_proj.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;


public class InfoWindowCustom implements GoogleMap.InfoWindowAdapter {
    Context context;
    LayoutInflater inflater;

    public InfoWindowCustom(Context context) {
        this.context = context;
    }

    @Override
    public View getInfoContents(Marker marker) {
        Log.d("InfoWindowCustom", "getInfoContents");

        inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.info_window, null);



        TextView title = (TextView) v.findViewById(R.id.addressTxt);
//        TextView subtitle = (TextView) v.findViewById(R.id.info_window_subtitle);
        title.setText(marker.getTitle());
//        subtitle.setText(marker.getSnippet());
        return v;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        Log.d("InfoWindowCustom", "getInfoWindow");

        return null;

    }
}

