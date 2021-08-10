package com.example.familymapapplication.Activities.Fragments.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.familymapapplication.Activities.Fragments.Activities.Fragments.MapFragment;
import com.example.familymapapplication.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String args = getIntent().getExtras().getString("Event");

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment mapFragment = new MapFragment(args);

        fragmentManager.beginTransaction()
                .replace(R.id.fragment_map, mapFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
