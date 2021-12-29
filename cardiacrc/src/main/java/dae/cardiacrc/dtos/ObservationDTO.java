package dae.cardiacrc.dtos;

public class ObservationDTO {
    private int id;
    private String patientUsername;
    private String patientName;
    private float value;
    private int dataTypeId;
    private String dataTypeName;
    private String date;
    private String qualitativeName;

    public ObservationDTO() {
    }

    public ObservationDTO(int id, String patientUsername, String patientName, float value, int dataTypeId, String dataTypeName, String qualitativeName, String date) {
        this.id = id;
        this.patientUsername = patientUsername;
        this.patientName = patientName;
        this.value = value;
        this.dataTypeId = dataTypeId;
        this.dataTypeName = dataTypeName;
        this.date = date;
        this.qualitativeName = qualitativeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPatientUsername() {
        return patientUsername;
    }

    public void setPatientUsername(String patientUsername) {
        this.patientUsername = patientUsername;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public int getDataTypeId() {
        return dataTypeId;
    }

    public void setDataTypeId(int dataTypeId) {
        this.dataTypeId = dataTypeId;
    }

    public String getDataTypeName() {
        return dataTypeName;
    }

    public void setDataTypeName(String dataTypeName) {
        this.dataTypeName = dataTypeName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQualitativeName() {
        return qualitativeName;
    }

    public void setQualitativeName(String qualitativeName) {
        this.qualitativeName = qualitativeName;
    }
}
