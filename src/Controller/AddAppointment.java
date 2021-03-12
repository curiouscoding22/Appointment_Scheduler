package Controller;

import Model.Appointment;
import Model.Contact;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import utils.DBQuery;
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
    @FXML private Button cancelButton;

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

        Appointment appointment = new Appointment(newID, custID, newTitle, newDescription, newLocation, newType, startMeeting, endMeeting, meetContact, contactMeet);

        DBQuery.addNewAppointment(appointment);
        DBQuery.updateAppointmentList();

    }

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

        ID.setEditable(false);
    }
}
