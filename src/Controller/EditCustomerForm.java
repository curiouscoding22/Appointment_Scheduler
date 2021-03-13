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


