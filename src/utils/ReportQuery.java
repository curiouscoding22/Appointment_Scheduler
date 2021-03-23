package utils;

import Model.Appointment;
import Model.Contact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    public static String reportTwo(Contact selectedContact) {
        LocalDate today = LocalDate.now();
        String schedule = null;
        for(int i = 0; i < Appointment.appointments.size(); ++i){
            Appointment app = (Appointment) Appointment.appointments.get(i);
            if(selectedContact.getContactID() == app.getContactID() && today == app.getStart().toLocalDate()){
                schedule = app.getAppointmentID() + " " + app.getTitle() + " " + app.getType() + " " + app.getDescription() + " " + app.getStart() + " " + app.getEnd() + " " + app.getCustomerID();
                System.out.println(schedule);
            }
        }
        return schedule;
    }

}
