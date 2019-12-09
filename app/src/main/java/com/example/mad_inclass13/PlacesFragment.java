package com.example.mad_inclass13;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class PlacesFragment extends Fragment {
    Trip trip;
    private final OkHttpClient client = new OkHttpClient();
    String API_KEY = "AIzaSyAdYwB1EtKrfU5FO14eQ7tms7WxV0sJKvY";
    String lat = "";
    String lon = "";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<Places> places = new ArrayList<>();

    public PlacesFragment() {
        // Required empty public constructor
    }

    public PlacesFragment(Trip trip) {
        this.trip = trip;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().setTitle("Add Places");
        Log.d("bagh", "URL "+ "https://maps.googleapis.com/maps/api/place/autocomplete/json?key=" + API_KEY + "&placeid="+ trip.getPlaceId());
        Request request = new Request.Builder()
                .url("https://maps.googleapis.com/maps/api/place/details/json?key=" + API_KEY + "&placeid="+ trip.getPlaceId())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);

                    String json = responseBody.string();

                    JSONObject root = new JSONObject(json);
                    JSONObject result = root.getJSONObject("result");
                    JSONObject geometry = result.getJSONObject("geometry");
                    JSONObject location = geometry.getJSONObject("location");
                    lat = location.getString("lat");
                    lon = location.getString("lng");
                    trip.setLatitude(lat);
                    trip.setLongitude(lon);
                    Message message = mHandler.obtainMessage(250, "success");
                    message.sendToTarget();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_places, container, false);
    }

    public void getAllPlaces(String lat, String lon){
        Request request = new Request.Builder()
                .url("https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=" + API_KEY + "&location="+ lat+","+lon+"&radius=1000")
                .build();
        Log.d("bagh", "URL is https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=" + API_KEY + "&location="+ lat+","+lon+"&radius=1000");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);

                    String json = responseBody.string();
                    Log.d("bagh", "Places fragment fetch data "+json);
                    JSONObject root = new JSONObject(json);
                    JSONArray result = root.getJSONArray("results");
                    for(int i=0; i < result.length(); i++){
                        JSONObject obj = result.getJSONObject(i);
                        JSONObject geometry = obj.getJSONObject("geometry");
                        JSONObject location = geometry.getJSONObject("location");

                        Places place = new Places();
                        place.setImageURL(obj.getString("icon"));
                        place.setLat(location.getString("lat"));
                        place.setLon(location.getString("lng"));
                        place.setPlaceName(obj.getString("name"));
                        place.setTripPlaceId(trip.getPlaceId());
                        places.add(place);
                    }

                    Log.d("bagh", places.toString());
                    Message message = mHandler.obtainMessage(200, "success");
                    message.sendToTarget();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message message) {
            if(message.what == 250){
                getAllPlaces(lat,lon);
            }
            else if(message.what == 200){

                recyclerView = getActivity().findViewById(R.id.placesrecyclerview);
                recyclerView.setHasFixedSize(true);

                // use a linear layout manager
                layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);

                mAdapter = new PlacesRecyclerView(places, (PlacesRecyclerView.PlacesRecyclerInterface) getActivity());
                recyclerView.setAdapter(mAdapter);
            }
        }
    };

}
