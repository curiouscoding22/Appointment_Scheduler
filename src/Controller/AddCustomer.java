package Controller;

import Model.Country;
import Model.Customer;
import Model.FirstLevel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import utils.DBConnection;
import utils.DBQuery;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;



public class AddCustomer implements Initializable {

    @FXML private TextField newCustID;
    @FXML private TextField newCustName;
    @FXML private TextField newCustAddress;
    @FXML private TextField newCustPhone;
    @FXML private TextField newCustPost;
    @FXML private ComboBox<Country> newCustCountry;
    @FXML private ComboBox<FirstLevel> newCustFirst;

    @FXML private Button saveNewCustomer;
    @FXML private Button cancelButton;



    public void saveNewCustomer(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        int newID = Integer.parseInt(newCustID.getText());
        String newName = newCustName.getText();
        String newAddress = newCustAddress.getText();
        String newPhone = newCustPhone.getText();
        String newPost = newCustPost.getText();
        FirstLevel newFirstID = newCustFirst.getValue();

        Customer customer = new Customer(newID, newName, newAddress, newPhone, newPost, newFirstID.getFirstLevelName(), newFirstID.getFirstLevelID());
        DBQuery.addNewCustomer(customer);
        //resetCustomerTable();
    }

    public void cancelAndExit(ActionEvent actionEvent) {
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

    public ObservableList<FirstLevel> setDivision(){
        ObservableList<FirstLevel> sortedFirstLevel = FXCollections.observableArrayList();
        int ID = newCustCountry.getValue().getCountryID();
        try {
            FirstLevel.firstLevels = DBQuery.getFirstLevel();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < FirstLevel.firstLevels.size(); i++){
            if(FirstLevel.firstLevels.get(i).getCountryID() == ID){
                sortedFirstLevel.add(FirstLevel.firstLevels.get(i));
            }
        }
        return sortedFirstLevel;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            Country.countries = DBQuery.getCountries();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        newCustCountry.setItems(Country.countries);

        try {
            FirstLevel.firstLevels = DBQuery.getFirstLevel();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        newCustFirst.setItems(FirstLevel.firstLevels);
    }
}
