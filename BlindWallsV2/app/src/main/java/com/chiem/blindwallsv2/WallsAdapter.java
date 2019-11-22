package com.chiem.blindwallsv2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chiem.blindwallsv2.Model.BlindWall;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class WallsAdapter extends RecyclerView.Adapter<WallsAdapter.WallsViewHolder> {

    private ArrayList<BlindWall> dataset;

    // Constructor
    public WallsAdapter(ArrayList<BlindWall> dataset) {
        this.dataset = dataset;
    }

    @NonNull
    @Override
    public WallsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        //View view = inflater.inflate(R.layout.gwb_row, viewGroup, false);
        View view = inflater.inflate(R.layout.wall_layout, viewGroup, false);
        return new WallsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WallsViewHolder holder, int i) {
        final BlindWall wallsInfo = dataset.get(i);
        holder.title.setText(wallsInfo.getTitle());
        String url = wallsInfo.getImagesUrls().get(0);

        Picasso.get()
                .load("https://api.blindwalls.gallery/" + url)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        //return 0;
        return this.dataset.size();
    }

    public class WallsViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public ImageView image;

        public View layout;

        // Dit is een rij in de recycler view
        public WallsViewHolder(@NonNull View itemView) {
            super(itemView);
            this.layout = itemView;

            this.title = itemView.findViewById(R.id.txtTitle);
            this.image = itemView.findViewById(R.id.imgWall);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), WallsDetail.class);

                    // Get GWB object waarop is geclicked
                    BlindWall wallsInfo = dataset.get(WallsViewHolder.super.getAdapterPosition());
                    intent.putExtra("WALLS_OBJECT", wallsInfo);

                    // Start de nieuwe activity
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

}

