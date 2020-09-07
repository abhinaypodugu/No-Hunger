package com.example.zerohungerlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText username, maddress, mobnumber, password;
    Button registerbtn;
    TextView logintxt;

    FirebaseAuth fauth;
    DatabaseReference reference;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username= findViewById(R.id.editTextTextPersonName);
        maddress= findViewById(R.id.editTextTextEmailAddress);
        mobnumber= findViewById(R.id.editTextPhone);
        password = findViewById(R.id.editTextTextPassword);
        registerbtn = findViewById(R.id.button3);
        logintxt = findViewById(R.id.regTextView);


        fauth = FirebaseAuth.getInstance();

        logintxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd = new ProgressDialog(RegisterActivity.this);
                pd.setMessage("Please Wait.....");
                pd.show();

                String str_username = username.getText().toString();
                String str_password = password.getText().toString();
                String str_mailaddress= maddress.getText().toString();
                String str_phonenumber = mobnumber.getText().toString();

                if(TextUtils.isEmpty(str_username) || TextUtils.isEmpty(str_password) || TextUtils.isEmpty(str_mailaddress) || TextUtils.isEmpty(str_phonenumber)){
                    Toast.makeText(getApplicationContext(),"All Fields are Required",Toast.LENGTH_SHORT).show();
                    pd.dismiss();

                }
                else if(str_password.length()<6){
                    Toast.makeText(getApplicationContext(),"Password must have atleast 6 characters",Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
                else{
                    register(str_username,str_mailaddress,str_password,str_phonenumber);
                }



            }
        });
    }

    private void register(final String username, String maddress, final String password, String mobnumber){
        fauth.createUserWithEmailAndPassword(maddress,password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser firebaseuser = fauth.getCurrentUser();
                    String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

                    HashMap<String, Object> hashmap = new HashMap<>();
                    hashmap.put("id",userid);
                    hashmap.put("username", username);
                    hashmap.put("Bio", ""); //TODO
                    hashmap.put("imageurl", FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl());

                    reference.setValue(hashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                pd.dismiss();
                                Intent intent = new Intent(RegisterActivity.this,StartActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }
                    });

                }
                else{
                    pd.dismiss();
                    Toast.makeText(getApplicationContext(),"You cant register with this email or password",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}