package com.example.zerohungerlogin;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class donorAdapter extends FirebaseRecyclerAdapter<food_donor_list, donorAdapter.donorViewfolder> {
String url;


    public donorAdapter(@NonNull FirebaseRecyclerOptions<food_donor_list> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull donorViewfolder holder, int position, @NonNull food_donor_list model) {
        holder.name.setText(model.getName());
        holder.rice.setText(model.getRice());
        holder.curry.setText(model.getCurry());
        holder.uid.setText(model.getuid());
        url = model.geturl();
    }

    @NonNull
    @Override
    public donorViewfolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_donor_list, parent, false);

        return new donorViewfolder(view);
    }

    class donorViewfolder extends RecyclerView.ViewHolder {

        TextView name,address,pin,ph,rice,curry,uid;
        CircularImageView userphoto;

        public donorViewfolder(@NonNull final View itemView)  {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            rice = itemView.findViewById(R.id.rice);
            curry = itemView.findViewById(R.id.curry);
            uid = itemView.findViewById(R.id.uid);
            userphoto = itemView.findViewById(R.id.userphotocard);

            //Picasso.get().load(url).into(userphoto);
        }
    }
}
