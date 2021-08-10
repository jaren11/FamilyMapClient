package Result;

/**
 * This class is the result of a login attempt.
 */
public class LoginResult {
    /**
     * The user's authtoken.
     */
    private String authtoken;
    /**
     * The user's authtoken.
     */
    private String personID;

    /**
     * The user's username.
     */
    private String username;

    /**
     * An error message.
     */
    private String message;

    /**
     * Whether the login was successful or not.
     */
    private boolean success;

    /**
     * Creates a new LoginResult on successful login.
     * @param authtoken
     * @param personID
     * @param message
     */
    public LoginResult(String authtoken, String personID, String username, String message) {
        this.authtoken = authtoken;
        this.personID = personID;
        this.username = username;
        this.message = message;
        this.success = true;
    }

    /**
     * Creates a new LoginResult on failed login.
     * @param error
     */
    public LoginResult(String error){
        this.authtoken = null;
        this.personID = null;
        this.message = error;
        this.success = false;
    }

    public String getAuthToken() {
        return authtoken;
    }

    public void setAuthToken(String authtoken) {
        this.authtoken = authtoken;
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
