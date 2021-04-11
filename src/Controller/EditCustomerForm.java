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
 * This is the controller class for the edit customer menu,
 */
public class EditCustomerForm implements Initializable {

    @FXML private TextField updateCustID;
    @FXML private TextField updateCustName;
    @FXML private TextField updateCustAddress;
    @FXML private TextField updatePostCode;
    @FXML private TextField updatePhone;
    @FXML private ComboBox updateCountry;
    @FXML private ComboBox updateFirstDiv;

    @FXML private Button cancelButton;
    private Customer customer;


    /**This is the customer retrieval method. When the user selects a customer to edit, this method collects the customer information and puts it into the fields for editing.
     * @param selectCustomer
     */
    public void retrieveSelectCustomer(Customer selectCustomer) {
        this.customer = selectCustomer;
        updateCustID.setText(Integer.toString(customer.getCustomerID()));
        updateCustName.setText(customer.getCustomerName());
        updateCustAddress.setText(customer.getAddress());
        updatePostCode.setText(customer.getPostCode());
        updatePhone.setText(customer.getPhone());

        for(int i = 0; i < FirstLevel.firstLevels.size(); ++i){
            if(customer.getFirstLevel().equals(FirstLevel.firstLevels.get(i).getFirstLevelName())) {
                updateFirstDiv.setValue(FirstLevel.firstLevels.get(i));
                updateCountry.setValue(FirstLevel.firstLevels.get(i).getCountry());
            }
        }
    }

    /**This is the cancel method. This method confirms the user's intent and closes the window.
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

    /**This is the set divison method. This method sets the division ID for the customer object based on the users choice in the Country combo box.
     * @param actionEvent
     */
    public void setUpdatedDIv(ActionEvent actionEvent) {
        Country country = (Country) updateCountry.getValue();
        ObservableList<FirstLevel> divisions = FirstLevel.firstLevels;
        ObservableList<FirstLevel> sortedDivisions = FXCollections.observableArrayList();
        for (FirstLevel div : divisions) {
            if (country.getCountryID() == div.getCountry().getCountryID()) {
                sortedDivisions.add(div);
            }
        }
        updateFirstDiv.setItems(sortedDivisions);
    }

    /**This method initializes and sets the information for the combo boxes on the edit customer form
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        updateCustID.setEditable(false);

        try {
            Country.countries = DBQuery.getCountries();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        updateCountry.setItems(Country.countries);

        try {
            FirstLevel.firstLevels = DBQuery.getFirstLevel();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        updateFirstDiv.setItems(FirstLevel.firstLevels);

    }

    /**THis is the save updated customer method. This method collects the user entered information and updates the user in the database by running the SQL query and matching the customer ID number.
     * @param actionEvent
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void saveUpdatedCustomer(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        int updateID = Integer.parseInt(updateCustID.getText());
        String updateName = updateCustName.getText();
        String updateAddress = updateCustAddress.getText();
        String updatedPhone = updatePhone.getText();
        String updatePost = updatePostCode.getText();
        FirstLevel updateFirstID = (FirstLevel) updateFirstDiv.getValue();
        int updateFLID = updateFirstID.getFirstLevelID();

        Customer updatedCustomer = new Customer(updateID, updateName, updateAddress, updatePost, updatedPhone, updateFirstID.getFirstLevelName(),updateFLID);

        DBQuery.updateCustomer(updatedCustomer);
        DBQuery.updateCustomerList();


    }
}


