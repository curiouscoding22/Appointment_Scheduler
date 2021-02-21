package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FirstLevel {
    private int firstLevelID;
    private String firstLevelName;
    private int countryID;

    public FirstLevel(){}

    public FirstLevel(int firstLevelID, String firstLevelName, int countryID) {
        this.firstLevelID = firstLevelID;
        this.firstLevelName = firstLevelName;
        this.countryID = countryID;
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

    public void setCountryID(int countryID){
        this.countryID = countryID;
    }

    public int getCountryID(){
        return countryID;
    }

    public static ObservableList<FirstLevel> firstLevels = FXCollections.observableArrayList();

    @Override
    public String toString(){
        return(firstLevelName);
    }
}
