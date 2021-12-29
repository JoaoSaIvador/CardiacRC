package dae.cardiacrc.dtos;

public class PrescriptionDTO {
    private int id;
    private String professionalUsername;
    private String professionalName;
    private String description;
    private String name;
    private int typeId;
    private String typeName;

    public PrescriptionDTO() {
    }

    public PrescriptionDTO(int id, String professionalUsername, String professionalName, String description, String name, int typeId, String typeName) {
        this.id = id;
        this.professionalUsername = professionalUsername;
        this.professionalName = professionalName;
        this.description = description;
        this.name = name;
        this.typeId = typeId;
        this.typeName = typeName;
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

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
