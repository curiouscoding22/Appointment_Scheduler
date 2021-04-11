package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This is the User class. This class creates user objects.
 */
public class User {

    private int userID;
    private String userName;
    private String password;

    /**
     * This is the empty constructor used to create user objects.
     */
    public User(){}

    /**This is the full constructor used to create user objects.
     * @param userID
     * @param userName
     * @param password
     */
    public User(int userID, String userName, String password) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
    }

    /**This method returns the user ID number.
     * @return the user ID number.
     */
    public int getUserID() {
        return userID;
    }

    /**This method sets the user ID number.
     * @param userID the user ID number to set.
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**This method returns the user's name.
     * @return the user's name.
     */
    public String getUserName() {
        return userName;
    }

    /**This method sets the user's name.
     * @param userName the username to set.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**This method returns the password.
     * @return the password.
     */
    public String getPassword() {
        return password;
    }

    /**This method sets the user's password.
     * @param password the password to be set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public static ObservableList<User> users = FXCollections.observableArrayList();

    @Override
    public String toString(){
        return(userName);
    }

}
