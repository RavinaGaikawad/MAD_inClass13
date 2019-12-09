package com.example.mad_inclass13;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlacesRecyclerView extends RecyclerView.Adapter<PlacesRecyclerView.MyViewHolder> {
    ArrayList<Places> places;
    private PlacesRecyclerInterface mplacesRecyclerInterface;

    public PlacesRecyclerView(ArrayList<Places> places, PlacesRecyclerInterface placesRecyclerInterface) {
        this.places = places;
        this.mplacesRecyclerInterface = placesRecyclerInterface;
    }

    @NonNull
    @Override
    public PlacesRecyclerView.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.places_list, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlacesRecyclerView.MyViewHolder holder, int position) {
        final Places place = places.get(position);

        holder.tv_placename.setText(place.placeName);
        //set with picasso
        Picasso.get().load(place.getImageURL()).into(holder.iv_placesicon);
        holder.place = place;

        holder.bt_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mplacesRecyclerInterface.gotoMainFromPlaces(place);
            }
        });
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_placesicon;
        TextView tv_placename;
        Button bt_select;
        Places place;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_placename = itemView.findViewById(R.id.tv_placename);
            bt_select = itemView.findViewById(R.id.bt_addplacerecy);
            iv_placesicon = itemView.findViewById(R.id.iv_placeicon);
        }
    }

    public interface PlacesRecyclerInterface{
        void gotoMainFromPlaces(Places place);
    }
}
