package com.hams.model;

import java.io.Serializable;

public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;

    private String patientName;
    private String doctorName;
    private String timeSlot;
    private String symptoms;

    private String date;

    private int id;
    private String status;

    public Appointment(String patientName, String doctorName,
            String date, String timeSlot, String symptoms) {
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.date = date;
        this.timeSlot = timeSlot;
        this.symptoms = symptoms;
        this.status = "SCHEDULED";
    }

    public Appointment(int id, String patientName, String doctorName,
            String date, String timeSlot, String symptoms, String status) {
        this.id = id;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.date = date;
        this.timeSlot = timeSlot;
        this.symptoms = symptoms;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public String getSymptoms() {
        return symptoms;
    }
}
