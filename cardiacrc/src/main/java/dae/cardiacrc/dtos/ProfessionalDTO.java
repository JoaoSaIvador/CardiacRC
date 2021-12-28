package dae.cardiacrc.dtos;

import java.util.ArrayList;
import java.util.List;

public class ProfessionalDTO {
    private String username;
    private String password;
    private String name;
    private String email;
    private int licenseNumber;
    private List<PrescriptionDTO> prescriptionsDTOs;
    private List<ProgramDTO> programsDTOs;
    private List<PatientDTO> patientDTOS;
    private int type;
    private String typeName;
    private String passwordConfirmation;

    public ProfessionalDTO() {
        this.prescriptionsDTOs = new ArrayList<PrescriptionDTO>();
        this.programsDTOs = new ArrayList<ProgramDTO>();
        this.patientDTOS = new ArrayList<PatientDTO>();
    }

    public ProfessionalDTO(String username, String name, String email, int licenseNumber, int type, String typeName) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.licenseNumber = licenseNumber;
        this.type = type;
        this.typeName = typeName;
        this.prescriptionsDTOs = new ArrayList<PrescriptionDTO>();
        this.programsDTOs = new ArrayList<ProgramDTO>();
        this.patientDTOS = new ArrayList<PatientDTO>();
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

    public int getLicenseNumber() {
        return licenseNumber;
    }

    public List<PrescriptionDTO> getPrescriptionsDTOs() {
        return prescriptionsDTOs;
    }

    public List<ProgramDTO> getProgramsDTOs() {
        return programsDTOs;
    }

    public List<PatientDTO> getPatientDTOs() {
        return patientDTOS;
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

    public void setLicenseNumber(int licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public void setPrescriptionsDTOs(List<PrescriptionDTO> prescriptionDTOs) {
        this.prescriptionsDTOs = prescriptionDTOs;
    }

    public void setProgramsDTOs(List<ProgramDTO> prescriptionDTOs) {
        this.programsDTOs = prescriptionDTOs;
    }

    public void setPatientDTOs(List<PatientDTO> patientDTOS) {
        this.patientDTOS = patientDTOS;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

}
