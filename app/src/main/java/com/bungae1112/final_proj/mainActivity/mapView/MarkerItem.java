package com.bungae1112.final_proj.mainActivity.mapView;

public class MarkerItem {

    double lat;
    double lng;
    String title, remainSeats;

    public MarkerItem(double lat, double lng, String title, String remainSeats) {
        this.lat = lat;
        this.lng = lng;
        this.title = title;
        this.remainSeats = remainSeats;
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

    public String getRemainSeats() {
        return remainSeats;
    }
}
