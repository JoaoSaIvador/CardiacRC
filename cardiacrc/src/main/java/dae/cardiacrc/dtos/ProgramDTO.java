package dae.cardiacrc.dtos;

import dae.cardiacrc.entities.Prescription;
import dae.cardiacrc.entities.Professional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProgramDTO implements Serializable {
    private int id;
    private int duration;
    private String startDate;
    private String patientUsername;
    private List<Prescription> prescriptionsDTOs;

    public ProgramDTO(int id, int duration, String startDate, String patientUsername) {
        this.id = id;
        this.duration = duration;
        this.startDate = startDate;
        this.patientUsername = patientUsername;
        prescriptionsDTOs = new ArrayList<Prescription>();
    }

    public ProgramDTO() {
        prescriptionsDTOs = new ArrayList<Prescription>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getPatientUsername() {
        return patientUsername;
    }

    public void setPatientUsername(String patientUsername) {
        this.patientUsername = patientUsername;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptionsDTOs;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptionsDTOs = prescriptions;
    }
}
