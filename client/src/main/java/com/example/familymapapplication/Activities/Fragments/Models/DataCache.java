package com.example.familymapapplication.Activities.Fragments.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Model.Event;
import Model.Person;
import Result.EventResult;
import Result.PersonResult;

public class DataCache {

    private Map<String, Person> thePersons;
    private Map<String, Event> theEvents;

    private Settings settings;

    private Map<String, Event> eventsDisplayed;
    private Map<String, List<Event>> allEventLists;
    private Person user;

    private String host;
    private String port;
    private String authtoken;
    private String personID;

    private Person currentPerson;
    private Event currentEvent;

    private List<String> eventTypes;
    private Map <String, ColorOfMap> eventColor;

    private String personMessage;
    private String eventMessage;

    private boolean isPerson;
    private boolean isEvent;

    private Set<String> motherSide;
    private Set<String> fatherSide;
    private Map<String, Person> progeny;

    private static DataCache instance;

    public static DataCache getInstance(){
        if(instance == null){
            instance = new DataCache();
        }
        return instance;
    }

    public Map<String, Person> getThePersons() {
        return thePersons;
    }

    public void setThePersons(Map<String, Person> thePersons) {
        this.thePersons = thePersons;
    }

    public Map<String, Event> getTheEvents() {
        return theEvents;
    }

    public void setTheEvents(Map<String, Event> theEvents) {
        this.theEvents = theEvents;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public Map<String, Event> getEventsDisplayed() {
        return eventsDisplayed;
    }

    public void setEventsDisplayed(Map<String, Event> eventsDisplayed) {
        this.eventsDisplayed = eventsDisplayed;
    }

    public Map<String, List<Event>> getAllEventLists() {
        return allEventLists;
    }

    public void setAllEventLists(Map<String, List<Event>> allEventLists) {
        this.allEventLists = allEventLists;
    }

    public Person getUser() {
        return user;
    }

    public void setUser(Person user) {
        this.user = user;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public Person getCurrentPerson() {
        return currentPerson;
    }

    public void setCurrentPerson(Person currentPerson) {
        this.currentPerson = currentPerson;
    }

    public Event getCurrentEvent() {
        return currentEvent;
    }

    public void setCurrentEvent(Event currentEvent) {
        this.currentEvent = currentEvent;
    }

    public List<String> getEventTypes() {
        return eventTypes;
    }

    public void setEventTypes(List<String> eventTypes) {
        this.eventTypes = eventTypes;
    }

    public Map<String, ColorOfMap> getEventColor() {
        return eventColor;
    }

    public void setEventColor(Map<String, ColorOfMap> eventColor) {
        this.eventColor = eventColor;
    }

    public String getPersonMessage() {
        return personMessage;
    }

    public void setPersonMessage(String personMessage) {
        this.personMessage = personMessage;
    }

    public String getEventMessage() {
        return eventMessage;
    }

    public void setEventMessage(String eventMessage) {
        this.eventMessage = eventMessage;
    }

    public boolean isPerson() {
        return isPerson;
    }

    public void setPerson(boolean person) {
        isPerson = person;
    }

    public boolean isEvent() {
        return isEvent;
    }

    public void setEvent(boolean event) {
        isEvent = event;
    }

    public Set<String> getMotherSide() {
        return motherSide;
    }

    public void setMotherSide(Set<String> motherSide) {
        this.motherSide = motherSide;
    }

    public Set<String> getFatherSide() {
        return fatherSide;
    }

    public void setFatherSide(Set<String> fatherSide) {
        this.fatherSide = fatherSide;
    }

    public Map<String, Person> getProgeny() {
        return progeny;
    }

    public void setProgeny(Map<String, Person> progeny) {
        this.progeny = progeny;
    }

    public List<Person> findAllRelatives(String personID){
        Person person = getThePersons().get(personID);
        List<Person> list = new ArrayList<>();

        if(getThePersons().get(person.getSpouseID()) != null){
            list.add(getThePersons().get(person.getSpouseID()));
        }
        if(getThePersons().get(person.getMotherID()) != null){
            list.add(getThePersons().get(person.getMotherID()));
        }
        if(getThePersons().get(person.getFatherID()) != null){
            list.add(getThePersons().get(person.getFatherID()));
        }
        if(getProgeny().get(person.getPersonID()) != null){
            list.add(getProgeny().get(person.getPersonID()));
        }

        return list;
    }

    public Map<String, Event> getDisplayedEvents()
    {
        eventsDisplayed = new HashMap<>();

        for (Event event: theEvents.values()) {
            Person person = getThePersons().get(event.getPersonID());
            if (!isDisplayed(person)){
            }
            else if (!settings.checkType(event.getEventType())){
            }
            else {
                eventsDisplayed.put(event.getEventID(), event);
            }
        }
        return eventsDisplayed;
    }

    public List<Event> sort(List<Event> eventList){
        List<Event> sortedEventsList = new ArrayList<>();
        List<Event> currArrayList = new ArrayList<>(eventList);

        while(currArrayList.size() > 0){
            Event currEvent = currArrayList.get(0);
            int index = 0;
            for (int i = 0; i < currArrayList.size(); i++){
                if (currArrayList.get(i).getYear() < currEvent.getYear()){
                    currEvent = currArrayList.get(i);
                    index = i;
                }
            }
            sortedEventsList.add(currEvent);
            currArrayList.remove(index);
        }
        return sortedEventsList;
    }

    public boolean isDisplayed(Person person){
        if (!settings.isMen() && person.getGender().toLowerCase().equals("m")){
            return false;
        }
        else if (!settings.isWomen() && person.getGender().toLowerCase().equals("f")){
            return false;
        }
        else if (!settings.isFatherSide() && fatherSide.contains(person.getPersonID())) {
            return false;
        }
        else return settings.isMotherSide() || !motherSide.contains(person.getPersonID());
    }

    public void initializeAllTheData(){
        //initialize event types
        ArrayList<Event> eventArrayList = new ArrayList<>();
        for(Event event: theEvents.values()){
            eventArrayList.add(event);
        }

        eventColor = new HashMap<>();
        eventTypes = new ArrayList<>();

        for(int i = 0; i < eventArrayList.size(); i++){
            if(!eventColor.containsKey(eventArrayList.get(i).getEventType().toLowerCase())){
                eventColor.put(eventArrayList.get(i).getEventType().toLowerCase(),
                        new ColorOfMap(eventArrayList.get(i).getEventType().toLowerCase()));
                eventTypes.add(eventArrayList.get(i).getEventType().toLowerCase());
            }
        }
        instance.setEventTypes(eventTypes);

        //initialize dad tree
        fatherSide = new HashSet<>();
        initializeHelper(user.getFatherID(), fatherSide);

        //initialize mom tree
        motherSide = new HashSet<>();
        initializeHelper(user.getMotherID(), motherSide);

        //initialize all events
        allEventLists = new HashMap<>();
        for(Person person: thePersons.values()){
            ArrayList<Event> eventList = new ArrayList<>();

            for(Event event: theEvents.values()){
                if(person.getPersonID().equals(event.getPersonID())){
                    eventList.add(event);
                }
            }
            allEventLists.put(person.getPersonID(), eventList);
        }

        //initialize progeny
        progeny = new HashMap<>();
        for(Person person: thePersons.values()){
            if(person.getFatherID() != null){
                progeny.put(person.getFatherID(), person);
            }
            if(person.getMotherID() != null){
                progeny.put(person.getMotherID(), person);
            }
        }

        if(settings == null){
            settings = new Settings();
        }
    }

    public boolean putInPeople(PersonResult personResult){
        if (personResult.getMessage() == null){
            Map<String, Person> theMap = new HashMap<>();
            ArrayList<Person> personArray = personResult.getData();
            this.setUser(personArray.get(0));
            this.setCurrentPerson(personArray.get(0));

            for(int i = 0; i < personArray.size(); i++){
                String personID = personArray.get(i).getPersonID();
                theMap.put(personID, personArray.get(i));
            }

            this.setThePersons(theMap);
            return true;
        }
        return false;
    }

    public boolean putInEvents(EventResult eventResult) {
        if (eventResult.getMessage() == null) {
            Map<String, Event> theMap = new HashMap<>();
            ArrayList<Event> eventArray = eventResult.getData();
            this.setCurrentEvent(eventArray.get(0));

            for (int i = 0; i < eventArray.size(); i++) {
                String personID = eventArray.get(i).getPersonID();
                theMap.put(personID, eventArray.get(i));
            }

            this.setTheEvents(theMap);
            this.setEventsDisplayed(theMap);
            return true;
        }
        return false;
    }

    private void initializeHelper(String personID, Set<String> setPeople){
        if(personID == null){
            return;
        }
        setPeople.add(personID);
        Person person = thePersons.get(personID);

        if(person.getFatherID() != null){
            //recursive call to initializeHelper
            initializeHelper(person.getFatherID(), setPeople);
        }

        if(person.getMotherID() != null){
            //recursive call to initializeHelper
            initializeHelper(person.getMotherID(), setPeople);
        }
    }
}
