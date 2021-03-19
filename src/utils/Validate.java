package utils;

import Model.Appointment;
import javafx.scene.control.Alert;

import java.time.*;
import java.time.chrono.ChronoLocalDateTime;

public class Validate {

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

    public static boolean appointmentOverlapCheck(Appointment appointment){
        boolean isOverlapping = true;
        LocalDateTime startUTC = appointment.getStart();
        LocalDateTime endUTC = appointment.getEnd();

        for(int i = 0; i < Appointment.appointments.size(); ++i) {
            Appointment app = (Appointment) Appointment.appointments.get(i);
            if (appointment.getCustomerID() == app.getCustomerID()) {
                if(appointment.getStart().isBefore(app.getStart()) && appointment.getEnd().isBefore(app.getStart()) && appointment.getStart().isAfter(app.getEnd()))  {
                    System.out.println("This does not overlap.");
                    isOverlapping = false;
                }
            }
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Overlapping Appointment");
        alert.setContentText("This customer already has an appointment at that time");
        alert.showAndWait();
        return isOverlapping;
    }

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
