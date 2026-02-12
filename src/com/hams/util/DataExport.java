package com.hams.util;

import com.hams.model.Appointment;
import com.hams.model.Doctor;
import com.hams.model.Patient;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class DataExport {

    public static void exportToCSV(List<Doctor> doctors, List<Patient> patients, List<Appointment> appointments,
            String filePath) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {

            // Doctors Section
            writer.println("--- DOCTORS ---");
            writer.println("Doctor ID, Name, Specialization, Hospital, Email");
            for (Doctor d : doctors) {
                writer.printf("%s, %s, %s, %s, %s%n",
                        escape(d.getDoctorId()),
                        escape(d.getName()),
                        escape(d.getSpecialization()),
                        escape(d.getHospital()),
                        escape(d.getEmail()));
            }
            writer.println();

            // Patients Section
            writer.println("--- PATIENTS ---");
            writer.println("Patient ID, Name, Email");
            for (Patient p : patients) {
                writer.printf("%s, %s, %s%n",
                        escape(p.getPatientId()),
                        escape(p.getName()),
                        escape(p.getEmail()));
            }
            writer.println();

            // Appointments Section
            writer.println("--- APPOINTMENTS ---");
            writer.println("Date, Time, Patient Name, Doctor Name, Symptoms");
            for (Appointment a : appointments) {
                writer.printf("%s, %s, %s, %s, %s%n",
                        escape(a.getDate()),
                        escape(a.getTimeSlot()),
                        escape(a.getPatientName()),
                        escape(a.getDoctorName()),
                        escape(a.getSymptoms()));
            }
        }
    }

    private static String escape(String data) {
        if (data == null)
            return "";
        return "\"" + data.replace("\"", "\"\"") + "\"";
    }
}
