package Model;

import java.util.UUID;

/**
 * Represents an Event that describes an important event from a given person's life.
 * Each event can only belong to one person, but a person may have many events.
 */

public class Event {
    /**
     * A unique ID for this Event.
     */
    private String eventID;

    /**
     * The Username to which this event belongs.
     */
    private String associatedUsername;

    /**
     * The ID of the person associated with this Event.
     */
    private String personID;

    /**
     * The latitude where this event occurred.
     */
    private double latitude;

    /**
     * The longitude where this event occurred.
     */
    private double longitude;
    /**
     * The country where this event occurred;
     */
    private String country;

    /**
     * The city where this event occurred.
     */
    private String city;

    /**
     * The type of event that this was (ex: Birth, Death, Baptism, etc.).
     */
    private String eventType;

    /**
     * The year in which this event occurred.
     */
    private int year;

    public Event(){
        this.eventID = UUID.randomUUID().toString();
        this.associatedUsername = null;
        this.personID = null;
        this.latitude = 0;
        this.longitude = 0;
        this.country = null;
        this.city = null;
        this.eventType = null;
        this.year = 0;
    };

    /**
     * Creates a new Event.
     * @param eventID
     * @param associatedUsername
     * @param personID
     * @param latitude
     * @param longitude
     * @param country
     * @param city
     * @param eventType
     * @param year
     */
    public Event(String eventID, String associatedUsername, String personID, double latitude, double longitude, String country, String city, String eventType, int year) {
        this.eventID = eventID;
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o){
        if (o == null)
            return false;
        if (o instanceof Event) {
            Event oEvent = (Event) o;
            return oEvent.getEventID().equals(getEventID()) &&
                    oEvent.getAssociatedUsername().equals(getAssociatedUsername()) &&
                    oEvent.getPersonID().equals(getPersonID()) &&
                    oEvent.getLatitude() == getLatitude() &&
                    oEvent.getLongitude() == getLongitude() &&
                    oEvent.getCountry().equals(getCountry()) &&
                    oEvent.getCity().equals(getCity()) &&
                    oEvent.getEventType().equals(getEventType()) &&
                    oEvent.getYear() == getYear();

        } else {
            return false;
        }
    }
}
