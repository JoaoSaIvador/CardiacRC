package dae.cardiacrc.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProgramDTO implements Serializable {
    private int id;
    private String duration;
    private String professionalUsername;
    private String professionalName;
    private String startDate;
    private String patientUsername;
    private String patientName;
    private int prescriptionIds[];
    private List<PrescriptionDTO> prescriptionsDTOs;

    public ProgramDTO(int id, String professionalUsername, String professionalName ,String patientUsername, String patientName, String startDate, String duration) {
        this.id = id;
        this.duration = duration;
        this.professionalUsername = professionalUsername;
        this.professionalName = professionalName;
        this.startDate = startDate;
        this.patientUsername = patientUsername;
        this.patientName = patientName;
        prescriptionsDTOs = new ArrayList<PrescriptionDTO>();
    }

    public ProgramDTO() {
        prescriptionsDTOs = new ArrayList<PrescriptionDTO>();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
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

    public List<PrescriptionDTO> getPrescriptions() {
        return prescriptionsDTOs;
    }

    public void setPrescriptions(List<PrescriptionDTO> prescriptions) {
        this.prescriptionsDTOs = prescriptions;
    }

    public String getProfessionalUsername() {
        return professionalUsername;
    }

    public void setProfessionalUsername(String professionalUsername) {
        this.professionalUsername = professionalUsername;
    }

    public String getProfessionalName() {
        return professionalName;
    }

    public void setProfessionalName(String professionalName) {
        this.professionalName = professionalName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int[] getPrescriptionIds() {
        return prescriptionIds;
    }

    public void setPrescriptionIds(int[] prescriptionIds) {
        this.prescriptionIds = prescriptionIds;
    }
}
