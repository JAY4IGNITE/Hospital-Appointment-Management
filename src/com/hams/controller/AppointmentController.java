package com.hams.controller;

import com.hams.dao.AppointmentDAO;
import com.hams.model.Appointment;

public class AppointmentController {
    public static boolean bookAppointment(
            String patientId,
            String patientName,
            String doctorId,
            String doctorName,
            String date,
            String timeSlot,
            String symptoms) {

        if (!AppointmentDAO.isTimeSlotAvailable(doctorName, date, timeSlot)) {
            return false;
        }

        Appointment appointment = new Appointment(
                patientId,
                patientName,
                doctorId,
                doctorName,
                date,
                timeSlot,
                symptoms);

        return AppointmentDAO.addAppointment(appointment);
    }
}
