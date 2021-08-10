package com.example.familymapapplication.Activities.Fragments.Activities.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.familymapapplication.Activities.Fragments.Activities.SettingsActivity;
import com.example.familymapapplication.Activities.Fragments.Activities.PersonActivity;
import com.example.familymapapplication.Activities.Fragments.Activities.SearchActivity;
import com.example.familymapapplication.Activities.Fragments.Models.DataCache;
import com.example.familymapapplication.Activities.Fragments.Models.ColorOfMap;
import com.example.familymapapplication.Activities.Fragments.Models.Settings;
import com.example.familymapapplication.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.Event;
import Model.Person;

public class MapFragment  extends Fragment implements OnMapReadyCallback{

    private GoogleMap map;
    private Marker currentMarker;
    private SupportMapFragment supportMapFragment;
    private Map<Marker, Event> markerEventMap;
    private Map<String, Event> currentEvents;

    private ImageView theIcon;
    private boolean isEvent;

    private TextView theName;
    private TextView theEvent;
    private TextView theYear;

    private List<Polyline> polylineList;

    private DataCache dataCache = DataCache.getInstance();

    public MapFragment(){}

    public MapFragment(String event){
        isEvent = event != null;
    }

    View.OnClickListener onClickText = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            textClicked();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(!isEvent);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.layout_map, container, false);

        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.theMap);
        supportMapFragment.getMapAsync(this);

        //set all variables
        theIcon = view.findViewById(R.id.map_icon);
        theEvent = view.findViewById(R.id.event_details);
        theYear = view.findViewById(R.id.year);
        theName = view.findViewById(R.id.person_name);

        polylineList = new ArrayList<>();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(markerEventMap != null && map != null){
            clearMap();
            Event event = markerEventMap.get(currentMarker);
            setUpMarkers(map);

            if(currentMarker == null){
                if(!markerEventMap.containsValue(event)){
                    clearLines();
                }
            }
        }

        if(currentMarker != null && markerEventMap != null){
            setUpLines();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menus, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.menu_item_search:
                searchClicked();
                return true;
            case R.id.menu_item_settings:
                settingsClicked();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        setUpMarkers(map);
    }


    private void searchClicked()
    {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        startActivity(intent);
    }

    private void settingsClicked()
    {
        Intent intent = new Intent(getActivity(), SettingsActivity.class);
        startActivity(intent);
    }

    private void textClicked()
    {
        Intent intent = new Intent(getActivity(), PersonActivity.class);
        Person person = dataCache.getThePersons().get(markerEventMap.get(currentMarker).getPersonID());
        dataCache.setCurrentPerson(person);
        startActivity(intent);
    }

    private void setUpMarkers(GoogleMap googleMap)
    {
        currentMarker = null;
        markerEventMap = new HashMap<>();
        Map<String, ColorOfMap> allMapColors = dataCache.getEventColor();
        currentEvents = dataCache.getDisplayedEvents();
        map = googleMap;

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker)
            {
                clickMarker(marker);
                return true;
            }
        });

        for (Event currEvent : currentEvents.values()) {
            LatLng currentPosition = new LatLng(currEvent.getLatitude(), currEvent.getLongitude());
            ColorOfMap mapColor = allMapColors.get(currEvent.getEventType().toLowerCase());

            Marker marker = map.addMarker(new MarkerOptions().position(currentPosition).icon(BitmapDescriptorFactory.defaultMarker(mapColor.getColor()))
                    .title(currEvent.getEventType()));
            markerEventMap.put(marker, currEvent);

            if (dataCache.getCurrentEvent() == currEvent){
                currentMarker = marker;
            }
        }

        if (currentMarker != null && isEvent){
            map.moveCamera(CameraUpdateFactory.newLatLng(currentMarker.getPosition()));
             clickMarker(currentMarker);
        }
    }

    private void setUpLines(){
        Settings settings = DataCache.getInstance().getSettings();
        clearLines();

        if(settings.isSpouseLines()){
            //set up spouse lines
            setUpSpouseLines();
        }
        if(settings.isFamilyTreeLines()){
            //set up family tree lines
            setUpFamilyTreeLines();
        }
        if(settings.isLifeStoryLines()){
            //set up life story lines
            setUpLifeStoryLines();
        }
    }

    private void setUpSpouseLines(){
        Event event = markerEventMap.get(currentMarker);
        Person person = dataCache.getThePersons().get(event.getPersonID());
        List<Event> eventList = dataCache.getAllEventLists().get(person.getSpouseID());
        eventList = dataCache.sort(eventList);
        Settings settings = dataCache.getSettings();

        if(settings.checkType(event.getEventType())){
            for(int i = 0; i < eventList.size(); i++){
                if(dataCache.getEventsDisplayed().containsValue(eventList.get(i))){
                    Event spouseEvent = eventList.get(i);

                    Polyline line = map.addPolyline(new PolylineOptions()
                            .add(new LatLng(spouseEvent.getLatitude(), spouseEvent.getLongitude()),
                                    new LatLng(event.getLatitude(), event.getLongitude()))
                            .color(dataCache.getSettings().getSpouseColor()));
                    polylineList.add(line);

                    return;
                }
            }
        }
    }

    private void setUpFamilyTreeLines(){
        Event event = markerEventMap.get(currentMarker);
        Person person = dataCache.getThePersons().get(event.getPersonID());

        familyTreeHelper(person, event, 10);
    }

    private void familyTreeHelper(Person person, Event event, int generationsAdded){
        if(person.getFatherID() != null){
            List<Event> eventList = dataCache.getAllEventLists().get(person.getFatherID());
            eventList = dataCache.sort(eventList);

            for (int i = 0; i < eventList.size(); i++) {
                if (currentEvents.containsValue(eventList.get(i))) {
                    Event validEvent = eventList.get(i);

                    Polyline newestLine = map.addPolyline(new PolylineOptions()
                            .add(new LatLng(event.getLatitude(), event.getLongitude()),
                                    new LatLng(validEvent.getLatitude(), validEvent.getLongitude()))
                            .color(dataCache.getSettings().getFamilyTreeColor())
                            .width(generationsAdded));
                    polylineList.add(newestLine);

                    Person father = dataCache.getThePersons().get(person.getFatherID());

                    //recursive call to familyTreeHelper
                    familyTreeHelper(father, validEvent, generationsAdded / 2);
                    return;
                }
            }
        }

        if(person.getMotherID() != null){
            List<Event> eventList = dataCache.getAllEventLists().get(person.getMotherID());
            eventList = dataCache.sort(eventList);

            for (int i = 0; i < eventList.size(); i++) {
                if (currentEvents.containsValue(eventList.get(i))) {
                    Event validEvent = eventList.get(i);

                    Polyline line = map.addPolyline(new PolylineOptions()
                            .add(new LatLng(event.getLatitude(), event.getLongitude()),
                                    new LatLng(validEvent.getLatitude(), validEvent.getLongitude()))
                            .color(dataCache.getSettings().getFamilyTreeColor())
                            .width(generationsAdded));
                    polylineList.add(line);

                    Person mother = dataCache.getThePersons().get(person.getMotherID());

                    //recursive call to familyTreeHelper
                    familyTreeHelper(mother, validEvent, generationsAdded / 2);
                    return;
                }
            }
        }
    }

    private void setUpLifeStoryLines(){
        DataCache dataCache = DataCache.getInstance();
        Event event = markerEventMap.get(currentMarker);
        Person person = dataCache.getThePersons().get(event.getPersonID());
        List<Event> eventList = dataCache.getAllEventLists().get(person.getPersonID());
        eventList = dataCache.sort(eventList);

        if(!dataCache.getSettings().checkType(event.getEventType())){
            return;
        }

        setUpFirstStoryLine(eventList);


    }

    private void setUpFirstStoryLine(List<Event> eventList){
        int i = 0;
        while (i < eventList.size() - 1) {
            if (dataCache.getEventsDisplayed().containsValue(currentEvents.get(i))) {
                Event event = eventList.get(i);
                i++;

                setUpSecondStoryLine(event, eventList, i);
            }
            else {
                i++;
            }
        }
    }

    private void setUpSecondStoryLine(Event firstEvent, List<Event> eventList, int i) {

        while (i < eventList.size()) {

            if (dataCache.getEventsDisplayed().containsValue(eventList.get(i))) {
                Event secondEvent = eventList.get(i);

                Polyline newestLine = map.addPolyline(new PolylineOptions()
                        .add(new LatLng(firstEvent.getLatitude(), firstEvent.getLongitude()),
                                new LatLng(secondEvent.getLatitude(), secondEvent.getLongitude()))
                        .color(dataCache.getSettings().getLifeStoryColor()));
                polylineList.add(newestLine);

                return;
            }
            i++;
        }


    }

    private void clickMarker(Marker marker){
        Event currentEvent = markerEventMap.get(marker);
        Person currentPerson = dataCache.getThePersons().get(currentEvent.getPersonID());

        String eventData = currentEvent.getEventType() + ": " + currentEvent.getCity() + ", " + currentEvent.getCountry();
        String yearData = "(" + currentEvent.getYear() + ")";
        String name = currentPerson.getFirstName() + " " + currentPerson.getLastName();

        theEvent.setText(eventData);
        theEvent.setVisibility(View.VISIBLE);
        theEvent.setOnClickListener(onClickText);

        theYear.setText(yearData);
        theYear.setVisibility(View.VISIBLE);
        theYear.setOnClickListener(onClickText);

        theName.setText(name);
        theName.setVisibility(View.VISIBLE);
        theName.setOnClickListener(onClickText);

        if(currentPerson.getGender().toLowerCase().equals("m")){
            theIcon.setImageDrawable(getResources().getDrawable(R.drawable.male_icon));
        }else{
            theIcon.setImageDrawable(getResources().getDrawable(R.drawable.female_icon));
        }
        theIcon.setVisibility(View.VISIBLE);
        theIcon.setOnClickListener(onClickText);

        currentMarker = marker;
        dataCache.setCurrentEvent(currentEvent);
        setUpLines();
    }

    private void clearMap()
    {
        for (Marker currMarker:markerEventMap.keySet()) {
            currMarker.remove();
        }
    }

    private void clearLines()
    {
        for (com.google.android.gms.maps.model.Polyline line : polylineList) {
            line.remove();
        }
        polylineList = new ArrayList<>();
    }

}
