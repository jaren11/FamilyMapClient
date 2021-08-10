package com.example.familymapapplication.Activities.Fragments.Models;

import android.graphics.Color;

public class ColorOfMap extends Color {
    private float color;

    public ColorOfMap(String eventType) {
        color = Math.abs(eventType.hashCode() % 360);
    }

    public float getColor() {
        return color;
    }

    public void setColor(float color) {
        this.color = color;
    }
}
