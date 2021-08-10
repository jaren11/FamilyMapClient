package com.example.familymapapplication.Activities.Fragments.AdaptersAndHolders;

import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.familymapapplication.Activities.Fragments.Models.Settings;
import com.example.familymapapplication.R;
import com.example.familymapapplication.Activities.Fragments.Models.DataCache;

public class SettingsHolder extends RecyclerView.ViewHolder {
    private TextView settingType;
    private TextView settingDescription;
    private Switch theSwitch;
    private Settings theSettings = DataCache.getInstance().getSettings();

    public SettingsHolder(View itemView)
    {
        super(itemView);

        settingType = itemView.findViewById(R.id.setting);
        settingDescription = itemView.findViewById(R.id.description);
        theSwitch = itemView.findViewById(R.id.setting_switch);
    }

    public Switch getSwitch()
    {
        return theSwitch;
    }

    public void bind(String eventType)
    {
        String eventTypeText = eventType + " Events";
        String eventDescription = "Filter by " + eventType;

        theSwitch.setChecked(theSettings.checkType(eventType));
        this.settingType.setText(eventTypeText);
        settingDescription.setText(eventDescription);
    }

    public void setDefaults(String text, int index)
    {
        String description;
        boolean isChecked;

        if(index == 0){
            description = "Show Life Story lines";
            isChecked = theSettings.isLifeStoryLines();
        } else if (index == 1){
            description = "Show Family Tree Lines";
            isChecked = theSettings.isFamilyTreeLines();
        } else if (index == 2) {
            description = "Show Spouse Lines";
            isChecked = theSettings.isSpouseLines();
        } else if (index == 3){
            description = "Filter by Mother's side";
            isChecked = theSettings.isMotherSide();
        }else if (index == 4){
            description = "Filter by Father's side";
            isChecked = theSettings.isFatherSide();
        }else if (index == 5){
            description = "Filter by Female events";
            isChecked = theSettings.isWomen();
        }else {
            description = "Filter by Male Events";
            isChecked = theSettings.isMen();
        }

        settingType.setText(text);
        settingDescription.setText(description);
        theSwitch.setChecked(isChecked);
    }
}
