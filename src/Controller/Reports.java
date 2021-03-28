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
import java.util.ResourceBundle;

public class Reports implements Initializable {

    @FXML private TextArea reportTextArea;
    @FXML private ComboBox contactSelect;


    public void runReportOne(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        reportTextArea.clear();
        reportTextArea.setText(ReportQuery.reportOne());
    }

    public void runReportTwo(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        reportTextArea.clear();
        Contact contactRep = (Contact) contactSelect.getValue();
        ArrayList<String> schedule = ReportQuery.reportTwo(contactRep);
        for(String i : schedule){
            reportTextArea.appendText(i);
            reportTextArea.appendText("\n");
        }

    }

    public void runReportThree(ActionEvent actionEvent) {
        reportTextArea.clear();
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
