package dae.cardiacrc.entities;

import io.smallrye.common.constraint.NotNull;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Table(name = "observations")
@SequenceGenerator(name = "userdata_id", sequenceName = "userdata_id",  initialValue = 1)
@Entity
public class Observation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "datatype_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "patient_username")
    @NotNull
    private Patient patient;

    @NotNull
    private int value;

    @ManyToOne
    @JoinColumn(name = "data_type")
    @NotNull
    private QuantitativeDataType quantitativeDataType;

    private String qualitativeDataType;

    @NotNull
    private String date;

    @Version
    private int version;
    SimpleDateFormat formatter= new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

    public Observation() {
    }

    public Observation(Patient patient, int value, QuantitativeDataType quantitativeDataType) {
        this.patient = patient;
        this.value = value;
        this.quantitativeDataType = quantitativeDataType;
        this.date = formatter.format(new Date(System.currentTimeMillis()));
        this.qualitativeDataType = quantitativeDataType.getQualitativeDataTypeName(value);
    }

    public int getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public QuantitativeDataType getDataType() {
        return quantitativeDataType;
    }

    public void setDataType(QuantitativeDataType quantitativeDataType) {
        this.quantitativeDataType = quantitativeDataType;
    }

    public String getQualitativeDataType() {
        return qualitativeDataType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
