package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Customer {

    private int customerID;
    private String customerName;
    private String address;
    private String postCode;
    private String phone;
    private String firstLevel;
    private int firstLevelID;

    public Customer(){

    }

    public String getFirstLevel() {
        return firstLevel;
    }

    public void setFirstLevel(String firstLevel) {
        this.firstLevel = firstLevel;
    }

    public int getFirstLevelID() {
        return firstLevelID;
    }

    public void setFirstLevelID(int firstLevelID) {
        this.firstLevelID = firstLevelID;
    }

    public Customer(int customerID, String customerName, String address, String postCode, String phone, String firstLevel, int firstLevelID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postCode = postCode;
        this.phone = phone;
        this.firstLevel = firstLevel;
        this.firstLevelID = firstLevelID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public static ObservableList<Customer> customers = FXCollections.observableArrayList();
}
