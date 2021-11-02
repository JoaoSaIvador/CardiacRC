package pt.ipleiria.estg.dei.ei.dae.cardiacrc.entities;

import io.smallrye.common.constraint.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//@Table(name = "professionals")
@Entity
public class Professional extends User implements Serializable {
    @Id
    private int licenceNumber;
    @OneToMany
    private List<Pacient> pacients;
    @OneToMany
    private List<Prescription> prescriptions;

    public Professional(int licenceNumber, String name, String password, String email) {
        super(name, password, email);
        this.licenceNumber = licenceNumber;
        pacients = new ArrayList<Pacient>();
        prescriptions = new ArrayList<Prescription>();
    }

    public Professional() {
        pacients = new ArrayList<Pacient>();
        prescriptions = new ArrayList<Prescription>();
    }

    public int getLicenceNumber() {
        return licenceNumber;
    }

    public void setLicenceNumber(int licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public List<Pacient> getPacients() {
        return pacients;
    }

    public void setPacients(List<Pacient> pacients) {
        this.pacients = pacients;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }
}
