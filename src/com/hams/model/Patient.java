package com.hams.model;

public class Patient extends User {

    private String patientId;
    private String name;

    public Patient(String patientId, String name,
            String email, String password) {
        super(email, password, "PATIENT");
        this.patientId = patientId;
        this.name = name;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }
}
