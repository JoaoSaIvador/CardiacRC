package pt.ipleiria.estg.dei.ei.dae.cardiacrc.entities;

import io.smallrye.common.constraint.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Table(name = "prescriptions")
@Entity
public class Prescription implements Serializable {
    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "professional_username")
    private Professional professional;

    @ManyToOne
    @JoinColumn(name = "patient_username")
    private Patient patient;
    //TODO attributes missing

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private String description;

    public Prescription() {
    }

    public Prescription(int id, Professional professional, Patient patient, LocalDate startDate, LocalDate endDate, String description) {
        this.id = id;
        this.professional = professional;
        this.patient = patient;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getDescription() {
        return description;
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

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
