package dae.cardiacrc.entities;

import io.smallrye.common.constraint.NotNull;

import javax.persistence.*;
import java.util.Date;

@Table(name = "patientdata")
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
    private DataType dataType;

    @NotNull
    private Date date;

    @Version
    private int version;

    public Observation() {
    }

    public Observation(Patient patient, int value, DataType dataType, Date date) {
        this.patient = patient;
        this.value = value;
        this.dataType = dataType;
        this.date = date;
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

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
