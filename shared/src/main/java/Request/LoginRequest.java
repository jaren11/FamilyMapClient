package Request;

/**
 * This class represents an request for a authToken from a user.
 */
public class LoginRequest {
    /**
     * The existing user's username.
     */
    private String username;

    /**
     * The existing user's password.
     */
    private String password;

    /**
     * Creates a new LoginRequest where all values are null.
     */
    public LoginRequest() {
        this.username = null;
        this.password = null;
    }

    /**
     * Creates a new LoginRequest.
     * @param username
     * @param password
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
