package dae.cardiacrc.dtos;

public class PrescriptionDTO {
    private int id;
    private String professionalUsername;
    private String professionalName;
    private String patientUsername;
    private String patientName;
    private String description;

    public PrescriptionDTO() {
    }

    public PrescriptionDTO(int id, String professionalUsername, String professionalName, String patientUsername, String patientName, String description) {
        this.id = id;
        this.professionalUsername = professionalUsername;
        this.professionalName = professionalName;
        this.patientUsername = patientUsername;
        this.patientName = patientName;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getProfessionalUsername() {
        return professionalUsername;
    }

    public String getProfessionalName() {
        return professionalName;
    }

    public String getPatientUsername() {
        return patientUsername;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProfessionalUsername(String professionalUsername) {
        this.professionalUsername = professionalUsername;
    }

    public void setProfessionalName(String professionalName) {
        this.professionalName = professionalName;
    }

    public void setPatientUsername(String patientUsername) {
        this.patientUsername = patientUsername;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
