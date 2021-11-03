package pt.ipleiria.estg.dei.ei.dae.cardiacrc.dtos;

import java.util.ArrayList;
import java.util.List;

public class PatientDTO {
    private String username;
    private String password;
    private String name;
    private String email;
    private int healthNumber;
    private String professionalUsername;
    private String professionalName;
    private List<PrescriptionDTO> prescriptionDTOs;

    public PatientDTO() {
        this.prescriptionDTOs = new ArrayList<PrescriptionDTO>();
    }

    public PatientDTO(String username, String password, String name, String email, int healthNumber, String professionalUsername, String professionalName) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.healthNumber = healthNumber;
        this.professionalUsername = professionalUsername;
        this.professionalName = professionalName;
        this.prescriptionDTOs = new ArrayList<PrescriptionDTO>();
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

    public String getProfessionalUsername() {
        return professionalUsername;
    }

    public String getProfessionalName() {
        return professionalName;
    }

    public List<PrescriptionDTO> getPrescriptionDTOs() {
        return prescriptionDTOs;
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

    public void setProfessionalUsername(String professionalUsername) {
        this.professionalUsername = professionalUsername;
    }

    public void setProfessionalName(String professionalName) {
        this.professionalName = professionalName;
    }

    public void setPrescriptionDTOs(List<PrescriptionDTO> prescriptionDTOs) {
        this.prescriptionDTOs = prescriptionDTOs;
    }
}
