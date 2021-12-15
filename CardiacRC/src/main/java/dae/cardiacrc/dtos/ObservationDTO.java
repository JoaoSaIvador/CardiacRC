package dae.cardiacrc.dtos;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ObservationDTO {
    private int id;
    private String patientUsername;
    private String patientName;
    private int value;
    private int dataTypeId;
    private String dataTypeName;
    private String date;

    public ObservationDTO() {
    }

    public ObservationDTO(int id, String patientUsername, String patientName, int value, int dataTypeId, String dataTypeName, Date date) {
        this.id = id;
        this.patientUsername = patientUsername;
        this.patientName = patientName;
        this.value = value;
        this.dataTypeId = dataTypeId;
        this.dataTypeName = dataTypeName;

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        this.date = formatter.format(date);
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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
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
}
