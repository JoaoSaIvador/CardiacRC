package dae.cardiacrc.entities;

import io.smallrye.common.constraint.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "prescriptions")
@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllPrescriptions",
                query = "SELECT p FROM Prescription p ORDER BY p.name"
        )
})
@SequenceGenerator(name = "prescription_id", sequenceName = "prescription_id",  initialValue = 1)
public class Prescription implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prescription_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "professional_username")
    @NotNull
    private Professional professional;

    @ManyToOne
    @JoinColumn(name = "patient_username")
    @NotNull
    private Patient patient;

    @NotNull
    private String description;

    @NotNull
    private String name;

    @NotNull
    private int duration;

    @NotNull
    private boolean state;

    @Version
    private int version;

    public Prescription() {
    }

    public Prescription(Professional professional, Patient patient, String description, String name, int duration) {
        this.professional = professional;
        this.patient = patient;
        this.description = description;
        this.name = name;
        this.duration = duration;
        this.state = true;
    }

    public int getId() {
        return id;
    }

    public Professional getProfessional() {
        return professional;
    }

    public Patient getPatient() {
        return patient;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public boolean isState() {
        return state;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProfessional(Professional professional) {
        this.professional = professional;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
