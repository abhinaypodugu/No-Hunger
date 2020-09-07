package com.example.zerohungerlogin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class SettingsActivity extends AppCompatActivity {
    TextView notifications,help,invitefriend,username;
    CardView cardView2,cardView3,cardView4;
    Switch sw;
    CircularImageView userphoto;
    FirebaseAuth fauth;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    private final int IMG_REQUEST_ID = 10;
    private Uri imageuri;
    String name;
    ArrayList<Object> UserInfo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        notifications = findViewById(R.id.notiTextView);
        help = findViewById(R.id.helpTextView);
        invitefriend = findViewById(R.id.inviteFriendTextView);
        cardView2 = findViewById(R.id.cardview2);
        cardView3 = findViewById(R.id.cardview3);
        userphoto = findViewById(R.id.userPhoto);
        cardView4 = findViewById(R.id.cardview4);
        username= findViewById(R.id.usernameTextView);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
        GoogleSignInAccount accCheck = GoogleSignIn.getLastSignedInAccount(this);
        if(accCheck!=null){
            username.setText(accCheck.getGivenName());
        }
        else {
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    name = snapshot.child("username").toString();
                    username.setText(name);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


        storageReference = FirebaseStorage.getInstance().getReference().child("pics/"+uid+".jpeg");

        try {
            final File localfile = File.createTempFile(uid,"jpeg");
            storageReference.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                    userphoto.setImageBitmap(bitmap);
                    Toast.makeText(getApplicationContext(),"Pic retrieved Successfully",Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),"Error Occurred",Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Wait for FAQ pages, Thank you...",Toast.LENGTH_SHORT).show();
            }
        });
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Wait for FAQ pages, Thank you...",Toast.LENGTH_SHORT).show();
            }
        });

        userphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                if(intent.resolveActivity(getPackageManager())!=null){
//                    startActivityForResult(intent, IMG_REQUEST_ID);
//                }
                requestImage();
            }
        });
    }


    private void requestImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),IMG_REQUEST_ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMG_REQUEST_ID && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageuri=data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageuri);
                userphoto.setImageBitmap(bitmap);
                saveInFirebase();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(),"Error Occured!!",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveInFirebase() {
        if(imageuri!=null){
            final ProgressDialog pd = new ProgressDialog(this);
            pd.setTitle("Please wait...");
            pd.show();
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            final StorageReference reference =FirebaseStorage.getInstance().getReference().child("pics").child(uid+".jpeg");
            try {
                reference.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Uploaded Successfully..", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Upload Failed..", Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        //double progress = ((100.0 * snapshot.getBytesTransferred()) / (snapshot.getTotalByteCount()));
                        pd.setMessage("Saved "+ (int) ((100.0 * snapshot.getBytesTransferred()) / (snapshot.getTotalByteCount())) + "%");
                        //pd.getProgress();
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == IMG_REQUEST_ID && resultCode==RESULT_OK && data!=null && data.getData()!=null){
//            imageuri=data.getData();
//            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
//            userphoto.setImageBitmap(bitmap);
//            saveInFirebase(bitmap);
//
//        }
//    }
//
//    private void saveInFirebase(Bitmap bitmap) {
//        final ProgressDialog pd = new ProgressDialog(this);
//        pd.setTitle("Please wait...");
//        pd.show();
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
//        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        final StorageReference reference =FirebaseStorage.getInstance().getReference().child("pics").child(uid+".jpeg");
//        reference.putBytes(baos.toByteArray()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                pd.dismiss();
//                Toast.makeText(getApplicationContext(), "Uploaded Successfully..", Toast.LENGTH_SHORT).show();
//                getDownloadurl(reference);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                pd.dismiss();
//                Toast.makeText(getApplicationContext(), "Upload Failed..", Toast.LENGTH_SHORT).show();
//
//            }
//        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
//                pd.setMessage("Saved "+ (int) progress + "%");
//            }
//        });
//    }
//
//    private void getDownloadurl(StorageReference reference) {
//        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                setUserProfileUrl(uri);
//            }
//        });
//    }
//    private void setUserProfileUrl(Uri uri){
//        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        UserProfileChangeRequest request;
//        request = new UserProfileChangeRequest();//todo
//    }
}