package com.hams.model;

public class Doctor extends User {

    private String doctorId;
    private String name;
    private String specialization;
    private String hospital;

    public Doctor(String doctorId, String name,
                  String specialization, String hospital,
                  String email, String password) {

        super(email, password, "DOCTOR");
        this.doctorId = doctorId;
        this.name = name;
        this.specialization = specialization;
        this.hospital = hospital;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getHospital() {
        return hospital;
    }
}
