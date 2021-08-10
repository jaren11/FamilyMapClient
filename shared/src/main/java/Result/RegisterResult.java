package Result;

public class RegisterResult {
    /**
     * AuthToken of the user registering.
     */
    private String authtoken;

    /**
     * Username of the user registering.
     */
    private String username;

    /**
     * personID related to the user registering.
     */
    private String personID;

    /**
     * Success or error message.
     */
    private String message;

    /**
     * Whether the register was successful or not.
     */
    private boolean success;

    /**
     * Creates a new RegisterResult on successful register.
     * @param authtoken
     * @param username
     * @param personID
     * @param message
     */
    public RegisterResult(String authtoken, String username, String personID, String message) {
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
        this.message = message;
        this.success = true;
    }

    /**
     * Creates a new RegisterResult on failed register.
     * @param error
     */
    public RegisterResult(String error) {
        this.authtoken = null;
        this.username = null;
        this.personID = null;
        this.message = error;
        this.success = false;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
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
