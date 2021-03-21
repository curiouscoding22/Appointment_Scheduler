package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ReportQuery {


    public static String reportOne() throws SQLException, ClassNotFoundException {
        LocalDateTime current = LocalDateTime.now();
        LocalDateTime oneMonth = current.plusMonths(1);
        String reportResult = null;
        Connection connection = DBConnection.beginConnection();
        String sqlQuery = "SELECT type COUNT(*) as count FROM appointments WHERE start >=" + current + "AND start <" + oneMonth + "GROUP BY type";
        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        ResultSet result = statement.executeQuery();
        while(result.next()){
            reportResult = String.valueOf(result.getInt("count")) + "number of " + result.getString("type") + " appointments.\n";
        }

        return reportResult;

    }

}
