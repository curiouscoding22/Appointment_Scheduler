package utils;

import Controller.CustomerMenu;
import Model.Appointment;
import Model.Country;
import Model.Customer;
import Model.FirstLevel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DBQuery {

    //public static ObservableList<Country> countries = FXCollections.observableArrayList();

    public static ObservableList<Appointment> getAppointments() throws SQLException, ClassNotFoundException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        Connection connection = DBConnection.beginConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT appointment_ID, title, description, location, type, start, end, customer_ID, contacts.contact_Name FROM appointments,contacts WHERE appointments.contact_ID = contacts.contact_ID");
        ResultSet result = statement.executeQuery();
        while(result.next()){
            Appointment appointment = new Appointment();
            appointment.setAppointmentID(result.getInt("appointment_ID"));
            appointment.setTitle(result.getString("title"));
            appointment.setDescription(result.getString("description"));
            appointment.setLocation(result.getString("location"));
            appointment.setType(result.getString("type"));
            appointment.setStart(result.getTimestamp("start").toLocalDateTime());
            appointment.setEnd((result.getTimestamp("end").toLocalDateTime()));
            appointment.setCustomerID(result.getInt("customer_ID"));
            appointment.setContact(result.getString("contact_Name"));
            appointments.add(appointment);
        }
        return appointments;
    }

    public static ObservableList<Customer> getCustomers() throws SQLException, ClassNotFoundException {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        Connection connection = DBConnection.beginConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT customer_ID, customer_Name, address, postal_Code, phone, division " +
                        "FROM customers,first_level_divisions WHERE customers.division_ID = first_level_divisions.division_ID");
        ResultSet result = statement.executeQuery();
        while(result.next()){
            Customer customer = new Customer();
            customer.setCustomerID(result.getInt("customer_ID"));
            customer.setCustomerName(result.getString("customer_Name"));
            customer.setAddress(result.getString("address"));
            customer.setFirstLevel(result.getString("division"));
            customer.setPostCode(result.getString("postal_code"));
            customer.setPhone(result.getString("phone"));
            customers.add(customer);
        }
        return customers;
    }

    public static ObservableList<Country> getCountries() throws SQLException, ClassNotFoundException {
        ObservableList<Country> DBcountries = FXCollections.observableArrayList();
        Connection connection = DBConnection.beginConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT country_ID, country FROM countries WHERE country_ID = 1 OR 2 OR 3");
        ResultSet result = statement.executeQuery();
        while(result.next()){
            Country country = new Country();
            country.setCountryID(result.getInt("country_ID"));
            country.setCountryName(result.getString("country"));
            DBcountries.add(country);
        }
        return DBcountries;
    }

    public static ObservableList<FirstLevel> getFirstLevel() throws SQLException, ClassNotFoundException {
        ObservableList<FirstLevel> firstLevels = FXCollections.observableArrayList();
        Connection connection = DBConnection.beginConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT division_ID, division, country_ID FROM first_level_divisions");
        ResultSet result = statement.executeQuery();
        while(result.next()){
            FirstLevel firstLevel = new FirstLevel();
            firstLevel.setFirstLevelID(result.getInt("division_ID"));
            firstLevel.setFirstLevelName(result.getString("division"));
            firstLevel.setCountryID(result.getInt("country_ID"));
            firstLevels.add(firstLevel);
        }
        return firstLevels;
    }

    public static void addNewCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.beginConnection();
        String sqlQuery =
                "INSERT INTO customers (customer_ID, customer_name, address, postal_code, phone, create_date, created_by, last_update, last_updated_by, division_ID) " +
                        "VALUES (NULL, ?, ?, ?, ?, now(), 'User', now(), 'User', ?)";
        PreparedStatement statement = connection.prepareStatement(sqlQuery) ;
        //statement.setInt(1, customer.getCustomerID());
        statement.setString(1, customer.getCustomerName());
        statement.setString(2, customer.getAddress());
        statement.setString(3, customer.getPostCode());
        statement.setString(4, customer.getPhone());
        statement.setInt(5, customer.getFirstLevelID());
        statement.execute();
    }

    public static void updateCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.beginConnection();
        String sqlQuery =
                "INSERT INTO customers (customer_ID, customer_name, address, postal_code, phone, last_update, last_updated_by, division_ID) " +
                        "VALUES (NULL, ?, ?, ?, ?, now(), 'User', ?)";
        PreparedStatement statement = connection.prepareStatement(sqlQuery) ;
        //statement.setInt(1, customer.getCustomerID());
        statement.setString(1, customer.getCustomerName());
        statement.setString(2, customer.getAddress());
        statement.setString(3, customer.getPostCode());
        statement.setString(4, customer.getPhone());
        statement.setInt(5, customer.getFirstLevelID());
        statement.execute();
    }

}
