package dae.cardiacrc.entities;

import io.smallrye.common.constraint.NotNull;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Table(name = "observations")
@NamedQueries(
        @NamedQuery(
                name = "getAllObservations",
                query = "SELECT o FROM Observation o WHERE o.patient.username = :username"
        )
)
@SequenceGenerator(name = "userdata_id", sequenceName = "userdata_id",  initialValue = 1)
@Entity
public class Observation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "datatype_id")
    private int id;

    private String observer;

    @ManyToOne
    @JoinColumn(name = "patient_username")
    @NotNull
    private Patient patient;

    @NotNull
    private double value;

    @ManyToOne
    @JoinColumn(name = "data_type")
    @NotNull
    private QuantitativeDataType quantitativeDataType;

    private String qualitativeDataType;

    @NotNull
    private String date;

    @Version
    private int version;

    public Observation() {
    }

    public Observation(String observer, Patient patient, double value, QuantitativeDataType quantitativeDataType) {
        SimpleDateFormat formatter= new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        this.observer = observer;
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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
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
        SimpleDateFormat formatter= new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
        this.date = formatter.format(date);
    }

    public String getObserver() {
        return observer;
    }

    public void setObserver(String observer) {
        this.observer = observer;
    }
}
