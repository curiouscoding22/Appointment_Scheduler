package Controller;

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
import java.util.ResourceBundle;

public class CustomerMenu implements Initializable {

    @FXML public TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, Integer> customerID;
    @FXML private TableColumn<Customer, String> customer;
    @FXML private TableColumn<Customer, String> address;
    @FXML private TableColumn<Customer, String> firstLevel;
    @FXML private TableColumn<Customer, String> postCode;
    @FXML private TableColumn<Customer, String> phone;
    @FXML private Button exitApplication;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            Customer.customers = DBQuery.getCustomers();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        customerTable.setItems(Customer.customers);


        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customer.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        firstLevel.setCellValueFactory(new PropertyValueFactory<>("firstLevel"));
        postCode.setCellValueFactory(new PropertyValueFactory<>("postCode"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }

    public void returnToAppointments(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void addNewCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/addCustomerForm.fxml"));
        Stage addCustomer = new Stage();
        addCustomer.setTitle("Add Customer");
        addCustomer.setScene(new Scene(root, 421, 487));
        addCustomer.show();

    }

    public void editCustomer(ActionEvent actionEvent) throws IOException {
        if(customerTable.getSelectionModel().getSelectedItem() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/editCustomerForm.fxml"));
                Parent mainScreen = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Update Product");
                stage.setScene(new Scene(mainScreen, 421, 487));
                stage.show();
                EditCustomerForm controller = loader.getController();
                Customer selectCustomer = customerTable.getSelectionModel().getSelectedItem();
                controller.retrieveSelectCustomer(selectCustomer);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Customer Selected");
            alert.setContentText("Select a customer from the list to update");
            alert.showAndWait();
        }
    }

    public void exitApplication(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Application");
        alert.setContentText("Are you sure you want to cancel?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Stage stage = (Stage) exitApplication.getScene().getWindow();
                stage.close();
            }
        });
    }

    public void deleteSelectCustomer(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(customerTable.getSelectionModel().getSelectedItem() != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Action");
            alert.setContentText("Do you want to delete this customer?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        DBQuery.deleteCustomer(customerTable.getSelectionModel().getSelectedItem());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            DBQuery.updateCustomerList();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No customer Selected");
            alert.setContentText("Select a customer from the list");
            alert.showAndWait();
        }
    }
}
