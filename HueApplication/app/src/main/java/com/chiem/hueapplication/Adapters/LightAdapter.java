package com.chiem.hueapplication.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chiem.hueapplication.ConnectedActivity;
import com.chiem.hueapplication.Models.Connection;
import com.chiem.hueapplication.Models.Light;
import com.chiem.hueapplication.R;
import com.chiem.hueapplication.SingleLightActivity;

import java.util.ArrayList;

public class LightAdapter extends RecyclerView.Adapter<LightAdapter.LightsViewHolder> {

    private ArrayList<Light> dataset;

    // Constructor
    public LightAdapter(ArrayList<Light> dataset) {
        this.dataset = dataset;
    }

    @NonNull
    @Override
    public LightAdapter.LightsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        //View view = inflater.inflate(R.layout.gwb_row, viewGroup, false);
        View view = inflater.inflate(R.layout.light_layout, viewGroup, false);
        return new LightAdapter.LightsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LightAdapter.LightsViewHolder holder, int i) {
        final Light light = dataset.get(i);

        holder.status.setText(light.getLightState().isOn() + "");
        holder.id.setText(light.getName());
    }

    @Override
    public int getItemCount() {
        //return 0;
        return this.dataset.size();
    }

    public class LightsViewHolder extends RecyclerView.ViewHolder {

        public TextView id;
        public TextView status;


        public View layout;

        // Dit is een rij in de recycler view
        public LightsViewHolder(@NonNull View itemView) {
            super(itemView);
            this.layout = itemView;

            this.id = itemView.findViewById(R.id.txtLightId);
            this.status = itemView.findViewById(R.id.txtStatus);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), SingleLightActivity.class);

                    // Get GWB object waarop is geclicked
                    Light light = dataset.get(LightAdapter.LightsViewHolder.super.getAdapterPosition());
                    intent.putExtra("LIGHT", light);

                    // Start de nieuwe activity
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

}