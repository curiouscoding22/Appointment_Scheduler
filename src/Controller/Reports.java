package Controller;

import Model.Contact;
import Model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import utils.DBQuery;
import utils.ReportQuery;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Reports implements Initializable {

    @FXML private TextArea reportTextArea;
    @FXML private ComboBox contactSelect;


    /**This is the first report button method. When selected by the user, this method runs the first report SQL query and updates the text field with the returned information.
     * @param actionEvent the user clicking the report one button.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void runReportOne(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        reportTextArea.clear();
        reportTextArea.appendText("Number of appointments this month:\n");
        reportTextArea.appendText(ReportQuery.reportOne());
    }

    /**This is the second report button method. When selected by the user, this method runs the first report SQL query and updates the text field with the returned information.This method uses a lambda expression to append the contents of the string to the text area which improves code readability and comprehension as well as make the code more concise.
     * @param actionEvent the user clicking the report two button.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void runReportTwo(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        reportTextArea.clear();
        Contact contactRep = (Contact) contactSelect.getValue();
        ArrayList<String> schedule = ReportQuery.reportTwo(contactRep);

        schedule.forEach( (i) -> {reportTextArea.appendText(i); reportTextArea.appendText("\n");});
    }

    /**This is the third report button method. When selected by the user, this method runs the first report SQL query and updates the text field with the returned information. This method uses a lambda expression to append the contents of the hashmap to the text area which improves code readability and comprehension as well as make the code more concise.
     * @param actionEvent the user clicking the report three button
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void runReportThree(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        reportTextArea.clear();
        reportTextArea.appendText("Number of Customers per country: \n");
        HashMap<String, Integer> reportResult = ReportQuery.reportThree();
        reportResult.forEach((key, value) -> reportTextArea.appendText(key + " : " + value + "\n"));



    }

    /**This is the initialize method for this class. This method populates the contact combo box and customer list.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try{
            Contact.contacts = DBQuery.getContacts();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        contactSelect.setItems(Contact.contacts);

        try {
            Customer.customers = DBQuery.getCustomers();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
