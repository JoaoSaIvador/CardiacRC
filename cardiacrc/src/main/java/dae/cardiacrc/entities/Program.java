package dae.cardiacrc.entities;

import io.smallrye.common.constraint.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@NamedQueries(
        @NamedQuery(
                name = "getAllPrograms",
                query = "SELECT p FROM Program p WHERE p.patient.username = :username OR p.professional.username = :username"
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
    private String duration;

    @NotNull
    private String startDate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "patient_username")
    private Patient patient;

    @ManyToOne
    private Professional professional;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Prescription> prescriptions;

    @Version
    private int version;

    public Program(String duration, Patient patient, Professional professional) {
        SimpleDateFormat formatter= new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
        this.duration = duration;
        this.startDate = formatter.format(new Date(System.currentTimeMillis()));
        this.patient = patient;
        this.professional = professional;
        prescriptions = new ArrayList<Prescription>();
    }

    public Program() {
        prescriptions = new ArrayList<Prescription>();
    }

    public int getId() {
        return id;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        SimpleDateFormat formatter= new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
        this.startDate = formatter.format(startDate);
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Professional getProfessional() {
        return professional;
    }

    public void setProfessional(Professional professional) {
        this.professional = professional;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
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
