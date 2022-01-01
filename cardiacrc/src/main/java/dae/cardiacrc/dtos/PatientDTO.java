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
    private boolean deleted;

    public PatientDTO() {
        this.activePrescriptionDTOs = new ArrayList<PrescriptionDTO>();
        this.inactivePrescriptionDTOs = new ArrayList<PrescriptionDTO>();
        this.observationDTOS = new ArrayList<ObservationDTO>();
    }

    public PatientDTO(String username, String name, String email, int healthNumber, boolean deleted) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.healthNumber = healthNumber;
        this.deleted = deleted;
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

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public List<ObservationDTO> getObservationDTOS() {
        return observationDTOS;
    }

    public void setObservationDTOS(List<ObservationDTO> observationDTOS) {
        this.observationDTOS = observationDTOS;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
