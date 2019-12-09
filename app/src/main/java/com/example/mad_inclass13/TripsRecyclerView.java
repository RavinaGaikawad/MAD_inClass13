package com.example.mad_inclass13;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TripsRecyclerView extends RecyclerView.Adapter<TripsRecyclerView.MyViewHolder>{
    ArrayList<Trip> trips;
   // private IGoToMain mgoToMain;

    public TripsRecyclerView(ArrayList<Trip> trips) {
        this.trips = trips;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trips_list, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Trip trip = trips.get(position);

        holder.tv_tripname.setText(trip.getTripName());
        holder.tv_cityname.setText(trip.getCityName());
        holder.trip = trip;
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Context context;
        Trip trip;
        TextView tv_cityname;
        TextView tv_tripname;
        Button bt_map;
        Button bt_addplace;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            context = itemView.getContext();
            tv_cityname = itemView.findViewById(R.id.tv_cityname);
            tv_tripname = itemView.findViewById(R.id.tv_tripname);
            bt_map = itemView.findViewById(R.id.bt_gotomap);
            bt_addplace = itemView.findViewById(R.id.bt_addplace);

            bt_addplace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("bagh", "trip clicked is "+trip.toString());
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, new PlacesFragment(trip), "PlacesFragment")
                            .addToBackStack(null)
                            .commit();
                }
            });

            bt_map.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArrayList<String> Lat = new ArrayList<>();
                    ArrayList<String> Lon = new ArrayList<>();
                    for (Trip t:trips) {
                        Lat.add(t.getLatitude());
                        Lon.add(t.getLongitude());
                        ArrayList<Places> places = t.getPlacesArrayList();
                        for (Places p: places) {
                            Lat.add(p.getLat());
                            Lon.add(p.getLon());
                        }
                    }
                    Intent intent = new Intent(itemView.getContext(), MapsActivity.class);
                    intent.putStringArrayListExtra("Latitude",Lat);
                    intent.putStringArrayListExtra("Longitude",Lon);
                    context.startActivity(intent);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(trip.getPlacesArrayList() == null){
                        Toast.makeText(context, "No places to view", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Log.d("bagh", "trips before go to places " + trip.getPlacesArrayList().toString());
                        AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
                        activity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, new PagesListFragment(trip.getPlacesArrayList(), (PagesListFragment.IGotToMain) context), "PagesListFragment")
                                .addToBackStack(null)
                                .commit();
                    }
                }
            });

        }
    }
}
