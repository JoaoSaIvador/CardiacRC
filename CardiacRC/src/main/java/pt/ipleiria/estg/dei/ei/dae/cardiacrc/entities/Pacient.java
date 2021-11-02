package pt.ipleiria.estg.dei.ei.dae.cardiacrc.entities;

import io.smallrye.common.constraint.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//@Table(name = "pacients")
@Entity
public class Pacient extends User implements Serializable {
    @Id
    private int healthNumber;
    @ManyToOne
    @JoinColumn(name = "professional_username")
    private Professional professional;
    @OneToMany
    private List<Prescription> prescriptions;

    public Pacient(int healthNumber, String name, String password, String email, Professional professional) {
        super(name, password, email);
        this.healthNumber = healthNumber;
        this.professional = professional;
        prescriptions = new ArrayList<Prescription>();
    }

    public Pacient() {
        prescriptions = new ArrayList<Prescription>();
    }

    public int getHealthNumber() {
        return healthNumber;
    }

    public void setHealthNumber(int healthNumber) {
        this.healthNumber = healthNumber;
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

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }
}
