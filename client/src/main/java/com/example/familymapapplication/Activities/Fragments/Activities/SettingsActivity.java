package com.example.familymapapplication.Activities.Fragments.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.familymapapplication.Activities.Fragments.AdaptersAndHolders.*;
import com.example.familymapapplication.Activities.Fragments.Models.DataCache;
import com.example.familymapapplication.R;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {
    private TextView logOut;
    private RecyclerView recyclerView;
    private SettingsAdapter settingsAdapter;

    private DataCache dataCache = DataCache.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.settings_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        logOut = findViewById(R.id.logout_text);
        logOut.setClickable(true);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent, 0);
            }
        });

        update();
    }

    private void update(){
        List<String> setting = new ArrayList<>();
        setting.add("Show Life Story");
        setting.add("Family Tree Lines");
        setting.add("Spouse Lines");
        setting.add("Mother's side");
        setting.add("Father's side");
        setting.add("Female events");
        setting.add("Male events");

        settingsAdapter = new SettingsAdapter(setting, this);
        recyclerView.setAdapter(settingsAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        return true;
    }
}
