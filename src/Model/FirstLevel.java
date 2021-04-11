package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This is the first level class. This class creates first level objects.
 */
public class FirstLevel {
    private int firstLevelID;
    private String firstLevelName;
    private Country country;

    /**
     * This is the empty constructor for creating first level objects.
     */
    public FirstLevel(){}

    /**This is the full constructor for creating First Level objects.
     * @param firstLevelID
     * @param firstLevelName
     * @param country
     */
    public FirstLevel(int firstLevelID, String firstLevelName, Country country) {
        this.firstLevelID = firstLevelID;
        this.firstLevelName = firstLevelName;
        this.country = country;
    }

    /**This method returns the first level ID number.
     * @return the first level ID number.
     */
    public int getFirstLevelID() {
        return firstLevelID;
    }

    /**This method sets the first level ID number.
     * @param firstLevelID the ID number to be set.
     */
    public void setFirstLevelID(int firstLevelID) {
        this.firstLevelID = firstLevelID;
    }

    /**This method returns the first level's name.
     * @return the first level name.
     */
    public String getFirstLevelName() {
        return firstLevelName;
    }

    /**This method sets the first level name.
     * @param firstLevelName the first level name to be set.
     */
    public void setFirstLevelName(String firstLevelName) {
        this.firstLevelName = firstLevelName;
    }

    /**This method returns the country name associated with the first level.
     * @return the country name.
     */
    public Country getCountry() {
        return country;
    }

    /**This method sets the country name associated with the first level.
     * @param country the country name to set.
     */
    public void setCountry(Country country) {
        this.country = country;
    }

    public static ObservableList<FirstLevel> firstLevels = FXCollections.observableArrayList();

    @Override
    public String toString(){
        return(firstLevelName);
    }
}
