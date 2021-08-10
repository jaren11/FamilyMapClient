package com.example.familymapapplication.Activities.Fragments.AdaptersAndHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.familymapapplication.Activities.Fragments.Models.DataCache;
import com.example.familymapapplication.R;

import Model.*;

public class SearchHolder extends RecyclerView.ViewHolder {
    private View convertView;

    private LinearLayout theLayout;
    private TextView theFirstLine;
    private TextView theDescription;
    private ImageView theIcon;
    public SearchHolder(View itemView)
    {
        super(itemView);
        theFirstLine = itemView.findViewById(R.id.event_list_info);
        theDescription = itemView.findViewById(R.id.event_list_person);
        theIcon = itemView.findViewById(R.id.list_item_icon);
        theLayout = itemView.findViewById(R.id.linear_layout_click_area);
        theLayout.setClickable(true);
        convertView = itemView;
    }

    public LinearLayout getLinearLayout()
    {
        return theLayout;
    }

    public void bindEvent(Object currObject) {

        final Event event = (Event) currObject;
        String eventInfo = event.getEventType() + ", " + event.getCity() + ", " + event.getCountry() + " " + event.getYear();
        theFirstLine.setText(eventInfo);

        DataCache dataCache = DataCache.getInstance();
        Person currPerson = dataCache.getThePersons().get(event.getPersonID());
        String personInfo = currPerson.getFirstName() + " " + currPerson.getLastName();
        theDescription.setText(personInfo);
        theIcon.setImageDrawable(convertView.getResources().getDrawable(R.drawable.map_pointer_icon));

    }

    public void bindPerson(Object currObject)
    {
        Person currPerson = (Person) currObject;
        String personInfo = currPerson.getFirstName() + " " + currPerson.getLastName();
        theFirstLine.setText(personInfo);
        theDescription.setVisibility(View.INVISIBLE);
        if (currPerson.getGender().toLowerCase().equals("m")) {
            theIcon.setImageDrawable(convertView.getResources().getDrawable(R.drawable.male_icon));
        } else {
            theIcon.setImageDrawable(convertView.getResources().getDrawable(R.drawable.female_icon));
        }
    }
}
