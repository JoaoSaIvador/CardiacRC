package dae.cardiacrc.entities;

import io.smallrye.common.constraint.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllProfessionals",
                query = "SELECT p FROM Professional p ORDER BY p.name"
        )
})
public class Professional extends Person implements Serializable {
    @NotNull
    private int licenseNumber;

    @OneToMany(mappedBy = "professional", cascade = CascadeType.REMOVE)
    private List<Patient> patients;

    @OneToMany(mappedBy = "professional", cascade = CascadeType.REMOVE)
    private List<Prescription> prescriptions;

    public Professional(String username, int licenseNumber, String name, String password, String email) {
        super(username, name, password, email);
        this.licenseNumber = licenseNumber;
        patients = new ArrayList<Patient>();
        prescriptions = new ArrayList<Prescription>();
    }

    public Professional() {
        patients = new ArrayList<Patient>();
        prescriptions = new ArrayList<Prescription>();
    }

    public int getLicenseNumber() {
        return licenseNumber;
    }

    public List<Patient> getPatients() {
        return patients;
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

    public void setLicenseNumber(int licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public void addPatient(Patient patient) {
        this.patients.add(patient);
    }

    public void removePatient(Patient patient) {
        if (this.patients.contains(patient)) {
            this.patients.remove(patient);
        }
    }

    public void addPrescription(Prescription prescription) {
        this.prescriptions.add(prescription);
    }

    public void removePrescription(Prescription prescription) {
        if (this.prescriptions.contains(prescription)) {
            this.prescriptions.remove(prescription);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Professional professional = (Professional) o;
        return getUsername() == professional.getUsername() && Objects.equals(getName(), professional.getName()) && Objects.equals(licenseNumber, professional.licenseNumber) && Objects.equals(patients, professional.patients) && Objects.equals(prescriptions, professional.prescriptions);
    }

}
