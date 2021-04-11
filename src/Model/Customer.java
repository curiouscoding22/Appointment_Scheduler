package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This is the customer class. This class is used to create customer objects.
 */
public class Customer {

    private int customerID;
    private String customerName;
    private String address;
    private String postCode;
    private String phone;
    private String firstLevel;
    private int firstLevelID;

    /**
     * This is the empty constructor for customer objects.
     */
    public Customer(){}

    /**This method returns the first level.
     * @return the first level name.
     */
    public String getFirstLevel() {
        return firstLevel;
    }

    /**This method sets the first level name.
     * @param firstLevel the first level name to be set.
     */
    public void setFirstLevel(String firstLevel) {
        this.firstLevel = firstLevel;
    }

    /**This method returns the first level ID number.
     * @return the first level ID.
     */
    public int getFirstLevelID() {
        return firstLevelID;
    }

    /**This method sets the first level ID.
     * @param firstLevelID the ID number to be set.
     */
    public void setFirstLevelID(int firstLevelID) {
        this.firstLevelID = firstLevelID;
    }

    /**This is the full constructor for creating customer objects.
     * @param customerID
     * @param customerName
     * @param address
     * @param postCode
     * @param phone
     * @param firstLevel
     * @param firstLevelID
     */
    public Customer(int customerID, String customerName, String address, String postCode, String phone, String firstLevel, int firstLevelID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postCode = postCode;
        this.phone = phone;
        this.firstLevel = firstLevel;
        this.firstLevelID = firstLevelID;
    }

    /**This method returns the customer ID number.
     * @return the customer ID.
     */
    public int getCustomerID() {
        return customerID;
    }

    /**This method sets the customer ID number.
     * @param customerID the ID number to set.
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**This method returns the customer name.
     * @return the customer name.
     */
    public String getCustomerName() {
        return customerName;
    }

    /**This method sets the customer name.
     * @param customerName the customer name being set.
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**This method returns the customer's address.
     * @return the customer's address.
     */
    public String getAddress() {
        return address;
    }

    /**This method sets the customer's address.
     * @param address the address to be set.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**This method returns the customer's postal code.
     * @return the customer's postal code.
     */
    public String getPostCode() {
        return postCode;
    }

    /**This method sets the customer's postal code.
     * @param postCode the postal code to be set.
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    /**This method returns the customer's phone number.
     * @return the customer's phone number.
     */
    public String getPhone() {
        return phone;
    }

    /**This method sets the customer's phone number.
     * @param phone the phone number to be set.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }


    public static ObservableList<Customer> customers = FXCollections.observableArrayList();

    @Override
    public String toString(){
        return(customerName);
    }
}
