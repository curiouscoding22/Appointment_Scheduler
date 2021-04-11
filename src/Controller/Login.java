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

/**
 * This is the controller class for the login menu.
 */
public class Login implements Initializable {

    private String userName;
    private String password;

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Label timeZoneLocation;
    @FXML private Label errorMessage;


    /**This is the login method. This method confirms the correct username and password have been entered and opens the application. If it has not been entered, the user is prompted to fix the error.
     * @param actionEvent
     * @throws IOException
     */
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

    /**This is the initialize method. This method creates an instance that is used to set the time on the login screen.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Instant instant = Instant.now();
        ZonedDateTime userTime = instant.atZone(TimeZone.getDefault().toZoneId());
        timeZoneLocation.setText(userTime.format(DateTimeFormatter.ofPattern("hh:mm a zzzz")));
    }
}
