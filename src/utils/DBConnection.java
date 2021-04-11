package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This is the DBConnection class. This class establishes and closes the connection of the application to the database.
 */
public class DBConnection {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com/WJ07lye";

    private static final String jdbcURL = protocol + vendor + ipAddress + "?connectionTimeZone=SERVER";

    //Driver Interface Reference
    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver";
    private static Connection conn = null;

    private static final String username = "U07lye";
    private static final String password = "53689065171";

    /**This is the beginConnection method which establishes the connection to the database.
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection beginConnection() throws ClassNotFoundException, SQLException {
        Class.forName(MYSQLJDBCDriver);
        conn = DriverManager.getConnection(jdbcURL, username, password);
        System.out.println("Connection established");
        return conn;
    }

    /**This is the closeConnection method that closes the connection to the database.
     * @throws SQLException
     */
    public static void closeConnection() throws SQLException {
        conn.close();
        System.out.println("Connection closed");
    }


}
