package utils;

import Model.Contact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is the report query class. This class contains methods for running reports on the database.
 */
public class ReportQuery {

    /**This is the first report method. This method runs a query to find the number of different tpyes of reports within the next month period.
     * @return a string with the type of report and number of occurances of that type.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static String reportOne() throws SQLException, ClassNotFoundException {
        String month = "";
        String reportResult = "";
        Connection connection = DBConnection.beginConnection();
        String sqlQuery = "SELECT month(start) as 'month', type, COUNT(*) as 'count' FROM appointments group by month, type";
        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        ResultSet result = statement.executeQuery();
        while(result.next()){
            int numMonth = result.getInt("month");
            switch(numMonth){
                case 1:
                    month = "January";
                    break;
                case 2:
                    month = "February";
                    break;
                case 3:
                    month = "March";
                    break;
                case 4:
                    month = "April";
                    break;
                case 5:
                    month = "May";
                    break;
                case 6:
                    month = "June";
                    break;
                case 7:
                    month = "July";
                    break;
                case 8:
                    month = "August";
                    break;
                case 9:
                    month = "September";
                    break;
                case 10:
                    month = "October";
                    break;
                case 11:
                    month = "November";
                    break;
                case 12:
                    month = "December";
                    break;
            }
            reportResult += month/*String.valueOf(result.getInt("month"))*/ + " " + result.getString("type") + " " + (result.getInt("count")) + "\n";
        }
        return reportResult;
    }

    /**This is the second report method. This report takes a contact name and runs a query to the database to return a schedule of that contact's appointments.
     * @param selectedContact the contact that the schedule is being checked for.
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static ArrayList<String> reportTwo(Contact selectedContact) throws SQLException, ClassNotFoundException {
        String appID;
        String title;
        String type;
        String desc;
        LocalDateTime start;
        LocalDateTime end;
        String cusID;
        ArrayList<String> contactApps = new ArrayList<String>();
        String app = null;
        Connection connection = DBConnection.beginConnection();
        String sqlQuery = "SELECT appointment_ID, title, type, description, start, end, customer_ID FROM appointments WHERE contact_ID = ?";
        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        statement.setInt(1, selectedContact.getContactID());
        ResultSet result = statement.executeQuery();
        while(result.next()){
            appID = result.getString(String.valueOf("appointment_ID"));
            title = result.getString("title");
            type = result.getString("type");
            desc = result.getString("description");
            start = result.getTimestamp("start").toLocalDateTime();
            end = result.getTimestamp("end").toLocalDateTime();
            cusID = result.getString(String.valueOf("customer_ID"));

            app = "Appointment ID: " + appID + "\n" +
                    "Title: " + title + "\n" +
                    "Type: " + type + "\n" +
                    "Description: " + desc + "\n" +
                    "Start: " + start + "\n" +
                    "End: " + end + "\n" +
                    "Customer ID: " + cusID + "\n";
            contactApps.add(app);
        }
        return contactApps;
    }

    /**This is the third report method. This report runs a query to the database to determine the number of customers in the countries where the company has locations.
     * @return a hashmap of key, value pairs of countries and customers.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static HashMap<String, Integer> reportThree() throws SQLException, ClassNotFoundException {
        String country;
        int cusCount = 1;
        HashMap<String, Integer> map = new HashMap<>();
        Connection connection = DBConnection.beginConnection();
        String sqlQuery = "SELECT customer_name, countries.country FROM customers, first_level_divisions, countries\n" +
                "WHERE customers.Division_ID = first_level_divisions.Division_ID AND first_level_divisions.Country_ID = countries.Country_ID;";
        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        ResultSet result = statement.executeQuery();
        while(result.next()){
            country = result.getString("country");
            if(map.containsKey(country)){
                map.put(country, cusCount + 1);
            } else {
                map.put(country, cusCount);
            }
        }
        return map;
    }

}
