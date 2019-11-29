package com.chiem.hueapplication.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chiem.hueapplication.Activitys.ConnectedActivity;
import com.chiem.hueapplication.Activitys.SingleLightActivity;
import com.chiem.hueapplication.Models.Connection;
import com.chiem.hueapplication.Models.Light;
import com.chiem.hueapplication.R;

import java.util.ArrayList;

public class PresetAdapter extends RecyclerView.Adapter<PresetAdapter.PresetsViewHolder> {

    private ArrayList<Light> dataset;

    // Constructor
    public PresetAdapter(ArrayList<Light> dataset) {
        this.dataset = dataset;
    }

    @NonNull
    @Override
    public PresetAdapter.PresetsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        //View view = inflater.inflate(R.layout.gwb_row, viewGroup, false);
        View view = inflater.inflate(R.layout.preset_layout, viewGroup, false);
        return new PresetAdapter.PresetsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PresetAdapter.PresetsViewHolder holder, int i) {
        final Light light = dataset.get(i);

        holder.name.setText(light.getName());
    }

    @Override
    public int getItemCount() {
        //return 0;
        return this.dataset.size();
    }

    public class PresetsViewHolder extends RecyclerView.ViewHolder {

        public TextView name;

        public View layout;

        // Dit is een rij in de recycler view
        public PresetsViewHolder(@NonNull View itemView) {
            super(itemView);
            this.layout = itemView;

            this.name = itemView.findViewById(R.id.lblPresetName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), SingleLightActivity.class);

                    // Get GWB object waarop is geclicked
                    Light light = dataset.get(PresetAdapter.PresetsViewHolder.super.getAdapterPosition());
                    intent.putExtra("LIGHT", light);

                    // Start de nieuwe activity
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

}