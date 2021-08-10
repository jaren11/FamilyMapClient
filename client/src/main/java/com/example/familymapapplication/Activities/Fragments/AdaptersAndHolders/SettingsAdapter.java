package com.example.familymapapplication.Activities.Fragments.AdaptersAndHolders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.example.familymapapplication.Activities.Fragments.Models.Settings;
import com.example.familymapapplication.R;
import androidx.recyclerview.widget.RecyclerView;

import com.example.familymapapplication.Activities.Fragments.Models.DataCache;

import java.util.List;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsHolder> {
    private List<String> settingsList;
    private LayoutInflater inflater;

    private Settings settings = DataCache.getInstance().getSettings();

    public SettingsAdapter(List<String> newSettings, Context context)
    {
        settingsList = newSettings;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public SettingsHolder onCreateViewHolder (ViewGroup viewGroup, final int i)
    {
        View settingsView = inflater.inflate(R.layout.settings_list_item, viewGroup, false);
        return new SettingsHolder(settingsView);
    }

    @Override
    public void onBindViewHolder(final SettingsHolder settingsHolder, final int i)
    {
        final String currentSetting = settingsList.get(i);

        settingsHolder.getSwitch().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                click(i, isChecked);
            }
        });
        settingsHolder.setDefaults(currentSetting, i);

    }

    @Override
    public int getItemCount()
    {
        return settingsList.size();
    }

    private void click(int index, boolean isChecked)
    {
        switch (index){
            case 0:
                settings.setLifeStoryLines(isChecked);
                break;
            case 1:
                settings.setFamilyTreeLines(isChecked);
                break;
            case 2:
                settings.setSpouseLines(isChecked);
                break;
            case 3:
                settings.setMotherSide(isChecked);
                break;
            case 4:
                settings.setFatherSide(isChecked);
                break;
            case 5:
                settings.setWomen(isChecked);
                break;
            case 6:
                settings.setMen(isChecked);
                break;
        }
    }

}
