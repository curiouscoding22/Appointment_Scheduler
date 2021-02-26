package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FirstLevel {
    private int firstLevelID;
    private String firstLevelName;
    private Country country;

    public FirstLevel(){}

    public FirstLevel(int firstLevelID, String firstLevelName, Country country) {
        this.firstLevelID = firstLevelID;
        this.firstLevelName = firstLevelName;
        this.country = country;
    }

    public int getFirstLevelID() {
        return firstLevelID;
    }

    public void setFirstLevelID(int firstLevelID) {
        this.firstLevelID = firstLevelID;
    }

    public String getFirstLevelName() {
        return firstLevelName;
    }

    public void setFirstLevelName(String firstLevelName) {
        this.firstLevelName = firstLevelName;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public static ObservableList<FirstLevel> firstLevels = FXCollections.observableArrayList();

    @Override
    public String toString(){
        return(firstLevelName);
    }
}
