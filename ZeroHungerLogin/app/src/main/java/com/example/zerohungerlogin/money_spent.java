package com.example.zerohungerlogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class money_spent extends AppCompatActivity {
    ArrayList<String> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_spent);

        ListView list_of_donors = findViewById(R.id.list_of_donors);
        notes.add("money spent1");
        notes.add("money spent2");
        notes.add("money spent3");
        notes.add("money spent4");
        notes.add("money spent5");
        notes.add("money spent6");
        notes.add("money spent7");
        notes.add("money spent8");
        notes.add("money spent9");
        notes.add("money spent10");
        notes.add("money spent11");
        notes.add("money spent12");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,notes);
        list_of_donors.setAdapter(arrayAdapter);

        list_of_donors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),ListAtivity3.class);
                intent.putExtra("noteId",i);
                startActivity(intent);
            }
        });
    }
}