package Controller;

import Model.Appointment;
import Model.Contact;
import Model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import utils.DBQuery;
import utils.ReportQuery;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Reports implements Initializable {

    @FXML private TextArea reportTextArea;
    @FXML private ComboBox contactSelect;


    public void runReportOne(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        reportTextArea.clear();
        reportTextArea.appendText("Number of appointments this month:\n");
        reportTextArea.appendText(ReportQuery.reportOne());
    }

    public void runReportTwo(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        reportTextArea.clear();
        Contact contactRep = (Contact) contactSelect.getValue();
        ArrayList<String> schedule = ReportQuery.reportTwo(contactRep);

        schedule.forEach( (i) -> {reportTextArea.appendText(i); reportTextArea.appendText("\n");});
    }

    public void runReportThree(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        reportTextArea.clear();
        reportTextArea.appendText("Number of Customers per country: \n");
        HashMap<String, Integer> reportResult = ReportQuery.reportThree();
        reportResult.forEach((key, value) -> reportTextArea.appendText(key + " : " + value + "\n"));



    }

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
