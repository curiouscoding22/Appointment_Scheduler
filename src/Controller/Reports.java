package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import utils.ReportQuery;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Reports implements Initializable {

    @FXML private TextArea reportTextArea;

    public void runReportOne(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        reportTextArea.clear();
        reportTextArea.setText(ReportQuery.reportOne());
    }

    public void runReportTwo(ActionEvent actionEvent) {
    }

    public void runReportThree(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
