package Result;

import Model.Event;

/**
 * This class is the result of an EventID service command.
 */
public class EventIDResult {
    /**
     * A unique ID for the event.
     */
    private String eventID;

    /**
     * The Username to which the event belongs.
     */
    private String associatedUsername;

    /**
     * The ID of the person associated with the Event.
     */
    private String personID;

    /**
     * The latitude where the event occurred.
     */
    private double latitude;

    /**
     * The longitude where the event occurred.
     */
    private double longitude;
    /**
     * The country where the event occurred;
     */
    private String country;

    /**
     * The city where the event occurred.
     */
    private String city;

    /**
     * The type of event that the event was (ex: Birth, Death, Baptism, etc.).
     */
    private String eventType;

    /**
     * The year in which the event occurred.
     */
    private int year;

    private String message;
    boolean success;

    /**
     * Creates a new EventIDResult in case of an error.
     * @param error
     */
    public EventIDResult(String error){
        this.message = error;
        this.success = false;
    }

    /**
     * Gets a new EventIDResult
     * @param event
     * @param username
     */
    public EventIDResult(Event event, String username) {
        this.associatedUsername = username;
        this.eventID = event.getEventID();
        this.personID = event.getPersonID();
        this.latitude = event.getLatitude();
        this.longitude = event.getLongitude();
        this.country = event.getCountry();
        this.city = event.getCity();
        this.eventType = event.getEventType();
        this.year = event.getYear();
        this.success = true;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
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

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
