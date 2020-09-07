package com.example.zerohungerlogin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.zip.Inflater;import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

public class StartActivity extends OptionsMenuActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG="StartActivity";

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Toolbar toolbar;
    CircularImageView userphoto;
    TextView username;
    String name;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        NavigationView navigationView = findViewById(R.id.navigation_drawer);
        navigationView.setNavigationItemSelectedListener(this);
        View hview = navigationView.getHeaderView(0);
        username = hview.findViewById(R.id.navigationUsername);
        userphoto = hview.findViewById(R.id.navigationUserPhoto);

        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"you are going to donations page",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MoneyDonationActivity.class));
            }
        });

        initView();
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.drawer_open,R.string.drawer_close);
        toggle.syncState();
        drawer.addDrawerListener(toggle);

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


        storageReference = FirebaseStorage.getInstance().getReference().child("pics/" + uid + ".jpeg");

        try {
            final File localfile = File.createTempFile(uid,"jpeg");
            storageReference.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                    userphoto.setImageBitmap(bitmap);
                    //Toast.makeText(getApplicationContext(),"Pic retrieved Successfully",Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //Toast.makeText(getApplicationContext(),"Error Occurred",Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void initView() {

        Log.d(TAG,"initView: started");
        drawer =(DrawerLayout)findViewById(R.id.drawer);
        navigationView =(NavigationView)findViewById(R.id.navigation_drawer);
        toolbar = (Toolbar)findViewById(R.id.toolbar);

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
            case R.id.donatefood:
                //Toast.makeText(getApplicationContext(),"Item 1 selected",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),DonateFoodActivity.class);
                //intent.putExtra("noteId",i);
                startActivity(intent);
                break;

            case R.id.fooddonors:
                //Toast.makeText(getApplicationContext(),"Item 2 selected",Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(getApplicationContext(),food_donors.class);
                //intent.putExtra("noteId",i);
                startActivity(intent1);
                break;

            case R.id.moneydonors:
                //Toast.makeText(getApplicationContext(),"Item 3 selected",Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(),"Item 2 selected",Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(getApplicationContext(),money_donors.class);
                //intent.putExtra("noteId",i);
                startActivity(intent2);
                break;


            case R.id.moneyspent:
                //Toast.makeText(getApplicationContext(),"Item 4 selected",Toast.LENGTH_SHORT).show();
                Intent intent3 = new Intent(getApplicationContext(),money_spent.class);
                //intent.putExtra("noteId",i);
                startActivity(intent3);
                break;

            case R.id.settings:
                startActivity(new Intent(getApplicationContext(),SettingsActivity.class));
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.navigation_drawer);
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }
}