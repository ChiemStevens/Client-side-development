package com.chiem.alameringen.Adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chiem.alameringen.Activitys.EmergencyDetailActivity;
import com.chiem.alameringen.Helpers.DatabaseManager;
import com.chiem.alameringen.Models.Emergency;
import com.chiem.alameringen.Models.Place;
import com.chiem.alameringen.R;

import java.util.ArrayList;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {

    private ArrayList<Place> dataset;

    // Constructor
    public PlaceAdapter(ArrayList<Place> dataset) {
        this.dataset = dataset;
    }

    @NonNull
    @Override
    public PlaceAdapter.PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        //View view = inflater.inflate(R.layout.gwb_row, viewGroup, false);
        View view = inflater.inflate(R.layout.place_layout, viewGroup, false);
        return new PlaceAdapter.PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceAdapter.PlaceViewHolder holder, int i) {

        final Place place = dataset.get(i);

        holder.txtPlace.setText(place.getName());

        if(i == 0) {
            holder.btn.setVisibility(View.INVISIBLE);
            holder.txtPlace.append(" (Huidge locatie)");
        }
    }

    @Override
    public int getItemCount() {
        //return 0;
        return this.dataset.size();
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder {

        public View layout;

        public TextView txtPlace;
        public Button btn;

        // Dit is een rij in de recycler view
        public PlaceViewHolder(@NonNull final View itemView) {
            super(itemView);
            this.layout = itemView;
            this.txtPlace = itemView.findViewById(R.id.txtPlace);
            this.btn = itemView.findViewById(R.id.btnRemove);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseManager databaseManager = new DatabaseManager(itemView.getContext());
                    Place place = dataset.get(PlaceAdapter.PlaceViewHolder.super.getAdapterPosition());
                    databaseManager.removePlace(place);

                    try {
                        dataset.remove(getAdapterPosition());

                        notifyItemChanged(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(), dataset.size());
                    } catch (Exception e) {
                        Log.e("Error", e.getMessage());
                    }

                }
            });
        }
    }

}