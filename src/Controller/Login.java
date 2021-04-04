package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class Login implements Initializable {

    private String userName;
    private String password;

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Label timeZoneLocation;
    @FXML private Label errorMessage;


    @FXML private void applicationLogin(ActionEvent actionEvent) throws IOException {

        String filename = "src/Activity_Log.txt";
        FileWriter writer = new FileWriter(filename, true);
        PrintWriter outputFile = new PrintWriter(writer);


        if(usernameField.getText().isEmpty() || passwordField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setContentText("Please enter the username or password");
            alert.showAndWait();
        }

        userName = usernameField.getText();
        password = passwordField.getText();

        if(userName.equals("test") && password.equals("test")){

            outputFile.println("Successful: " + userName + " : " + LocalDateTime.now());
            outputFile.close();


            Parent parent = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else{

            outputFile.println("Failed: " + userName + " : " + LocalDateTime.now());
            outputFile.close();
            errorMessage.setText("Attempt failed. Please check username and password.");
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Instant instant = Instant.now();
        ZonedDateTime userTime = instant.atZone(TimeZone.getDefault().toZoneId());
        timeZoneLocation.setText(userTime.format(DateTimeFormatter.ofPattern("hh:mm a zzzz")));
    }
}
