package Controller;

import Model.Appointment;
import Model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import utils.DBQuery;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class MainScreen implements Initializable {

    @FXML private TableView<Appointment> appointmentTable;
    @FXML private TableColumn<Appointment, Integer> appointmentIDColumn;
    @FXML private TableColumn<Appointment, String> titleColumn;
    @FXML private TableColumn<Appointment, String> descriptionColumn;
    @FXML private TableColumn<Appointment, String> locationColumn;
    @FXML private TableColumn<Appointment, String> contactColumn;
    @FXML private TableColumn<Appointment, String> typeColumn;
    @FXML private TableColumn<Appointment, LocalTime> startColumn;
    @FXML private TableColumn<Appointment, LocalTime> endColumn;
    @FXML private TableColumn<Appointment, Integer> customerIDColumn;

    @FXML private Button exitButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            appointmentTable.setItems(DBQuery.getAppointments());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));




    }

    public void changeToCustomer(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/View/Customers.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void addAppointment(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/addAppointment.fxml"));
        Stage addAppointment = new Stage();
        addAppointment.setTitle("Add Appointment");
        addAppointment.setScene(new Scene(root, 428, 539));
        addAppointment.show();
    }

    public void updateAppointment(ActionEvent actionEvent) throws IOException {
        if(appointmentTable.getSelectionModel().getSelectedItem() != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/EditAppointment.fxml"));
            Parent mainScreen = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Update Product");
            stage.setScene(new Scene(mainScreen, 428, 539));
            stage.show();
            EditAppointment controller = loader.getController();
            Appointment appointment = appointmentTable.getSelectionModel().getSelectedItem();
            controller.retrieveSelectAppointment(appointment);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Appointment Selected");
            alert.setContentText("Select an appointment from the list to update");
            alert.showAndWait();
        }

    }

    public void deleteAppointment(ActionEvent actionEvent) {
    }

    public void exitFromMain(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Application");
        alert.setContentText("Are you sure you want to cancel?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Stage stage = (Stage) exitButton.getScene().getWindow();
                stage.close();
            }
        });
    }
}
