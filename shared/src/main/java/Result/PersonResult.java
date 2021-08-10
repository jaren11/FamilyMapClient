package Result;

import Model.Person;

import java.util.ArrayList;

/**
 * This class is the result of a person service command.
 */
public class PersonResult {
    /**
     * List of all Persons.
     */
    private ArrayList<Person> data;

    /**
     * Contains the error or success message.
     */
    private String message;

    /**
     * keeps track of if the operation was a success or not.
     */
    private boolean success;

    /**
     * Creates a new PersonResult.
     * @param data
     * @param message
     * @param success
     */
    public PersonResult(ArrayList<Person> data, String message, boolean success) {
        this.data = data;
        this.message = message;
        this.success = true;
    }

    /**
     * Creates a new PersonResult on error.
     * @param error
     */
    public PersonResult(String error){
        this.data = null;
        this.message = error;
        this.success = false;
    }

    public ArrayList<Person> getData() {
        return data;
    }

    public void setData(ArrayList<Person> data) {
        this.data = data;
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
