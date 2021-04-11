package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Contact {

    private int contactID;
    private String contactName;
    private String contactEmail;

    /**
     * This is the empty constructor for creating Contact objects.
     */
    public Contact(){}

    /**This is the full constructor for creating Contact objects.
     * @param contactID
     * @param contactName
     * @param contactEmail
     */
    public Contact(int contactID, String contactName, String contactEmail) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**This method returns the contact ID number.
     * @return the contact ID number.
     */
    public int getContactID() {
        return contactID;
    }

    /**This method sets the contact ID number.
     * @param contactID the contact ID number.
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**This method returns the contact name.
     * @return the contact name.
     */
    public String getContactName() {
        return contactName;
    }

    /**This method sets the contact name.
     * @param contactName the contact name to set.
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**This method returns the contact email.
     * @return the contact email.
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**This method sets the contact email.
     * @param contactEmail the email to set.
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public static ObservableList<Contact> contacts = FXCollections.observableArrayList();


    @Override
    public String toString(){
        return(contactName);
    }
}
