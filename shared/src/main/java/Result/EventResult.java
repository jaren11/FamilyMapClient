package Result;

import Model.Event;

import java.util.ArrayList;

/**
 * This class is the result of an event service command.
 */
public class EventResult {
    /**
     * List of all Events.
     */
    private ArrayList<Event> data;

    /**
     * Contains the error or success message.
     */
    private String message;

    /**
     * keeps track of if the operation was a success or not.
     */
    private boolean success;

    /**
     * Creates a new EventResult.
     * @param data
     * @param message
     * @param success
     */
    public EventResult(ArrayList<Event> data, String message, boolean success) {
        this.data = data;
        this.message = message;
        this.success = true;
    }

    public EventResult(String error){
        this.data = null;
        this.message = error;
        this.success = false;
    }

    public ArrayList<Event> getData() {
        return data;
    }

    public void setData(ArrayList<Event> data) {
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
