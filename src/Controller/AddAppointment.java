package Controller;

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
import java.util.ResourceBundle;

public class AddAppointment implements Initializable {

    @FXML private TextField newID;
    @FXML private TextField newTitle;
    @FXML private TextField newDescription;
    @FXML private TextField newLocation;
    @FXML private ComboBox newContact;
    @FXML private TextField newType;
    @FXML private DatePicker newDate;
    @FXML private ComboBox newStartHr;
    @FXML private ComboBox newStartMin;
    @FXML private ComboBox newStartTOD;
    @FXML private ComboBox newEndHr;
    @FXML private ComboBox newEndMin;
    @FXML private ComboBox newEndTOD;
    @FXML private ComboBox newCustomer;
    @FXML private Button cancelButton;

    public void saveNewAppointment(ActionEvent actionEvent) {
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
        newStartHr.setItems(comboHours);
        newStartMin.setItems(comboMin);
        newStartTOD.setItems(comboTOD);
        newEndHr.setItems(comboHours);
        newEndMin.setItems(comboMin);
        newEndTOD.setItems(comboTOD);


        try {
            Customer.customers = DBQuery.getCustomers();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        newCustomer.setItems(Customer.customers);

    }
}
