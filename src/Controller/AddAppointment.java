package Controller;

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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class AddAppointment implements Initializable {

    @FXML private TextField ID;
    @FXML private TextField title;
    @FXML private TextField description;
    @FXML private TextField location;
    @FXML private ComboBox contact;
    @FXML private TextField type;
    @FXML private DatePicker date;
    @FXML private ComboBox startHr;
    @FXML private ComboBox startMin;
    @FXML private ComboBox startTOD;
    @FXML private ComboBox endHr;
    @FXML private ComboBox endMin;
    @FXML private ComboBox endTOD;
    @FXML private ComboBox customer;
    @FXML private Button cancelButton;

    public void saveNewAppointment(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        int newID = DBQuery.getAppointments().size() + 1;
        String newTitle = title.getText();
        String newDescription = description.getText();
        String newLocation = location.getText();
        Contact newContact = (Contact) contact.getValue();
        String newType = type.getText();
        LocalDate newDate = date.getValue();
        if(startTOD.getValue().equals("PM")){
            //LocalTime newStartTime = LocalTime.of(startHr.getValue(), startMin.getValue());
        }

        LocalTime newStartTime = (LocalTime) startHr.getValue();
        LocalTime newEndTime = null;

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
