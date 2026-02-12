package com.hams.controller;

import com.hams.dao.AppointmentDAO;
import com.hams.model.Appointment;

public class AppointmentController {
    public static boolean bookAppointment(
            String patientName,
            String doctorName,
            String date,
            String timeSlot,
            String symptoms) {

        if (!AppointmentDAO.isTimeSlotAvailable(doctorName, date, timeSlot)) {
            return false;
        }

        Appointment appointment = new Appointment(
                patientName,
                doctorName,
                date,
                timeSlot,
                symptoms);

        AppointmentDAO.addAppointment(appointment);
        return true;
    }
}
