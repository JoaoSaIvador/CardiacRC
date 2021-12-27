package dae.cardiacrc.dtos;

import java.util.ArrayList;
import java.util.List;

public class PatientDTO {
    private String username;
    private String password;
    private String name;
    private String email;
    private int healthNumber;
    private List<ProfessionalDTO> professionalDTOs;
    private List<PrescriptionDTO> activePrescriptionDTOs;
    private List<PrescriptionDTO> inactivePrescriptionDTOs;
    private List<ObservationDTO> observationDTOS;
    private String passwordConfirmation;

    public PatientDTO() {
        this.activePrescriptionDTOs = new ArrayList<PrescriptionDTO>();
        this.inactivePrescriptionDTOs = new ArrayList<PrescriptionDTO>();
        this.observationDTOS = new ArrayList<ObservationDTO>();
    }

    public PatientDTO(String username, String name, String email, int healthNumber) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.healthNumber = healthNumber;
        this.professionalDTOs = new ArrayList<ProfessionalDTO>();
        this.activePrescriptionDTOs = new ArrayList<PrescriptionDTO>();
        this.inactivePrescriptionDTOs = new ArrayList<PrescriptionDTO>();
        this.observationDTOS = new ArrayList<ObservationDTO>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getHealthNumber() {
        return healthNumber;
    }

    public List<ProfessionalDTO> getProfessionalDTOs() {
        return professionalDTOs;
    }

    public List<PrescriptionDTO> getActivePrescriptionDTOs() {
        return activePrescriptionDTOs;
    }

    public List<PrescriptionDTO> getInactivePrescriptionDTOs() {
        return inactivePrescriptionDTOs;
    }

    public List<ObservationDTO> getPatientDataDTOS() {
        return observationDTOS;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHealthNumber(int healthNumber) {
        this.healthNumber = healthNumber;
    }

    public void setProfessionalDTOs(List<ProfessionalDTO> professionalDTOs) {
        this.professionalDTOs = professionalDTOs;
    }

    public void setActivePrescriptionDTOs(List<PrescriptionDTO> prescriptionDTOs) {
        this.activePrescriptionDTOs = prescriptionDTOs;
    }

    public void setInactivePrescriptionDTOs(List<PrescriptionDTO> prescriptionDTOs) {
        this.inactivePrescriptionDTOs = prescriptionDTOs;
    }

    public void setPatientDataDTOS(List<ObservationDTO> observationDTOS) {
        this.observationDTOS = observationDTOS;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }
}
