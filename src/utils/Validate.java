package utils;

import Model.Appointment;
import javafx.scene.control.Alert;

import java.time.*;
import java.time.chrono.ChronoLocalDateTime;

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
        LocalDateTime endTime = appointment.getEnd();

        ZonedDateTime businessOpen = ZonedDateTime.of(startTime.toLocalDate(), LocalTime.of(7, 59), ZoneId.of("America/New_York"));
        ZonedDateTime businessClose = ZonedDateTime.of(endTime.toLocalDate(), LocalTime.of(22, 00), ZoneId.of("America/New_York"));

        if(startTime.isAfter(businessOpen) && startTime.isBefore(businessClose) && endTime.isAfter(ChronoLocalDateTime.from(businessOpen)) && endTime.isBefore(ChronoLocalDateTime.from(businessClose))){
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
        LocalDateTime startUTC = appointment.getStart();
        LocalDateTime endUTC = appointment.getEnd();

        for(int i = 0; i < Appointment.appointments.size(); ++i) {
            Appointment app = (Appointment) Appointment.appointments.get(i);
            if (appointment.getCustomerID() == app.getCustomerID()) {
                if(startUTC.isAfter(app.getStart()) && startUTC.isBefore(app.getEnd()))  {
                    System.out.println("Start is overlapping");
                    isOverlapping = true;
                } else if(endUTC.isAfter(app.getStart()) && endUTC.isBefore(app.getEnd())){
                    isOverlapping = true;
                } else if(app.getStart().isAfter(startUTC) && app.getEnd().isBefore(endUTC)){
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
                alert.setContentText(app.getDescription() + " appointment is starting soon.");
                alert.showAndWait();
                return true;
            }
        }
        return false;
    }

}
