package com.example.mad_inclass13;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Trip {
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String tripName;
    String cityName;
    String description;
    String placeId;
    ArrayList<Places> placesArrayList;
    String latitude;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    String longitude;

    public ArrayList<Places> getPlacesArrayList() {
        return placesArrayList;
    }

    public void setPlacesArrayList(ArrayList<Places> placesArrayList) {
        this.placesArrayList = placesArrayList;
    }

    public Trip(String id, String tripName, String cityName, String description, String placeId, ArrayList<Places> placesArrayList, String latitude, String longitude) {
        this.id = id;
        this.tripName = tripName;
        this.cityName = cityName;
        this.description = description;
        this.placeId = placeId;
        this.placesArrayList = placesArrayList;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public Trip() {
    }

    public Map toHashMap(){
        Map<String, Object> tripMap = new HashMap<>();

        tripMap.put("tripid", this.getId());
        tripMap.put("placename", this.getCityName());
        tripMap.put("tripname", this.getTripName());
        tripMap.put("placeid", this.getPlaceId());
        tripMap.put("latitude", this.getLatitude());
        tripMap.put("longitude", this.getLongitude());
        return tripMap;
    }
}
