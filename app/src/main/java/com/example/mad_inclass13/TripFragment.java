package com.example.mad_inclass13;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;


public class TripFragment extends Fragment {
Button bt_addtrip;
ArrayList<Trip> trips = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private OnFragmentInteractionListener mListener;

    public TripFragment() {
        // Required empty public constructor
    }

    public TripFragment(ArrayList<Trip> trips) {
        this.trips = trips;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Trips");

        recyclerView = getActivity().findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        Log.d("bagh", "inside interface");
        Log.d("bagh" , trips.toString());
        mAdapter = new TripsRecyclerView(trips);
        recyclerView.setAdapter(mAdapter);

        bt_addtrip = getActivity().findViewById(R.id.bt_addtrip);


        bt_addtrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.TripToAddTripFragment(trips);
            }
        });
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AddTripFragment.OnFragmentInteractionListener) {
            mListener = (TripFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trip, container, false);
    }

    public interface OnFragmentInteractionListener {
        void TripToAddTripFragment(ArrayList<Trip> trip);
        void TripToAddPlaceFragment(ArrayList<Trip> trip);
    }

}
