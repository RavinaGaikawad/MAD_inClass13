package com.example.mad_inclass13;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PagesListRecyclerView extends RecyclerView.Adapter<PagesListRecyclerView.MyViewHolder> {
    ArrayList<Places> placesArrayList;

    public PagesListRecyclerView(ArrayList<Places> placesArrayList) {
        this.placesArrayList = placesArrayList;
    }

    @NonNull
    @Override
    public PagesListRecyclerView.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.added_places_list, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PagesListRecyclerView.MyViewHolder holder, int position) {
        final Places place = placesArrayList.get(position);
        holder.tv_placename.setText(place.getPlaceName());
    }

    @Override
    public int getItemCount() {
        return placesArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_placename;
        Button bt_Delete;
        Button bt_gototrips;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_placename = itemView.findViewById(R.id.tv_pname);
            bt_Delete = itemView.findViewById(R.id.bt_delete);
            bt_gototrips = itemView.findViewById(R.id.bt_gototrips);

            bt_Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    placesArrayList.remove(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        }
    }
}
