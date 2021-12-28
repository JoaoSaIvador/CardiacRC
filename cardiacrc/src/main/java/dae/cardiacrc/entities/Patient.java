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
    private List<Observation> observations;

    @OneToMany
    private List<Program> programs;

    public Patient(String username, int healthNumber, String name, String password, String email) {
        super(username, name, password, email);
        this.healthNumber = healthNumber;
        professionals = new ArrayList<Professional>();
        observations = new ArrayList<Observation>();
        programs = new ArrayList<Program>();
    }

    public Patient() {
        professionals = new ArrayList<Professional>();
        observations = new ArrayList<Observation>();
        programs = new ArrayList<Program>();
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

    public List<Observation> getPatientObservations() {
        return observations;
    }

    public void addPatientData(Observation observation) {
        this.observations.add(observation);
    }

    public void removePatientData(Observation observation) {
        if (this.observations.contains(observation)) {
            this.observations.remove(observation);
        }
    }

    public List<Program> getPrograms() {
        return programs;
    }

    public void addProgram(Program program) {
        if (program != null) {
            this.programs.add(program);
        }
    }

    public void removeProgram(Program program) {
        if (professionals.contains(program)) {
            this.programs.remove(program);
        }
    }
}
