package dae.cardiacrc.entities;

import io.smallrye.common.constraint.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllPatients",
                query = "SELECT p FROM Patient p ORDER BY p.name"
        ),
        @NamedQuery(
                name = "countPatients",
                query = "SELECT count(p) From Patient p"
        )
})
@Table(name = "patients")
public class Patient extends Person implements Serializable {
    @NotNull
    private int healthNumber;

    @ManyToMany
    @JoinTable(name = "PATIENTS_PROFESSIONALS",
            joinColumns = @JoinColumn(name = "patient_username", referencedColumnName = "USERNAME"),
            inverseJoinColumns = @JoinColumn(name = "professional_username", referencedColumnName = "USERNAME"))
    private List<Professional> professionals;

    @OneToMany
    private  List<Observation> data;

    public Patient(String username, int healthNumber, String name, String password, String email) {
        super(username, name, password, email);
        this.healthNumber = healthNumber;
        professionals = new ArrayList<Professional>();
        data = new ArrayList<Observation>();
    }

    public Patient() {

    }

    public int getHealthNumber() {
        return healthNumber;
    }

    public void setHealthNumber(int healthNumber) {
        this.healthNumber = healthNumber;
    }

    public List<Professional> getProfessionals() {
        return professionals;
    }

    public void addProfessional(Professional professional) {
        if (professional != null) {
            this.professionals.add(professional);
        }
    }

    public void removeProfessional(Professional professional) {
        if (professionals.contains(professional)) {
            this.professionals.remove(professional);
        }
    }

    public List<Observation> getPatientData() {
        return data;
    }

    public void addPatientData(Observation observation) {
        this.data.add(observation);
    }

    public void removePatientData(Observation observation) {
        if (this.data.contains(observation)) {
            this.data.remove(observation);
        }
    }
}
