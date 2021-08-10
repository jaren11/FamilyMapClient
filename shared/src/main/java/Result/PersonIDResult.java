package Result;

import Model.Person;

/**
 * This class is the result of an PersonID service command.
 */
public class PersonIDResult {
    /**
     * A unique ID for this person.
     */
    private String personID;

    /**
     * The user to whom this Person belongs.
     */
    private String associatedUsername;

    /**
     * The first name of this person.
     */
    private String firstName;

    /**
     * The last name of this person.
     */
    private String lastName;

    /**
     * The gender of this person (m or f);
     */
    private String gender;

    /**
     * The personID of the person's mother.
     */
    private String motherID;

    /**
     * The personID of the person's father.
     */
    private String fatherID;

    /**
     * The personID of the person's spouse.
     */
    private String spouseID;

    /**
     * Success or error message.
     */
    private String message;

    /**
     * Whether the personID operation was successful.
     */
    private boolean success;

    /**
     * Creates a new PersonIDResult on successful operation.
     * @param person
     * @param personID
     */
    public PersonIDResult(Person person, String personID) {
        this.personID = personID;
        this.associatedUsername = person.getAssociatedUsername();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.gender = person.getGender();
        this.motherID = person.getMotherID();
        this.fatherID = person.getFatherID();
        this.spouseID = person.getSpouseID();
        this.success = true;
    }

    /**
     * Creates a new PersonIDResult on failed operation.
     * @param error
     */
    public PersonIDResult(String error){
        this.personID = null;
        this.associatedUsername = null;
        this.firstName = null;
        this.lastName = null;
        this.gender = null;
        this.motherID = null;
        this.fatherID = null;
        this.spouseID = null;
        this.message = error;
        this.success = false;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
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

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
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
