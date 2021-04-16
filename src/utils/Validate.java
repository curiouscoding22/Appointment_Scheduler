package utils;

import Model.Appointment;
import javafx.scene.control.Alert;
import java.time.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * This is the validation class. This class contains methods used to assist the user in validating appointment times and alerting them in the case of a rapidly approaching appointment when they log in.
 */
public class Validate {

    /**This method is used when creating or updating an appointment to confirm that the proposed start and end times fall within the business' hours of operation.
     * @param appointment the appointment being checked.
     * @return
     */
    public static boolean businessHoursCheck(Appointment appointment){

        ZonedDateTime start = appointment.getStart().atZone(ZoneId.systemDefault());
        ZonedDateTime end = appointment.getEnd().atZone(ZoneId.systemDefault());

        ZonedDateTime zonedStartTime = start.withZoneSameInstant(ZoneId.of("America/New_York"));
        ZonedDateTime zonedEndTime = end.withZoneSameInstant(ZoneId.of("America/New_York"));

        ZonedDateTime businessOpen = ZonedDateTime.of(zonedStartTime.toLocalDate(), LocalTime.of(8, 00), ZoneId.of("America/New_York"));
        ZonedDateTime businessClose = ZonedDateTime.of(zonedEndTime.toLocalDate(), LocalTime.of(22, 00), ZoneId.of("America/New_York"));

        if((zonedStartTime.isEqual(businessOpen) || zonedStartTime.isAfter(businessOpen)) && zonedStartTime.isBefore(businessClose) && zonedEndTime.isAfter(businessOpen) && (zonedEndTime.isBefore(businessClose) || zonedEndTime.isEqual(businessClose))){
            return true;
        }
        return false;
    }

    /**This method is used when creating or updating an appointment to confirm the proposed start and end times do not overlap with existing appointments. If they do, the user is alerted and prompted to correct the times.
     * @param appointment the appointment being checked,
     * @return
     */
    public static boolean appointmentOverlapCheck(Appointment appointment){
        boolean isOverlapping = false;
        LocalDateTime checkStart = appointment.getStart();
        LocalDateTime checkEnd = appointment.getEnd();

        for(int i = 0; i < Appointment.appointments.size(); ++i) {
            Appointment app = (Appointment) Appointment.appointments.get(i);
            if (appointment.getCustomerID() == app.getCustomerID() && appointment.getAppointmentID() != app.getAppointmentID()) {
                if(checkStart.isEqual(app.getStart()) || (checkStart.isAfter(app.getStart()) && checkStart.isBefore(app.getEnd()))){
                    System.out.println("Start is overlapping");
                    isOverlapping = true;
                } else if(checkEnd.isEqual(app.getEnd()) || (checkEnd.isAfter(app.getStart()) && checkEnd.isBefore(app.getEnd()))){
                    isOverlapping = true;
                } else if(app.getStart().isAfter(checkStart) && app.getEnd().isBefore(checkEnd)){
                    isOverlapping = true;
                }
            }
        }
        return isOverlapping;
    }

    /**This method alerts the user if there is an appointment scheduled within fifteen minutes of their successful login and provides the description of the appointment for the user to quickly identify which appointment it is. .
     * @return
     */
    public static boolean fifteenMinuteWarning(){
        LocalDateTime userLogin = LocalDateTime.now();
        LocalDateTime checkWindow = userLogin.plusMinutes(15);
        DateTimeFormatter timeFormat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.SHORT);
        for (int i = 0; i < Appointment.appointments.size(); ++i) {
            Appointment app = (Appointment) Appointment.appointments.get(i);
            if (app.getStart().isAfter(userLogin) && app.getStart().isBefore(checkWindow)) {
                String startTime = timeFormat.format(app.getStart());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setResizable(true);
                alert.setTitle("Appointment Soon");
                alert.setContentText("Appointment ID: " + app.getAppointmentID() + " at " + startTime + " is starting soon.");
                alert.showAndWait();
                return true;
            }
        }
        return false;
    }
}
