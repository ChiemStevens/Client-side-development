package com.chiem.alameringen.Adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chiem.alameringen.Activitys.MainActivity;
import com.chiem.alameringen.Fragments.EmergencyDetailFragment;
import com.chiem.alameringen.Models.Emergency;
import com.chiem.alameringen.R;

import java.util.ArrayList;

public class EmergencyAdapter extends RecyclerView.Adapter<EmergencyAdapter.ConnectionsViewHolder> {

    private ArrayList<Emergency> dataset;

    // Constructor
    public EmergencyAdapter(ArrayList<Emergency> dataset) {
        this.dataset = dataset;
    }

    @NonNull
    @Override
    public ConnectionsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        //View view = inflater.inflate(R.layout.gwb_row, viewGroup, false);
        View view = inflater.inflate(R.layout.emergency_layout, viewGroup, false);
        return new ConnectionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConnectionsViewHolder holder, int i) {
        final Emergency emergency = dataset.get(i);
        holder.place.setText(emergency.getPlace());
        holder.time.setText(emergency.getDate());

        switch (emergency.getIcon().toLowerCase()) {
            case "ambulance":
                holder.imageView.setBackgroundResource(R.drawable.ic_emergency_icon);
                break;
            case "brandweer":
                holder.imageView.setBackgroundResource(R.drawable.ic_fire_truck);
                break;
            case "politie":
                holder.imageView.setBackgroundResource(R.drawable.ic_police_car);
                break;
        }
    }

    @Override
    public int getItemCount() {
        //return 0;
        return this.dataset.size();
    }

    public class ConnectionsViewHolder extends RecyclerView.ViewHolder {

        public TextView place;
        public TextView time;
        public ImageView imageView;

        public View layout;

        // Dit is een rij in de recycler view
        public ConnectionsViewHolder(@NonNull View itemView) {
            super(itemView);
            this.layout = itemView;

            this.place = itemView.findViewById(R.id.txtRegion);
            this.time = itemView.findViewById(R.id.txtTime);
            this.imageView = itemView.findViewById(R.id.imgEmergencyIcon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Emergency emergency = dataset.get(ConnectionsViewHolder.super.getAdapterPosition());

                    MainActivity activity = (MainActivity) view.getContext();

                    EmergencyDetailFragment emergencyDetailFragment = new EmergencyDetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("EMERGENCY", emergency);
                    emergencyDetailFragment.setArguments(bundle);
                    activity.openFragment(emergencyDetailFragment);
                }
            });
        }
    }

}