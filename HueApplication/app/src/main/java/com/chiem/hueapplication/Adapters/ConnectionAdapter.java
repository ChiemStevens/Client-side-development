package com.chiem.hueapplication.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chiem.hueapplication.Activitys.ConnectedActivity;
import com.chiem.hueapplication.Models.Connection;
import com.chiem.hueapplication.R;

import java.util.ArrayList;

public class ConnectionAdapter extends RecyclerView.Adapter<ConnectionAdapter.ConnectionsViewHolder> {

    private ArrayList<Connection> dataset;

    // Constructor
    public ConnectionAdapter(ArrayList<Connection> dataset) {
        this.dataset = dataset;
    }

    @NonNull
    @Override
    public ConnectionsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        //View view = inflater.inflate(R.layout.gwb_row, viewGroup, false);
        View view = inflater.inflate(R.layout.connection_layout, viewGroup, false);
        return new ConnectionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConnectionsViewHolder holder, int i) {
        final Connection connection = dataset.get(i);
        holder.name.setText(connection.getName());
        holder.ip.setText(connection.getIp());
        holder.port.setText(connection.getPort());

        if(connection.isEmulator()) {
            holder.type.setText("Emulator");
        }
        else {
            holder.type.setText("Bridge");
        }


    }

    @Override
    public int getItemCount() {
        //return 0;
        return this.dataset.size();
    }

    public class ConnectionsViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView ip;
        public TextView port;
        public TextView type;


        public View layout;

        // Dit is een rij in de recycler view
        public ConnectionsViewHolder(@NonNull View itemView) {
            super(itemView);
            this.layout = itemView;

            this.name = itemView.findViewById(R.id.lblName);
            this.ip = itemView.findViewById(R.id.lblIp);
            this.port = itemView.findViewById(R.id.lblPort);
            this.type = itemView.findViewById(R.id.lblType);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), ConnectedActivity.class);

                    // Get GWB object waarop is geclicked
                    Connection connection = dataset.get(ConnectionsViewHolder.super.getAdapterPosition());
                    intent.putExtra("CONNECTION", connection);

                    // Start de nieuwe activity
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

}