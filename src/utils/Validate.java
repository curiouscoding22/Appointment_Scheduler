package utils;

import Model.Appointment;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
        boolean notOverlapping = false;
        LocalDateTime startUTC = appointment.getStart();
        LocalDateTime endUTC = appointment.getEnd();

        for(int i = 0; i < Appointment.appointments.size(); ++i){
            Appointment app = (Appointment) Appointment.appointments.get(i);
            if(appointment.getCustomerID() == app.getCustomerID()){
                if(app.getStart().isBefore(startUTC) && app.getEnd().isBefore(startUTC) && app.getStart().isAfter(endUTC)){
                    notOverlapping = true;
                    System.out.println("This does not overlap.");
                }
            }

        }
        return notOverlapping;
    }


}
