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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

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
    @FXML private Button cancelButton;

    private Appointment appointment;

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

        IDField.setEditable(false);

    }

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
            startMinCombo.setValue("OO");
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
            endMinCombo.setValue("OO");
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


    }

    public void saveUpdatedAppointment(ActionEvent actionEvent) {
    }

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
