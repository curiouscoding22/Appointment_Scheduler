package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This is the country class. This class is used to create country objects.
 */
public class Country {

    private int countryID;
    private String countryName;

    /**This method returns the country ID number.
     * @return the country ID
     */
    public int getCountryID() {
        return countryID;
    }

    /**This method sets the country ID.
     * @param countryID the country ID to be set.
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**This method returns the country name.
     * @return the country name.
     */
    public String getCountryName() {
        return countryName;
    }

    /**This method sets the country name.
     * @param countryName the name to be set.
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * This is the empty constructor for creating country objects.
     */
    public Country(){}

    /**This is the full country
     * @param countryID
     * @param countryName
     */
    public Country(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }

    /**This is the sort country method. This method returns a specific country which matches the given country ID number.
     * @param sqlID the ID used to search the list.
     * @return the matching county.
     */
    public static Country sortCountry(int sqlID){
        for(int i = 0; i < countries.size(); ++i){
            if(sqlID == countries.get(i).getCountryID()){
                return countries.get(i);
            }
        }
        return null;
    }

    public static ObservableList<Country> countries = FXCollections.observableArrayList();

    @Override
    public String toString(){
        return(countryName);
    }
}
