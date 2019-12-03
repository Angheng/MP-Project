package com.bungae1112.final_proj.mainActivity.mapView;

public class MarkerItem {

    double lat;
    double lng;
    String title;

    public MarkerItem(double lat, double lng, String title) {
        this.lat = lat;
        this.lng = lng;
        this.title = title;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getTitle() {
        return title;
    }

}
