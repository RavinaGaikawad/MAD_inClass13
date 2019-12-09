package com.example.mad_inclass13;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<LatLng> latlon = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent intent = getIntent();
        ArrayList<String> Lat = intent.getStringArrayListExtra("Latitude");
        ArrayList<String> Lon = intent.getStringArrayListExtra("Longitude");

        for (int i=0; i < Lat.size(); i++) {
            LatLng latLng = new LatLng(Double.parseDouble(Lat.get(i)),Double.parseDouble(Lon.get(i)));
            Log.d("bagh", Lat.get(i)+" - "+Lon.get(i));
            latlon.add(latLng);
        }


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.clear();

        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.addAll(latlon);

        polylineOptions.clickable(false);

        mMap.addPolyline(polylineOptions);

        mMap.addMarker(new MarkerOptions().position(latlon.get(0)));
        mMap.addMarker(new MarkerOptions().position(latlon.get(latlon.size()-1)));

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (LatLng latLng : latlon) {
                    builder.include(latLng);
                }
                LatLngBounds bounds = builder.build();

                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 5);
                mMap.animateCamera(cu);
            }
        });
    }
}
