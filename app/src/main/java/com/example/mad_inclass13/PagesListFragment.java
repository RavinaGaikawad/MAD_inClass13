package com.example.mad_inclass13;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class PagesListFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<Places> placesArrayList = new ArrayList<>();
    IGotToMain mtoMain;


    public PagesListFragment() {
        // Required empty public constructor
    }

    public PagesListFragment(ArrayList<Places> places) {
        this.placesArrayList = places;
    }

    public PagesListFragment(ArrayList<Places> placesArrayList, IGotToMain mtoMain) {
        this.placesArrayList = placesArrayList;
        this.mtoMain = mtoMain;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pages_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().setTitle("Places List");

        recyclerView = getActivity().findViewById(R.id.pageslist_recyclerview);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new PagesListRecyclerView(placesArrayList);
        recyclerView.setAdapter(mAdapter);

        getActivity().findViewById(R.id.bt_gototrips).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mtoMain.FrompagestoMain(placesArrayList, placesArrayList.get(0).getTripPlaceId());
            }
        });
    }

    public  interface IGotToMain{
        void FrompagestoMain(ArrayList<Places> places, String placeId);
    }
}
