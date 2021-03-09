package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

    public static Connection beginConnection() throws ClassNotFoundException, SQLException {
        Class.forName(MYSQLJDBCDriver);
        conn = DriverManager.getConnection(jdbcURL, username, password);
        System.out.println("Connection established");
        return conn;
    }

    public static void closeConnection() throws SQLException {
        conn.close();
        System.out.println("Connection closed");
    }


}
