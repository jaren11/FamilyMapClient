package Model;

import java.util.UUID;

/**
 * Represents a User that will log onto the server.
 */
public class User {
    /**
     * Unique username.
     */
    private String username;

    /**
     * User's password.
     */
    private String password;

    /**
     * User's email address.
     */
    private String email;

    /**
     * User's first name.
     */
    private String firstName;

    /**
     * User's last name.
     */
    private String lastName;

    /**
     * User's gender (char: 'f' or 'm').
     */
    private String gender;

    /**
     * Unique personID assigned to this User's generated Person object.
     */
    private String personID;

    public User(){
        this.username = null;
        this.password = null;
        this.email = null;
        this.firstName = null;
        this.lastName = null;
        this.gender = null;
        this.personID = UUID.randomUUID().toString();
    };

    /**
     * Creates a new User with the given username, password, email, firstName,
     * lastName, gender, and personID.
     */
    public User(String username, String password, String email, String firstName, String lastName, String gender, String personID) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    @Override
    public boolean equals(Object o){
        if (o == null)
            return false;
        if (o instanceof User) {
            User oUser = (User) o;
            return oUser.getUsername().equals(getUsername()) &&
                    oUser.getPassword().equals(getPassword()) &&
                    oUser.getEmail().equals(getEmail()) &&
                    oUser.getFirstName().equals(getFirstName()) &&
                    oUser.getLastName().equals(getLastName()) &&
                    oUser.getGender().equals(getGender()) &&
                    oUser.getPersonID().equals(getPersonID());

        } else {
            return false;
        }
    }
}
