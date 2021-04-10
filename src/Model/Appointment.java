package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

/**
 * This is the appointment class. This class creates appointment objects.
 */
public class Appointment {

    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start, end;
    private int customerID;
    private int userID;
    private int contactID;
    private String contact;

    /**This is the full constructor for creating an appointment.
     * @param appointmentID
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customerID
     * @param userID
     * @param contactID
     * @param contact
     */
    public Appointment(int appointmentID, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID, String contact) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
        this.contact = contact;
    }

    /**This method returns the appointment ID number.
     * @return the appointment ID number.
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**This method sets the appointment ID number.
     * @param appointmentID
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**This method returns the appointment title.
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**This method sets the appointment title.
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**This method returns the appointment description.
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**This method sets the appointment description.
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**This method returns the appointment's location.
     * @return
     */
    public String getLocation() {
        return location;
    }

    /**This method sets the appointments location.
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**This method returns the appointment type.
     * @return
     */
    public String getType() {
        return type;
    }

    /**This method sets the appointment type.
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**This method returns the start time of the appointment.
     * @return
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**This method sets the start time of the appointment.
     * @param start
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**This method sets the end time for the appointment
     * @return
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**This method sets the end time for the appointment.
     * @param end
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**This method returns the customer ID number.
     * @return
     */
    public int getCustomerID() {
        return customerID;
    }

    /**This method sets the customer ID number for the appointment.
     * @param customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**This method returns the user ID number.
     * @return
     */
    public int getUserID() {
        return userID;
    }

    /**This method sets the user ID number.
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**This method returns the contact ID number.
     * @return
     */
    public int getContactID() {
        return contactID;
    }

    /**This method sets the contact ID.
     * @param contactID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**This method returns the contact name.
     * @return
     */
    public String getContact() {
        return contact;
    }

    /**This sets the contact string for the appointment.
     * @param contact
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * This is the empty constructor for creating appointment objects.
     */
    public Appointment(){}


    public static ObservableList appointments = FXCollections.observableArrayList();
}