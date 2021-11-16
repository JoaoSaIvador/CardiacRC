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
public class Patient extends Person implements Serializable {
    @NotNull
    private int healthNumber;

    @ManyToOne
    @JoinColumn(name = "professional_username")
    @NotNull
    private Professional professional;

    @OneToMany
    private List<Prescription> prescriptions;

    public Patient(String username, int healthNumber, String name, String password, String email, Professional professional) {
        super(username, name, password, email);
        this.healthNumber = healthNumber;
        this.professional = professional;
        prescriptions = new ArrayList<Prescription>();
    }

    public Patient() {
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

    public List<Prescription> getActivePrescriptions() {
        List<Prescription> activePrescriptions = this.prescriptions;
        activePrescriptions.removeIf(prescription -> prescription.isState() == true);
        return activePrescriptions;
    }

    public List<Prescription> getInactivePrescriptions() {
        List<Prescription> activePrescriptions = this.prescriptions;
        activePrescriptions.removeIf(prescription -> prescription.isState() == false);
        return activePrescriptions;
    }

    public void addPrescription(Prescription prescription) {
        this.prescriptions.add(prescription);
    }

    public void removePrescription(Prescription prescription) {
        if (this.prescriptions.contains(prescription)) {
            this.prescriptions.remove(prescription);
        }
    }
}
