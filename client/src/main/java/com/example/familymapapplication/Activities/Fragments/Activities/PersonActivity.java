package com.example.familymapapplication.Activities.Fragments.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.familymapapplication.Activities.Fragments.AdaptersAndHolders.PersonActivityAdapter;
import com.example.familymapapplication.Activities.Fragments.Models.DataCache;
import com.example.familymapapplication.R;

import java.util.ArrayList;
import java.util.List;

import Model.*;

public class PersonActivity extends AppCompatActivity {
    private Person thePerson;
    private TextView theFirstName;
    private TextView theLastName;
    private TextView theGender;
    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private DataCache dataCache = DataCache.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("FamilyMap: Person Details");
        thePerson = dataCache.getCurrentPerson();

        theFirstName = findViewById(R.id.person_first_name);
        theLastName = findViewById(R.id.person_last_name);
        theGender = findViewById(R.id.person_gender);

        theFirstName.setText(thePerson.getFirstName());
        theLastName.setText(thePerson.getLastName());
        theGender.setText(thePerson.getGender().toUpperCase());

        listView = findViewById(R.id.expandable_list_person_activity);

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (groupPosition == 0){
                    Intent intent = new Intent(PersonActivity.this, EventActivity.class);
                    intent.putExtra("Event", "Event");
                    dataCache.setCurrentEvent((Event) listAdapter.getChild(groupPosition, childPosition));
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(PersonActivity.this, PersonActivity.class);
                    dataCache.setCurrentPerson((Person) listAdapter.getChild(groupPosition, childPosition));
                    startActivity(intent);
                }
                return false;
            }
        });

        update();
    }

    private void update()
    {
        List<Person> relatives = new ArrayList<>(dataCache.findAllRelatives(thePerson.getPersonID()));

        List<Event> eventsArrayList = new ArrayList<>(dataCache.getAllEventLists().get(thePerson.getPersonID()));
        eventsArrayList = dataCache.sort(eventsArrayList);

        List<String> headers = new ArrayList<>();
        headers.add("Events");
        headers.add("Relatives");

        eventsArrayList = filterEvents(eventsArrayList);
        relatives = filterPersons(relatives);

        listAdapter = new PersonActivityAdapter(this, headers, eventsArrayList, relatives, thePerson);
        listView.setAdapter(listAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        return true;
    }

    private List<Event> filterEvents(List<Event> eventsList)
    {
        List<Event> testEventList = new ArrayList<>();
        for (Event currEvent: eventsList) {
            if (dataCache.getDisplayedEvents().containsValue(currEvent)){
                testEventList.add(currEvent);
            }
        }
        return testEventList;
    }

    private List<Person> filterPersons(List<Person> personsList)
    {
        List<Person> filteredPersonsList = new ArrayList<>();

        for (Person person: personsList) {
            if (dataCache.isDisplayed(person)){
                filteredPersonsList.add(person);
            }
        }
        return filteredPersonsList;
    }
}
