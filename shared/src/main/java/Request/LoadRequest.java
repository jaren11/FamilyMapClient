package Request;

import Model.Event;
import Model.Person;
import Model.User;

import java.util.ArrayList;

/**
 * This class represents an object from the client that contains arrays to load into the database.
 */
public class LoadRequest {
    /**
     * An array of users to load into the database.
     */
    private ArrayList<User> users;

    /**
     * An array of persons to load into the database.
     */
    private ArrayList<Person> persons;

    /**
     * An array of events to load into the database.
     */
    private ArrayList<Event> events;

    /**
     * Creates a new LoadRequest where all values are null.
     */
    public LoadRequest(){
        this.users = null;
        this.persons = null;
        this.events = null;
    }

    /**
     * Creates a new LoadRequest.
     * @param users
     * @param persons
     * @param events
     */
    public LoadRequest(ArrayList<User> users, ArrayList<Person> persons, ArrayList<Event> events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
}
