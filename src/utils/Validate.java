package utils;

import Model.Appointment;
import javafx.scene.control.Alert;

import java.time.*;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This is the validation class. This class contains methods used to assist the user in validating appointment times and alerting them in the case of a rapidly approaching appointment when they log in.
 */
public class Validate {

    /**This method is used when creating or updating an appointment to confirm that the proposed start and end times fall within the business' hours of operation.
     * @param appointment the appointment being checked.
     * @return
     */
    public static boolean businessHoursCheck(Appointment appointment){
        ZonedDateTime startTime = appointment.getStart().atZone(ZoneId.of("America/New_York"));
        ZonedDateTime endTime = appointment.getEnd().atZone(ZoneId.of("America/New_York"));

        ZonedDateTime businessOpen = ZonedDateTime.of(startTime.toLocalDate(), LocalTime.of(8, 00), ZoneId.of("America/New_York"));
        ZonedDateTime businessClose = ZonedDateTime.of(endTime.toLocalDate(), LocalTime.of(22, 00), ZoneId.of("America/New_York"));

        if((startTime.isEqual(businessOpen) || startTime.isAfter(businessOpen)) && startTime.isBefore(businessClose) && endTime.isAfter(businessOpen) && (endTime.isBefore(businessClose) || endTime.isEqual(businessClose))){
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
            if (appointment.getCustomerID() == app.getCustomerID()) {
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
        for (int i = 0; i < Appointment.appointments.size(); ++i) {
            Appointment app = (Appointment) Appointment.appointments.get(i);
            if (app.getStart().isAfter(userLogin) && app.getStart().isBefore(checkWindow)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Appointment Soon");
                alert.setContentText("Appointment ID: " + app.getAppointmentID() + " at " + app.getStart().format(DateTimeFormatter.ofPattern("hh:mm a zzzz")) + " is starting soon.");
                alert.showAndWait();
                return true;
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Appointment Alert");
                alert.setContentText("No appointments within 15 minutes.");
                alert.showAndWait();
                break;
            }
        }
        return false;
    }

}
