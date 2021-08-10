package com.example.familymapapplication.Activities.Fragments.Models;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;
import java.util.List;

public class Settings {
    private boolean lifeStoryLines;
    private boolean familyTreeLines;
    private boolean spouseLines;
    private int lifeStoryColor;
    private int familyTreeColor;
    private int spouseColor;
    private ArrayList<Integer> settingsSelections;
    private List<String> theEvents;
    private List<String> currentEvents;
    private boolean motherSide;
    private boolean fatherSide;
    private boolean women;
    private boolean men;

    public Settings() {
        this.lifeStoryLines = true;
        this.familyTreeLines = true;
        this.spouseLines = true;
        this.lifeStoryColor = Color.BLUE;
        this.familyTreeColor = Color.GREEN;
        this.spouseColor = Color.RED;
        this.settingsSelections = new ArrayList<>();
        while(settingsSelections.size() != 7){
            settingsSelections.add(0);
        }
        theEvents = new ArrayList<>(DataCache.getInstance().getEventTypes());
        currentEvents = new ArrayList<>(DataCache.getInstance().getEventTypes());
        motherSide = true;
        fatherSide = true;
        women = true;
        men = true;
    }

    public List<String> getTheEvents() {
        return theEvents;
    }

    public void setTheEvents(List<String> theEvents) {
        this.theEvents = theEvents;
    }

    public List<String> getCurrentEvents() {
        return currentEvents;
    }

    public void setCurrentEvents(List<String> currentEvents) {
        this.currentEvents = currentEvents;
    }

    public boolean isMotherSide() {
        return motherSide;
    }

    public void setMotherSide(boolean motherSide) {
        this.motherSide = motherSide;
    }

    public boolean isFatherSide() {
        return fatherSide;
    }

    public void setFatherSide(boolean fatherSide) {
        this.fatherSide = fatherSide;
    }

    public boolean isWomen() {
        return women;
    }

    public void setWomen(boolean women) {
        this.women = women;
    }

    public boolean isMen() {
        return men;
    }

    public void setMen(boolean men) {
        this.men = men;
    }

    public boolean isLifeStoryLines() {
        return lifeStoryLines;
    }

    public void setLifeStoryLines(boolean lifeStoryLines) {
        this.lifeStoryLines = lifeStoryLines;
    }

    public boolean isFamilyTreeLines() {
        return familyTreeLines;
    }

    public void setFamilyTreeLines(boolean familyTreeLines) {
        this.familyTreeLines = familyTreeLines;
    }

    public boolean isSpouseLines() {
        return spouseLines;
    }

    public void setSpouseLines(boolean spouseLines) {
        this.spouseLines = spouseLines;
    }

    public int getLifeStoryColor() {
        return lifeStoryColor;
    }

    public void setLifeStoryColor(int lifeStoryColor) {
        this.lifeStoryColor = lifeStoryColor;
    }

    public int getFamilyTreeColor() {
        return familyTreeColor;
    }

    public void setFamilyTreeColor(int familyTreeColor) {
        this.familyTreeColor = familyTreeColor;
    }

    public int getSpouseColor() {
        return spouseColor;
    }

    public void setSpouseColor(int spouseColor) {
        this.spouseColor = spouseColor;
    }



    public boolean checkType(String eventType){
        eventType = eventType.toLowerCase();
        for(String event: currentEvents){
            if(event.toLowerCase().equals(eventType)){
                return true;
            }
        }
        return false;
    }

    public void deleteType(String eventType){
        eventType = eventType.toLowerCase();
        for(int i = 0; i < currentEvents.size(); i++){
            if(currentEvents.get(i).toLowerCase().equals(eventType)){
                currentEvents.remove(i);
            }
        }
    }

    public void addEvent(String eventType){
        int index = 0;
        eventType = eventType.toLowerCase();
        for (int i = 0; i < theEvents.size(); i++) {
            if (theEvents.get(i).toLowerCase().equals(eventType)){
                index = i;
            }
        }
        currentEvents.add(index, eventType);
    }
}
