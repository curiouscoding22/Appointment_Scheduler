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
import utils.DBQuery;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


/**
 * This class is the controller for the add customer menu.
 */
public class AddCustomer implements Initializable {

    @FXML private TextField newCustID;
    @FXML private TextField newCustName;
    @FXML private TextField newCustAddress;
    @FXML private TextField newCustPhone;
    @FXML private TextField newCustPost;
    @FXML private ComboBox<Country> newCustCountry;
    @FXML private ComboBox<FirstLevel> newCustFirst;
    @FXML private Button cancelButton;


    /**This is the save new customer method. This method collects the information from the form's fields and creates a customer which is added to the database.
     * @param actionEvent this is the event triggered when the user clicks the "Save" button.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void saveNewCustomer(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        int newID = Customer.customers.size() + 1;
        String newName = newCustName.getText();
        String newAddress = newCustAddress.getText();
        String newPhone = newCustPhone.getText();
        String newPost = newCustPost.getText();
        FirstLevel newFirstID = newCustFirst.getValue();
        int correctFLID = newFirstID.getFirstLevelID();

        Customer customer = new Customer(newID, newName, newAddress, newPost, newPhone, newFirstID.getFirstLevelName(), correctFLID);
        DBQuery.addNewCustomer(customer);
        DBQuery.updateCustomerList();
    }

    /**This is the cancel method. This method confirms the user's intent and closes the window.
     * @param actionEvent user clicking the "Cancel" button
     */
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

    /**This method sets the division for the combo box choice based on the user selecting the customer's country.
     * @param actionEvent sets the list in the First Division combo box
     */
    public void setDivisons(ActionEvent actionEvent) {
        Country country = newCustCountry.getValue();
        ObservableList<FirstLevel> divisions = FirstLevel.firstLevels;
        ObservableList<FirstLevel> sortedDivisions = FXCollections.observableArrayList();
        for(FirstLevel div: divisions){
            if(country.getCountryID() == div.getCountry().getCountryID()){
                sortedDivisions.add(div);
            }
        }
        newCustFirst.setItems(sortedDivisions);
    }

    /** This method initializes the combo boxes as well as sets the customer ID as uneditable.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        newCustID.setText("Auto-generated");
        newCustID.setEditable(false);

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
