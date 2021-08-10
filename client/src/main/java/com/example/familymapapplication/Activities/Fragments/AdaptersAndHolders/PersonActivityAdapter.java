package com.example.familymapapplication.Activities.Fragments.AdaptersAndHolders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.familymapapplication.Activities.Fragments.Models.DataCache;
import com.example.familymapapplication.R;

import java.util.List;

import Model.*;

public class PersonActivityAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> theHeaders;
    private List<Event> theEvents;
    private List<Person> thePersons;
    private Person currentPerson;

    private TextView theFirstLine;
    private TextView theSecondLine;
    private ImageView theListIcon;

    private DataCache dataCache = DataCache.getInstance();

    public PersonActivityAdapter(Context context, List<String> listDataHeader,
                                     List<Event> eventsList, List<Person> personsList,
                                     Person person) {
        this.context = context;
        this.theHeaders = listDataHeader;
        this.theEvents = eventsList;
        this.thePersons = personsList;
        this.currentPerson = person;
    }

    @Override
    public int getGroupCount()
    {
        return theHeaders.size();
    }

    @Override
    public int getChildrenCount(int groupPosition)
    {
        if (groupPosition == 0){
            return theEvents.size();
        }
        else{
            return thePersons.size();
        }
    }

    @Override
    public Object getGroup(int groupPosition)
    {
        if (groupPosition == 0){
            return theEvents;
        }
        else{
            return thePersons;
        }
    }

    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        if(groupPosition == 0){
            return theEvents.get(childPosition);
        }
        else{
            return thePersons.get(childPosition);
        }
    }

    @Override
    public long getGroupId(int groupPosition)
    {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return childPosition;
    }

    @Override
    public boolean hasStableIds()
    {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        String headerTitle = theHeaders.get(groupPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.event_list_header, null);
        }

        TextView header = convertView.findViewById(R.id.event_header);
        header.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.event_list_item, null);
        }

        theFirstLine = convertView.findViewById(R.id.event_list_info);
        theSecondLine = convertView.findViewById(R.id.event_list_person);
        theListIcon = convertView.findViewById(R.id.list_item_icon);

        if (groupPosition == 0) {
            Event currEvent = (Event) getChild(groupPosition, childPosition);

            theListIcon.setImageDrawable(convertView.getResources().getDrawable(R.drawable.map_pointer_icon));
            update(currEvent, null);

        }
        else{
            Person currPerson = (Person) getChild(groupPosition, childPosition);

            if (currPerson.getGender().toLowerCase().equals("m")){
                theListIcon.setImageDrawable(convertView.getResources().getDrawable(R.drawable.male_icon));
            }
            else {
                theListIcon.setImageDrawable(convertView.getResources().getDrawable(R.drawable.female_icon));
            }

            update(null, currPerson);
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return true;
    }


    private void update(Event events, Person persons)
    {
        if (persons == null) {
            String eventInfo = events.getEventType() + ", " + events.getCity() + ", " + events.getCountry() + " " + events.getYear();
            theFirstLine.setText(eventInfo);
            Person currPerson = dataCache.getThePersons().get(events.getPersonID());
            String personInfo = currPerson.getFirstName() + " " + currPerson.getLastName();
            theSecondLine.setText(personInfo);
        }
        else {
            String personInfo = persons.getFirstName() + " " + persons.getLastName();
            theFirstLine.setText(personInfo);
            theSecondLine.setText(getRelationship(persons));

        }
    }

    private String getRelationship(Person persons)
    {
        if (currentPerson.getSpouseID().equals(persons.getPersonID())) {
            return "Spouse";
        }

        if (persons.getFatherID() != null && persons.getMotherID() != null) {
            if (persons.getFatherID().equals(currentPerson.getPersonID()) ||
                    persons.getMotherID().equals(currentPerson.getPersonID())) {
                return "Child";
            }
        }

        if (currentPerson.getMotherID() != null && currentPerson.getMotherID() != null) {
            if (currentPerson.getFatherID().equals(persons.getPersonID())) {
                return "Father";
            }
            else if (currentPerson.getMotherID().equals(persons.getPersonID())) {
                return "Mother";
            }
        }
        return "Error";
    }
}
