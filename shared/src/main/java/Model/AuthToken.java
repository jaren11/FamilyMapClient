package Model;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Represents a unique Authorization Token String for each user that logs into the server.
 * Each user can have one AuthToken, and each AuthToken can only belong to one user.
 */
public class AuthToken {
    /**
     * The user to whom the AuthToken belongs.
     */
    private String username;

    /**
     * The literal String value of the AuthToken.
     */
    private String authtoken;

    /**
     * The TimeStamp when the AuthToken was generated.
     */
    private Timestamp timeStamp;

    public AuthToken(){
        this.username = null;
        this.authtoken = UUID.randomUUID().toString();
        this.timeStamp = null;
    }

    /**
     * Creates a new AuthToken.
     * @param username
     * @param authtoken
     * @param timestamp
     */
    public AuthToken(String username, String authtoken, Timestamp timestamp) {
        this.username = username;
        this.authtoken = UUID.randomUUID().toString();
        this.timeStamp = timestamp;
    }

    public AuthToken(String token, String name){
        this.username = name;
        this.authtoken = token;
        this.timeStamp = null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthToken() {
        return authtoken;
    }

    public void setAuthToken(String authToken) {
        this.authtoken = authToken;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public boolean equals(Object o){
        if (o == null)
            return false;
        if (o instanceof AuthToken) {
            AuthToken oAuthToken = (AuthToken) o;
            return oAuthToken.getUsername().equals(getUsername()) &&
                    oAuthToken.getAuthToken().equals(getAuthToken()) &&
                    oAuthToken.getTimeStamp().equals(getTimeStamp());
        } else {
            return false;
        }
    }
}
