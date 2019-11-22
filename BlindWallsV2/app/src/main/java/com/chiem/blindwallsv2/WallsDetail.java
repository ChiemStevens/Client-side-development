package com.chiem.blindwallsv2;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chiem.blindwallsv2.Model.BlindWall;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class WallsDetail extends AppCompatActivity {

    private String imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall_detail);

        //PARCELABLE
        //Bundle data = getIntent().getExtras();
        //BlindWall wallsInfo = data.getParcelable("WALLS_OBJECT");

        BlindWall wallsInfo = getIntent().getExtras().getParcelable("WALLS_OBJECT");

        //SERIALIZABLE
        //BlindWall wallsInfo = (BlindWall) getIntent().getSerializableExtra("WALLS_OBJECT");

        ImageView imageView = findViewById(R.id.image);
        imageUri = "https://api.blindwalls.gallery/" + wallsInfo.getImagesUrls().get(0);

        Picasso.get()
                .load(imageUri)
                .into(imageView);


        TextView txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(wallsInfo.getTitle());

        TextView txtAuthor = findViewById(R.id.txtAuthor);
        txtAuthor.setText(wallsInfo.getArtist());

        TextView txtDescription = findViewById(R.id.txtDescription);
        txtDescription.setText(wallsInfo.getDescriptionDutch());

        TextView txtYear = findViewById(R.id.txtYear);
        txtYear.setText(wallsInfo.getYear() + "");


    }

    public void OnImageClick(View v) {
        showImage();
    }

    public void showImage() {
        Dialog builder = new Dialog(this);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //nothing;
            }
        });

        ImageView imageView = new ImageView(this);
        Picasso.get()
                .load(imageUri)
                .into(imageView);

        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();
    }

}
