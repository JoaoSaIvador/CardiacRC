package pt.ipleiria.estg.dei.ei.dae.cardiacrc.ejbs;

import pt.ipleiria.estg.dei.ei.dae.cardiacrc.entities.Patient;
import pt.ipleiria.estg.dei.ei.dae.cardiacrc.entities.Prescription;
import pt.ipleiria.estg.dei.ei.dae.cardiacrc.entities.Professional;
import pt.ipleiria.estg.dei.ei.dae.cardiacrc.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.cardiacrc.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.cardiacrc.exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless
public class PatientBean {
    @PersistenceContext
    private EntityManager em;

    public void create(String username, int healthNumber, String name, String password, String email, String professionalUsername) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        Professional professional = em.find(Professional.class, professionalUsername);
        Patient patient =  em.find(Patient.class, username);

        if(patient != null) {
            throw new MyEntityExistsException("Patient already exists!");
        }

        if(professional == null) {
            throw new MyEntityNotFoundException("Professional not found!");
        }

        try {
            patient = new Patient(username, healthNumber, name, password, email, professional);
            em.persist(patient);
            professional.addPatient(patient);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public List<Patient> getAllPatients() {
        return (List<Patient>) em.createNamedQuery("getAllPatients").getResultList();
    }

    public Patient findPatient(String username) throws MyEntityNotFoundException {
        Patient patient = em.find(Patient.class, username);
        if(patient == null) {
            throw new MyEntityNotFoundException("Patient not found!");
        }
        return patient;
    }

    public void updatePatient(String username, int healthNumber, String name, String password, String email, String professionalUsername) throws MyEntityNotFoundException {
        Patient patient =  em.find(Patient.class, username);
        if(patient == null) {
            throw new MyEntityNotFoundException("Patient not found!");
        }

        Professional professional = em.find(Professional.class, username);
        if(professional == null) {
            throw new MyEntityNotFoundException("Professional not found!");
        }

        if(!professional.equals(patient.getProfessional())) {
            patient.getProfessional().removePatient(patient);
            professional.addPatient(patient);
        }

        em.lock(patient, LockModeType.OPTIMISTIC);
        patient.setProfessional(professional);
        patient.setName(name);
        patient.setEmail(email);
        patient.setPassword(password);
        patient.setHealthNumber(healthNumber);
        em.merge(patient);
    }

    public void deletePatient (String username) throws MyEntityNotFoundException {
        Patient patient =  em.find(Patient.class, username);
        if(patient == null) {
            throw new MyEntityNotFoundException("Patient not found!");
        }

        for (Prescription prescription : patient.getPrescriptions()) {
            em.remove(prescription);
        }

        patient.getProfessional().removePatient(patient);

        em.remove(patient);
    }
}
