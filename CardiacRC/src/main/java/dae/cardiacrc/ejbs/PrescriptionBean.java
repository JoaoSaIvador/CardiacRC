package dae.cardiacrc.ejbs;

import dae.cardiacrc.entities.Patient;
import dae.cardiacrc.entities.Prescription;
import dae.cardiacrc.entities.Professional;
import dae.cardiacrc.exceptions.MyConstraintViolationException;
import dae.cardiacrc.exceptions.MyEntityExistsException;
import dae.cardiacrc.exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless
public class PrescriptionBean {
    @PersistenceContext
    private EntityManager em;

    public void create(String professionalUsername, String patientUsername, String description, String name, int duration) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        Professional professional =  em.find(Professional.class, professionalUsername);
        if(professional == null) {
            throw new MyEntityNotFoundException("Professional not found!");
        }

        Patient patient =  em.find(Patient.class, patientUsername);
        if(patient == null) {
            throw new MyEntityNotFoundException("Patient not found!");
        }

        try {
            Prescription prescription = new Prescription(professional, patient, description, name, duration, professional.getType());
            em.persist(prescription);
            patient.addPrescription(prescription);
            professional.addPrescription(prescription);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public List<Prescription> getAllPrescriptions(){
        return (List<Prescription>) em.createNamedQuery("getAllPrescriptions").getResultList();
    }

    public Prescription findPrescription(int id) throws MyEntityNotFoundException {
        Prescription prescription = em.find(Prescription.class, id);
        if (prescription == null){
            throw new MyEntityNotFoundException("Prescription not found!");
        }
        return prescription;
    }

    public void updatePrescription(int id, String professionalUsername, String patientUsername, String description, String name, int duration) throws MyEntityNotFoundException {
        Prescription prescription = findPrescription(id);
        Professional professional = em.find(Professional.class, professionalUsername);
        if(professional == null) {
            throw new MyEntityNotFoundException("Professional not found!");
        }
        Patient patient = em.find(Patient.class, patientUsername);
        if(patient == null) {
            throw new MyEntityNotFoundException("Patient not found!");
        }

        if (!prescription.getProfessional().equals(professional)){
            prescription.getProfessional().removePrescription(prescription);
            professional.addPrescription(prescription);
        }
        if (!prescription.getPatient().equals(patient)){
            prescription.getPatient().removePrescription(prescription);
            patient.addPrescription(prescription);
        }

        em.lock(prescription, LockModeType.OPTIMISTIC);
        prescription.setProfessional(professional);
        prescription.setPatient(patient);
        prescription.setName(name);
        prescription.setDescription(description);
        prescription.setDuration(duration);

        em.merge(prescription);
    }

    public void deletePresciption(int id) throws MyEntityNotFoundException {
        Prescription prescription = findPrescription(id);
        Professional professional = em.find(Professional.class, prescription.getProfessional());
        if(professional == null) {
            throw new MyEntityNotFoundException("Professional not found!");
        }
        Patient patient = em.find(Patient.class, prescription.getPatient());
        if(patient == null) {
            throw new MyEntityNotFoundException("Patient not found!");
        }

        patient.removePrescription(prescription);
        professional.removePrescription(prescription);
        em.remove(prescription);
    }

    public void toggleState(int id) throws MyEntityNotFoundException {
        Prescription prescription = findPrescription(id);

        em.lock(prescription, LockModeType.OPTIMISTIC);
        prescription.setState(!prescription.isState());
        em.merge(prescription);
    }
}
