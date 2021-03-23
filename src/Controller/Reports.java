package Controller;

import Model.Contact;
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
import java.util.ResourceBundle;

public class Reports implements Initializable {

    @FXML private TextArea reportTextArea;
    @FXML private ComboBox contactSelect;


    public void runReportOne(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        reportTextArea.clear();
        reportTextArea.setText(ReportQuery.reportOne());
    }

    public void runReportTwo(ActionEvent actionEvent) {
        reportTextArea.clear();
        reportTextArea.appendText(ReportQuery.reportTwo((Contact) contactSelect.getValue()));

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

    }
}
