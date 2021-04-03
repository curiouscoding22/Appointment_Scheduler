package utils;

import Model.Contact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class ReportQuery {


    public static String reportOne() throws SQLException, ClassNotFoundException {
        LocalDateTime current = LocalDateTime.now();
        LocalDateTime oneMonth = current.plusMonths(1);
        String reportResult = null;
        Connection connection = DBConnection.beginConnection();
        String sqlQuery = "SELECT type, COUNT(*) as 'c' FROM appointments WHERE start >='" + current + "'AND start <'" + oneMonth + "'GROUP BY type";
        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        ResultSet result = statement.executeQuery();
        while(result.next()){
            reportResult = String.valueOf(result.getInt("c")) + " " + result.getString("type") + " appointment(s) this month.\n";
        }
        return reportResult;
    }

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
