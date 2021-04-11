package utils;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;


/**
 * This is the DBQuery class. This class contains the methods for running
 */
public class DBQuery {

    /**This is the get appointments method. This method runs a query to the database to collect the appointment data for display and manipulation in the application.
     * @return this method returns a list of all appointments in the database.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static ObservableList<Appointment>getAppointments() throws SQLException, ClassNotFoundException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        Connection connection = DBConnection.beginConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT appointment_ID, title, description, location, type, start, end, customer_ID, user_ID, contacts.contact_Name FROM appointments,contacts WHERE appointments.contact_ID = contacts.contact_ID");
        ResultSet result = statement.executeQuery();
        while(result.next()){
            Appointment appointment = new Appointment();
            appointment.setAppointmentID(result.getInt("appointment_ID"));
            appointment.setTitle(result.getString("title"));
            appointment.setDescription(result.getString("description"));
            appointment.setLocation(result.getString("location"));
            appointment.setType(result.getString("type"));
            appointment.setStart(result.getTimestamp("start").toLocalDateTime());
            appointment.setEnd((result.getTimestamp("end").toLocalDateTime()/*.atZone(ZoneId.systemDefault()))*/));
            appointment.setCustomerID(result.getInt("customer_ID"));
            appointment.setUserID(result.getInt("user_ID"));
            appointment.setContact(result.getString("contact_Name"));
            appointments.add(appointment);
        }
        return appointments;
    }

    /**This is the get customers method. This method queries the database to return a list of all customers currently stored.
     * @return this method returns a list of all customers in the database.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
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

    /**This is the get countries method. This method queries the database to return a list of all countries currently stored.
     * @return this method returns a list of all countries in the database
     * @throws SQLException
     * @throws ClassNotFoundException
     */
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

    /**This is the get first level method. This method runs a query to the database and returns a list of all First Level Divisions currently stored.
     * @return this method returns a list of all first level divisions in the database.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static ObservableList<FirstLevel> getFirstLevel() throws SQLException, ClassNotFoundException {
        ObservableList<FirstLevel> firstLevels = FXCollections.observableArrayList();
        Connection connection = DBConnection.beginConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT division_ID, division, country_ID FROM first_level_divisions");
        ResultSet result = statement.executeQuery();
        while(result.next()){
            FirstLevel firstLevel = new FirstLevel();
            firstLevel.setFirstLevelID(result.getInt("division_ID"));
            firstLevel.setFirstLevelName(result.getString("division"));
            firstLevel.setCountry(Country.sortCountry(result.getInt("country_ID")));
            firstLevels.add(firstLevel);
        }
        return firstLevels;
    }

    /**This is the get contacts method. This method runs a query to the database and returns a list of all contacts currently stored.
     * @return this method returns a list of all contacts in the database.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static ObservableList<Contact> getContacts() throws SQLException, ClassNotFoundException {
        ObservableList<Contact> contacts = FXCollections.observableArrayList();
        Connection connection = DBConnection.beginConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM contacts");
        ResultSet result = statement.executeQuery();
        while(result.next()){
            Contact contact = new Contact();
            contact.setContactID(result.getInt("contact_ID"));
            contact.setContactName((result.getString("contact_name")));
            contact.setContactEmail(result.getString("email"));
            contacts.add(contact);
        }
        return contacts;
    }

    /**This is the get users method. This method runs a query to the database and returns a list of all users currently stored.
     * @return this method returns a list of all users currently in the database.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static ObservableList<User> getUsers() throws SQLException, ClassNotFoundException {
        ObservableList<User> users = FXCollections.observableArrayList();
        Connection connection = DBConnection.beginConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
        ResultSet result = statement.executeQuery();
        while(result.next()){
            User user = new User();
            user.setUserID(result.getInt("user_ID"));
            user.setUserName(result.getString("user_name"));
            user.setPassword("password");
            users.add(user);
        }
        return users;
    }

    /**This is the add new customer method. This method takes a customer object and runs a query to add a new customer into the database.
     * @param customer the customer to be added.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
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

    /**This is the update customer method. This method takes a customer object that already exists ana runs a query to save a modified version over the existing version in the database.
     * @param customer the customer being updated.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void updateCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.beginConnection();
        String sqlQuery =
                "UPDATE customers SET customer_name = ?, address = ?, postal_code = ?, phone = ?, last_update = now(), last_updated_by = 'User', division_ID = ? WHERE customer_ID = ?";
        PreparedStatement statement = connection.prepareStatement(sqlQuery) ;
        statement.setString(1, customer.getCustomerName());
        statement.setString(2, customer.getAddress());
        statement.setString(3, customer.getPostCode());
        statement.setString(4, customer.getPhone());
        statement.setInt(5, customer.getFirstLevelID());
        statement.setInt(6, customer.getCustomerID());
        statement.execute();
    }

    /**This is the update customer list method. This method clears the existing customer list and runs a query to the database to repopulate it with all the customers once a customer has been added or updated.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void updateCustomerList() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.beginConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT customer_ID, customer_Name, address, postal_Code, phone, division " +
                        "FROM customers,first_level_divisions WHERE customers.division_ID = first_level_divisions.division_ID");
        ResultSet result = statement.executeQuery();
        Customer.customers.clear();
        while(result.next()){
            Customer customer = new Customer();
            customer.setCustomerID(result.getInt("customer_ID"));
            customer.setCustomerName(result.getString("customer_Name"));
            customer.setAddress(result.getString("address"));
            customer.setFirstLevel(result.getString("division"));
            customer.setPostCode(result.getString("postal_code"));
            customer.setPhone(result.getString("phone"));
            Customer.customers.add(customer);
        }
    }

    /**This is the add new appointment method. This method takes a new appointment object and runs a query to add it to the database.
     * @param appointment the appointment that is being added.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void addNewAppointment(Appointment appointment) throws SQLException, ClassNotFoundException {
        Timestamp startTime = Timestamp.valueOf(appointment.getStart());
        Timestamp endTime = Timestamp.valueOf(appointment.getEnd());
        Connection connection = DBConnection.beginConnection();
        String query =
                "INSERT INTO appointments(appointment_ID, title, description, location, type, start, end, create_date, created_by, last_update, last_updated_by, customer_ID, user_ID, contact_ID)" +
                        "VALUES(NULL, ?, ?, ?, ?, ?, ?, now(), 'User', now(), 'User', ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, appointment.getTitle());
        statement.setString(2, appointment.getDescription());
        statement.setString(3, appointment.getLocation());
        statement.setString(4, appointment.getType());
        statement.setTimestamp(5, startTime);
        statement.setTimestamp(6, endTime);
        statement.setInt(7, appointment.getCustomerID());
        statement.setInt(8, appointment.getUserID());
        statement.setInt(9, appointment.getContactID());
        statement.execute();
    }

    /**This is the update appointment method. This method takes an appointment object and runs a query to update the information with the existing appointment by verifying the appointment ID.
     * @param appointment the appointment that is updated.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void updateAppointment(Appointment appointment) throws SQLException, ClassNotFoundException{
        Timestamp startTime = Timestamp.valueOf(appointment.getStart());
        Timestamp endTime = Timestamp.valueOf(appointment.getEnd());
        Connection connection = DBConnection.beginConnection();
        String query = "UPDATE appointments SET title = ?, description = ?, location = ?, type = ?, start = ?, end = ?, last_update = now(), last_updated_by = 'User', customer_ID = ?, user_ID = ?, contact_ID = ? WHERE appointment_ID = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, appointment.getTitle());
        statement.setString(2, appointment.getDescription());
        statement.setString(3, appointment.getLocation());
        statement.setString(4, appointment.getType());
        statement.setTimestamp(5, startTime);
        statement.setTimestamp(6, endTime);
        statement.setInt(7, appointment.getCustomerID());
        statement.setInt(8, appointment.getUserID());
        statement.setInt(9, appointment.getContactID());
        statement.setInt(10, appointment.getAppointmentID());
        statement.execute();
    }

    /**This is the update appointment list method. This method clears the existing appointment list and runs a query to the database to repopulate it with all the appointments once an appointment has been added or updated.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void updateAppointmentList() throws SQLException, ClassNotFoundException {
        //ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        Connection connection = DBConnection.beginConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT appointment_ID, title, description, location, type, start, end, customer_ID, contacts.contact_Name FROM appointments,contacts WHERE appointments.contact_ID = contacts.contact_ID");
        ResultSet result = statement.executeQuery();
        Appointment.appointments.clear();
        while(result.next()){
            Appointment appointment = new Appointment();
            appointment.setAppointmentID(result.getInt("appointment_ID"));
            appointment.setTitle(result.getString("title"));
            appointment.setDescription(result.getString("description"));
            appointment.setLocation(result.getString("location"));
            appointment.setType(result.getString("type"));
            appointment.setStart(result.getTimestamp("start").toLocalDateTime());
            appointment.setEnd((result.getTimestamp("end").toLocalDateTime()/*.atZone(ZoneId.systemDefault()))*/));
            appointment.setCustomerID(result.getInt("customer_ID"));
            appointment.setContact(result.getString("contact_Name"));
            Appointment.appointments.add(appointment);
        }
        //return appointments;

    }

    /**This is the delete appointment method. This method removes an appointment from the database.
     * @param selectedItem the appointment to be deleted.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void deleteAppointment(Appointment selectedItem) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.beginConnection();
        String sqlQuery = "DELETE FROM appointments WHERE appointment_ID = ?";
        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        statement.setInt(1, selectedItem.getAppointmentID());
        statement.execute();
    }

    /**This is the delete customer method. This method first deletes any associated appointments due to foreign key constraints then deletes the specified customer.
     * @param selectedItem the customer to be deleted.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void deleteCustomer(Customer selectedItem) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.beginConnection();
        String sqlAppQuery = "DELETE FROM appointments WHERE customer_ID = ?";
        String sqlCusQuery = "DELETE FROM customers WHERE customer_ID = ?";
        PreparedStatement appStatement = connection.prepareStatement(sqlAppQuery);
        appStatement.setInt(1, selectedItem.getCustomerID());
        appStatement.execute();
        PreparedStatement cusStatement = connection.prepareStatement(sqlCusQuery);
        cusStatement.setInt(1, selectedItem.getCustomerID());
        cusStatement.execute();
    }
}
