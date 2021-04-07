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
import java.time.*;
import java.util.ResourceBundle;

public class AddAppointment implements Initializable {

    @FXML private TextField ID;
    @FXML private TextField title;
    @FXML private TextField description;
    @FXML private TextField location;
    @FXML private ComboBox contact;
    @FXML private TextField type;
    @FXML private DatePicker startDate;
    @FXML private ComboBox<Integer> startHr;
    @FXML private ComboBox <String> startMin;
    @FXML private ComboBox startTOD;
    @FXML private ComboBox<Integer> endHr;
    @FXML private ComboBox <String>endMin;
    @FXML private ComboBox endTOD;
    @FXML private ComboBox customer;
    @FXML private ComboBox user;
    @FXML private Button cancelButton;

    /**This is the save new appointment method. This method takes the user input and creates an appointment object that is added to the database.
     * @param actionEvent
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void saveNewAppointment(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        int newID = Appointment.appointments.size() + 1;
        String newTitle = title.getText();
        String newDescription = description.getText();
        String newLocation = location.getText();
        Contact newContact = (Contact) contact.getValue();
        String meetContact = newContact.getContactName();
        int contactMeet = newContact.getContactID();
        String newType = type.getText();
        LocalDate newDate = startDate.getValue();
        User newUser = (User) user.getValue();

        int sHour = startHr.getValue();
        if(startHr.getValue().equals(12) && startTOD.getValue().equals("PM")){
            startHr.getValue().equals(12);
        }
        else if(startTOD.getValue().equals("PM")){
            sHour += 12;
        }
        String startHour = String.valueOf(sHour);
        String startMinute = startMin.getValue();
        if(startHour.length() < 2){
            startHour = "0" + startHour;
        }
        if(startMinute.length() < 2){
            startMinute = "0" + startMinute;
        }
        LocalTime meetingStartTime = LocalTime.parse(startHour + ":" + startMinute);
        LocalDateTime startMeeting = LocalDateTime.of(newDate, meetingStartTime);

        int eHour = endHr.getValue();
        if(endHr.getValue().equals(12) && endTOD.getValue().equals("PM")){
            endHr.getValue().equals(12);
        }
        else if(endTOD.getValue().equals("PM")){
            eHour += 12;
        }
        String endHour = String.valueOf(eHour);
        String endMinute = endMin.getValue();
        if(endHour.length() < 2){
            endHour = "0" + endHour;
        }
        if(endMinute.length() < 2){
            endMinute = "0" + endMinute;
        }
        LocalTime meetingEndTime = LocalTime.parse(endHour + ":" + endMinute);
        LocalDateTime endMeeting = LocalDateTime.of(newDate, meetingEndTime);

        Customer selectedCustomer = (Customer) customer.getValue();
        int custID = selectedCustomer.getCustomerID();

        int userID = newUser.getUserID();

        Appointment appointment = new Appointment(newID, newTitle, newDescription, newLocation, newType, startMeeting, endMeeting, custID, userID, contactMeet,  meetContact);

        if(!Validate.businessHoursCheck(appointment)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Business Hours");
            alert.setContentText("Please select times within business hours.");
            alert.showAndWait();
            return;
        }

        if(!Validate.appointmentOverlapCheck(appointment)){
            try{
                DBQuery.addNewAppointment(appointment);
                ID.clear();
                title.clear();
                description.clear();
                location.clear();
                contact.setValue(null);
                type.clear();
                startDate.setValue(null);
                startHr.setValue(null);
                startHr.setPromptText("Hour");
                startMin.setValue(null);
                startMin.setPromptText("Min");
                startTOD.setValue(null);
                startTOD.setPromptText("AM/PM");
                endHr.setValue(null);
                endHr.setPromptText("Hour");
                endMin.setValue(null);
                endMin.setPromptText("Min");
                endTOD.setValue(null);
                endTOD.setPromptText("AM/PM");
                customer.setValue(null);
                user.setValue(null);
                DBQuery.updateAppointmentList();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Appointment Added");
                alert.setContentText("Appointment successfully added.");
                alert.showAndWait();
            } catch (Exception e){
                System.out.println(e);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Add Unsuccessful");
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


    /**This is the cancel appointment method. This method closes out the add appointment form if the user clicks the "Cancel" button.
     * @param actionEvent
     */
    public void cancelAddAppointment(ActionEvent actionEvent) {
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

    /**This method initializes the combo boxes for collecting the user input
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList <Integer> comboHours = FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10,11,12);
        ObservableList <String> comboMin = FXCollections.observableArrayList("00", "15", "30", "45");
        ObservableList <String> comboTOD = FXCollections.observableArrayList("AM", "PM");
        startHr.setItems(comboHours);
        startMin.setItems(comboMin);
        startTOD.setItems(comboTOD);
        endHr.setItems(comboHours);
        endMin.setItems(comboMin);
        endTOD.setItems(comboTOD);


        try {
            Customer.customers = DBQuery.getCustomers();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        customer.setItems(Customer.customers);

        try{
            Contact.contacts = DBQuery.getContacts();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        contact.setItems(Contact.contacts);

        try{
            User.users = DBQuery.getUsers();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        user.setItems(User.users);

        ID.setEditable(false);
    }
}
