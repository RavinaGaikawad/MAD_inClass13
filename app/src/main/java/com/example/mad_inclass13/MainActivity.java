package com.example.mad_inclass13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddTripFragment.OnFragmentInteractionListener, TripFragment.OnFragmentInteractionListener, PlacesRecyclerView.PlacesRecyclerInterface, PagesListFragment.IGotToMain{
ArrayList<Trip> trips = new ArrayList<>();
ArrayList<Places> places = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(isConnected()){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new TripFragment(), "TripFragment")
                    .addToBackStack(null)
                    .commit();
        }
        else{
            Toast.makeText(this, "No internet Connection.", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isConnected() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());

        if (networkCapabilities != null) {
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void AddTripToTripFragment(Trip trip) {
        Log.d("bagh" , trip.toString());
        trips.add(trip);
        Log.d("bagh" , trips.toString());



        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new TripFragment(trips), "TripFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void TripToAddTripFragment(ArrayList<Trip> tripArrayList) {
        trips = tripArrayList;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new AddTripFragment(), "AddTripFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void TripToAddPlaceFragment(ArrayList<Trip> trip) {

    }

    @Override
    public void gotoMainFromPlaces(Places place) {
        if(!places.contains(place.tripPlaceId)){
            places.add(place);
        }

        for(int i = 0; i < trips.size(); i++){
            if(trips.get(i).getPlaceId().equals(place.getTripPlaceId())){
                trips.get(i).setPlacesArrayList(places);
            }
        }

        Log.d("bagh", trips.toString());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new TripFragment(trips), "TripFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void FrompagestoMain(ArrayList<Places> places, String placeId) {
        Log.d("bagh", "FrompagestoMain "+ places.toString());
        Log.d("bagh", "FrompagestoMain trips size"+ trips.size());
        if(places.size() > 0){
            for(int i = 0; i < trips.size(); i++){
                if(trips.get(i).getPlaceId().equals(placeId)){
                    trips.get(i).setPlacesArrayList(places);
                }
            }
        }
        else{
            for(int i = 0; i < trips.size(); i++){
                if(trips.get(i).getPlaceId().equals(placeId)){
                    trips.get(i).setPlacesArrayList(null);
                }
            }
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new TripFragment(trips), "TripFragment")
                .addToBackStack(null)
                .commit();
    }
}
