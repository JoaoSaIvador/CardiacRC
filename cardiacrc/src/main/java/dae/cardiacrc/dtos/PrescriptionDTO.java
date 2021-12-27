package dae.cardiacrc.dtos;

public class PrescriptionDTO {
    private int id;
    private String professionalUsername;
    private String professionalName;
    private String description;
    private String name;

    public PrescriptionDTO() {
    }

    public PrescriptionDTO(int id, String professionalUsername, String professionalName, String description, String name) {
        this.id = id;
        this.professionalUsername = professionalUsername;
        this.professionalName = professionalName;
        this.description = description;
        this.name = name;
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

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }
}
