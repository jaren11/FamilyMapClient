package com.example.familymapapplication.Activities.Fragments.Activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.familymapapplication.Activities.Fragments.AdaptersAndHolders.SearchAdapter;
import com.example.familymapapplication.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.familymapapplication.Activities.Fragments.Models.DataCache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Model.*;

public class SearchActivity extends AppCompatActivity {

    private EditText searchBar;
    private Button searchButton;
    private String input;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter searchAdapter;

    private DataCache dataCache = DataCache.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        searchBar = findViewById(R.id.search_text);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                input = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

        recyclerView = findViewById(R.id.list_search_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void update(){
        List<Object> objectList = new ArrayList<>();

        Map<String, Person> availablePeople = dataCache.getThePersons();
        getListOfPersons(availablePeople, objectList);

        Map<String, Event> availableEvents = dataCache.getDisplayedEvents();
        getListOfEvents(availableEvents, objectList);

        if (objectList.size() != 0) {
            searchAdapter = new SearchAdapter(objectList, this);
            recyclerView.setAdapter(searchAdapter);
        }
    }

    private void getListOfPersons(Map<String, Person> persons, List<Object> objects){
        for(Person person: persons.values()){
            if(person.getFirstName().toLowerCase().contains(input.toLowerCase())){
                objects.add(person);
            }else if(person.getLastName().toLowerCase().contains(input.toLowerCase())){
                objects.add(person);
            }
        }
    }

    private void getListOfEvents(Map<String, Event> events, List<Object> objects){
        for(Event event: events.values()){
            if(event.getEventType().toLowerCase().contains(input.toLowerCase())){
                objects.add(event);
            }else if(event.getCountry().toLowerCase().contains(input.toLowerCase())){
                objects.add(event);
            }else if(event.getCity().toLowerCase().contains(input.toLowerCase())){
                objects.add(event);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
