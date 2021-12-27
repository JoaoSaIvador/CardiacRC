package dae.cardiacrc.entities;

import io.smallrye.common.constraint.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NamedQueries(
        @NamedQuery(
                name = "getAllPrograms",
                query = "SELECT p FROM Program p"
        )
)
@Table(name = "programs")
@Entity
@SequenceGenerator(name = "program_id", sequenceName = "program_id",  initialValue = 1)
public class Program implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "program_id")
    private int id;

    @NotNull
    private int duration;

    @NotNull
    private String startDate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "patient_username")
    private Patient patient;

    @OneToMany
    private List<Prescription> prescriptions;

    @Version
    private int version;
    SimpleDateFormat formatter= new SimpleDateFormat("dd-MM-yyyy");

    public Program(int duration, Patient patient) {
        this.duration = duration;
        this.startDate = formatter.format(new Date(System.currentTimeMillis()));
        this.patient = patient;
        prescriptions = new ArrayList<Prescription>();
    }

    public Program() {
        prescriptions = new ArrayList<Prescription>();
    }

    public int getId() {
        return id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = formatter.format(startDate);
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void addPrescription(Prescription prescription){
        if (prescription != null) {
            this.prescriptions.add(prescription);
        }
    }

    public void removePrescription(Prescription prescription){
        if (prescriptions.contains(prescription)) {
            this.prescriptions.remove(prescription);
        }
    }
}
