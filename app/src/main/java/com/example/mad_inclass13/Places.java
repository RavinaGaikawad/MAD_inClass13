package com.example.mad_inclass13;

public class Places {
    String placeName;
    String imageURL;
    String lat;

    @Override
    public String toString() {
        return "Places{" +
                "placeName='" + placeName + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", tripPlaceId='" + tripPlaceId + '\'' +
                '}';
    }

    String lon;
    String tripPlaceId;

    public String getTripPlaceId() {
        return tripPlaceId;
    }

    public void setTripPlaceId(String tripPlaceId) {
        this.tripPlaceId = tripPlaceId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public Places() {
    }
}
