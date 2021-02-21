package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Country {

    private int countryID;
    private String countryName;

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Country(){}

    public Country(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }

    public static ObservableList<Country> countries = FXCollections.observableArrayList();

    @Override
    public String toString(){
        return(countryName);
    }
}
