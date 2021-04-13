package Controller;

import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import utils.DBQuery;
import utils.Validate;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 * This is the edit appointment menu controller class.
 */
public class EditAppointment implements Initializable {

    @FXML private TextField IDField;
    @FXML private TextField titleField;
    @FXML private TextField descriptionField;
    @FXML private TextField locationField;
    @FXML private ComboBox contactCombo;
    @FXML private TextField typeField;
    @FXML private DatePicker dateSelector;
    @FXML private ComboBox<Integer> startHRCombo;
    @FXML private ComboBox<String> startMinCombo;
    @FXML private ComboBox startAMPM;
    @FXML private ComboBox<Integer> endHRCombo;
    @FXML private ComboBox<String> endMinCombo;
    @FXML private ComboBox endAMPM;
    @FXML private ComboBox customerCombo;
    @FXML private ComboBox userCombo;
    @FXML private Button cancelButton;

    private Appointment appointment;

    /** This method initializes the hour, minute and time of day combo boxes as well as sets the lists for the customer, contact and user combo boxes.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Integer> comboHours = FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10,11,12);
        ObservableList <String> comboMin = FXCollections.observableArrayList("00", "15", "30", "45");
        ObservableList <String> comboTOD = FXCollections.observableArrayList("AM", "PM");
        startHRCombo.setItems(comboHours);
        startMinCombo.setItems(comboMin);
        startAMPM.setItems(comboTOD);
        endHRCombo.setItems(comboHours);
        endMinCombo.setItems(comboMin);
        endAMPM.setItems(comboTOD);


        try {
            Customer.customers = DBQuery.getCustomers();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        customerCombo.setItems(Customer.customers);

        try{
            Contact.contacts = DBQuery.getContacts();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        contactCombo.setItems(Contact.contacts);

        try{
            User.users = DBQuery.getUsers();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        userCombo.setItems(User.users);

        IDField.setEditable(false);

    }

    /**This is the retrieve appointment method. This method takes the appointment selected by the user to edit and fills im the fields with the appoinements information.
     * @param appointment
     */
    public void retrieveSelectAppointment(Appointment appointment) {
        this.appointment = appointment;
        IDField.setText(Integer.toString(appointment.getAppointmentID()));
        titleField.setText(appointment.getTitle());
        descriptionField.setText(appointment.getDescription());
        locationField.setText(appointment.getLocation());
        for(Contact con: Contact.contacts){
            if(appointment.getContact().equals(con.getContactName())){
                contactCombo.setValue(con);
            }
        }
        typeField.setText(appointment.getType());
        dateSelector.setValue(appointment.getStart().toLocalDate());
        LocalDateTime start = appointment.getStart();
        if(start.getHour() == 12){
            startHRCombo.setValue(start.getHour());
            startAMPM.setValue("PM");
        } else if(start.getHour() > 12){
            startHRCombo.setValue(start.getHour() - 12);
            startAMPM.setValue("PM");
        } else {
            startHRCombo.setValue(start.getHour());
            startAMPM.setValue("AM");
        }
        if(start.getMinute() == 0){
            startMinCombo.setValue("00");
        } else if(start.getMinute() == 15){
            startMinCombo.setValue("15");
        } else if(start.getMinute() == 30){
            startMinCombo.setValue("30");
        } else if(start.getMinute() == 45){
            startMinCombo.setValue("45");
        }
        LocalDateTime end = appointment.getEnd();
        if(end.getHour() == 12){
            endHRCombo.setValue(end.getHour());
            endAMPM.setValue("PM");
        } else if(end.getHour() > 12){
            endHRCombo.setValue(end.getHour() - 12);
            endAMPM.setValue("PM");
        } else {
            endHRCombo.setValue(end.getHour());
            endAMPM.setValue("AM");
        }
        if(end.getMinute() == 0){
            endMinCombo.setValue("00");
        } else if(end.getMinute() == 15){
            endMinCombo.setValue("15");
        } else if(end.getMinute() == 30){
            endMinCombo.setValue("30");
        } else if(end.getMinute() == 45){
            endMinCombo.setValue("45");
        }

        for(Customer cus : Customer.customers){
            if(appointment.getCustomerID() == cus.getCustomerID()){
                customerCombo.setValue(cus);
            }
        }

        for(User u : User.users){
            if(appointment.getUserID() == u.getUserID()){
                userCombo.setValue(u);
            }
        }


    }

    /**This is the method that saves the updated appointment. The method takes the information from the fields and updates the appointment in the database that matches the appointment ID.
     * @param actionEvent
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void saveUpdatedAppointment(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        int updateID = Integer.parseInt(IDField.getText());
        String updateTitle = titleField.getText();
        String updateDesc = descriptionField.getText();
        String updateLocation = locationField.getText();
        Contact con = (Contact) contactCombo.getValue();
        String updateContact = con.getContactName();
        int updateConID = con.getContactID();
        String updateType = typeField.getText();
        LocalDate updateDate = dateSelector.getValue();
        User updateUser = (User) userCombo.getValue();
        Customer cus = (Customer) customerCombo.getValue();
        int custIDUpdate = cus.getCustomerID();

        int sHour = startHRCombo.getValue();
        if(startHRCombo.getValue().equals(12) && startAMPM.getValue().equals("PM")){
            startHRCombo.getValue().equals(12);
        }
        else if(startAMPM.getValue().equals("PM")){
            sHour += 12;
        }
        String startHour = String.valueOf(sHour);
        String startMinute = startMinCombo.getValue();
        if(startHour.length() < 2){
            startHour = "0" + startHour;
        }
        if(startMinute.length() < 2){
            startMinute = "0" + startMinute;
        }
        LocalTime meetingStartTime = LocalTime.parse(startHour + ":" + startMinute);
        LocalDateTime startMeeting = LocalDateTime.of(updateDate, meetingStartTime);

        int eHour = endHRCombo.getValue();
        if(endHRCombo.getValue().equals(12) && endAMPM.getValue().equals("PM")){
            endHRCombo.getValue().equals(12);
        }
        else if(endAMPM.getValue().equals("PM")){
            eHour += 12;
        }
        String endHour = String.valueOf(eHour);
        String endMinute = endMinCombo.getValue();
        if(endHour.length() < 2){
            endHour = "0" + endHour;
        }
        if(endMinute.length() < 2){
            endMinute = "0" + endMinute;
        }
        LocalTime meetingEndTime = LocalTime.parse(endHour + ":" + endMinute);
        LocalDateTime endMeeting = LocalDateTime.of(updateDate, meetingEndTime);

        int userUpdate = updateUser.getUserID();

        Appointment updateAppointment = new Appointment(updateID, updateTitle, updateDesc, updateLocation, updateType, startMeeting, endMeeting, custIDUpdate, userUpdate, updateConID, updateContact);

        if(!Validate.businessHoursCheck(appointment)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Business Hours");
            alert.setContentText("Please select times within business hours.");
            alert.showAndWait();
            return;
        }

        if(!Validate.appointmentOverlapCheck(updateAppointment)) {
            try {
                DBQuery.updateAppointment(updateAppointment);
                IDField.clear();
                titleField.clear();
                descriptionField.clear();
                locationField.clear();
                contactCombo.setValue(null);
                typeField.clear();
                dateSelector.setValue(null);
                startHRCombo.setValue(null);
                startHRCombo.setPromptText("Hour");
                startMinCombo.setValue(null);
                startMinCombo.setPromptText("Min");
                startAMPM.setValue(null);
                startAMPM.setPromptText("AM/PM");
                endHRCombo.setValue(null);
                endHRCombo.setPromptText("Hour");
                endMinCombo.setValue(null);
                endMinCombo.setPromptText("Min");
                endAMPM.setValue(null);
                endAMPM.setPromptText("AM/PM");
                customerCombo.setValue(null);
                userCombo.setValue(null);
                DBQuery.updateAppointmentList();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Appointment Updated");
                alert.setContentText("Appointment successfully updated.");
                alert.showAndWait();
            } catch (Exception e) {
                System.out.println(e);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Update Unsuccessful");
                alert.setContentText("Review the information entered.");
                alert.showAndWait();
            }
            return;
        } else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Overlapping Appointment");
            alert.setContentText("This customer already has an appointment at that time");
            alert.showAndWait();
        }
    }

    /**This is the cancel method. When the user clicks the Cancel button, this method confirms their intent then closes the stage.
     * @param actionEvent
     */
    public void cancelUpdate(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Add Customer");
        alert.setContentText("Are you sure you want to cancel?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Stage stage = (Stage) cancelButton.getScene().getWindow();
                stage.close();
            }
        });
    }
}
