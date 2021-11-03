package pt.ipleiria.estg.dei.ei.dae.cardiacrc.entities;

import io.smallrye.common.constraint.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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
    private Date startDate;

    @NotNull
    private Date endDate;

    @NotNull
    private String description;

    public Prescription() {
    }

    public Prescription(int id, Professional professional, Patient patient, Date startDate, Date endDate, String description) {
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

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
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

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
