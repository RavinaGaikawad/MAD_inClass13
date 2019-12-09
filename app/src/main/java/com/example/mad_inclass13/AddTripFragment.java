package com.example.mad_inclass13;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class AddTripFragment extends Fragment {
EditText et_tripname;
EditText et_cityname;
private final OkHttpClient client = new OkHttpClient();
String API_KEY = "AIzaSyAZFzkNiLS1TIQlWEDB_2SqyFctY5nyAZI";
ArrayList<Trip> trips = new ArrayList<>();
String citynames[];
String placeId = "";
private OnFragmentInteractionListener mListener;

    public AddTripFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().setTitle("Add Trip");

        et_tripname = getActivity().findViewById(R.id.et_tripname);
        et_cityname = getActivity().findViewById(R.id.et_cityname);

        getActivity().findViewById(R.id.bt_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Request request = new Request.Builder()
                        .url("https://maps.googleapis.com/maps/api/place/autocomplete/json?key=" + API_KEY + "&types=(cities)&input=" + et_cityname.getText().toString())
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try (ResponseBody responseBody = response.body()) {
                            if (!response.isSuccessful())
                                throw new IOException("Unexpected code " + response);

                            //Log.d("bagh", responseBody.string());
                            String json = responseBody.string();

                            JSONObject root = new JSONObject(json);
                            JSONArray predictions = root.getJSONArray("predictions");

                            citynames = new String[predictions.length()];
                            for(int i=0; i< predictions.length(); i++){
                                JSONObject pred = predictions.getJSONObject(i);
                                String description = pred.getString("description");
                                String place_id = pred.getString("place_id");

                                Trip trip = new Trip();
                                trip.setDescription(description);
                                trip.setPlaceId(place_id);
                                citynames[i] = description;
                                Log.d("bagh", "Cities "+citynames[i].toString());

                                trips.add(trip);
                            }

                            Message message = mHandler.obtainMessage(250, "success");
                            message.sendToTarget();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        getActivity().findViewById(R.id.bt_addtrip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Trip trip = new Trip();
                trip.setDescription(et_cityname.getText().toString());
                trip.setCityName(et_cityname.getText().toString());
                trip.setTripName(et_tripname.getText().toString());
                trip.setPlaceId(placeId);
                mListener.AddTripToTripFragment(trip);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_trip, container, false);
    }

    Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message message) {
            if(message.what == 250){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Choose a City")
                        .setItems(citynames, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.d("bagh" ,"Clicked "+citynames[i]);
                                et_cityname.setText(citynames[i]);
                                placeId = trips.get(i).placeId;
                            }
                        });

                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        }
    };

    public interface OnFragmentInteractionListener {
        void AddTripToTripFragment(Trip trip);
    }
}
