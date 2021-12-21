package dae.cardiacrc.ejbs;

import dae.cardiacrc.entities.Patient;
import dae.cardiacrc.entities.Prescription;
import dae.cardiacrc.entities.Professional;
import dae.cardiacrc.exceptions.MyConstraintViolationException;
import dae.cardiacrc.exceptions.MyEntityExistsException;
import dae.cardiacrc.exceptions.MyEntityNotFoundException;
import dae.cardiacrc.exceptions.MyIllegalArgumentException;

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

    public void create(String username, int healthNumber, String name, String password, String email) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        Patient patient =  em.find(Patient.class, username);

        if(patient != null) {
            throw new MyEntityExistsException("Patient already exists!");
        }

        try {
            patient = new Patient(username, healthNumber, name, password, email);
            em.persist(patient);
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

    public void updatePatient(String username, String newUsername, int healthNumber, String name, String password, String email) throws MyEntityNotFoundException, MyEntityExistsException {
        Patient patient =  findPatient(username);
        //        if (!professional.getPassword().equals(Professional.hashPassword(password))){
        //            throw new Exception("Incorrect password");
        //        }

        if (newUsername.equals((findPatient(newUsername)).getUsername())){
            throw new MyEntityExistsException("There\'s already a patient with that username");
        }


        em.lock(patient, LockModeType.OPTIMISTIC);
        patient.setName(name);
        patient.setEmail(email);
        patient.setPassword(password);
        patient.setHealthNumber(healthNumber);
        em.merge(patient);
    }

    public void addProfessional(String username, String professionalUsername) throws MyEntityNotFoundException {
        Patient patient = findPatient(username);

        Professional professional = em.find(Professional.class, professionalUsername);
        if(professional == null) {
            throw new MyEntityNotFoundException("Professional not found!");
        }

        patient.addProfessional(professional);
        professional.addPatient(patient);
    }

    public void removeProfessional(String username, String professionalUsername) throws MyEntityNotFoundException, MyIllegalArgumentException {
        Patient patient = findPatient(username);

        Professional professional = em.find(Professional.class, professionalUsername);
        if(professional == null) {
            throw new MyEntityNotFoundException("Professional not found!");
        }

        if(!patient.getProfessionals().contains(professional) || !professional.getPatients().contains(patient)) {
            throw new MyIllegalArgumentException("Professional and Patient not associated");
        }

        patient.removeProfessional(professional);
        professional.removePatient(patient);
    }

    public void deletePatient (String username) throws MyEntityNotFoundException {
        Patient patient =  findPatient(username);

        for (Prescription prescription : patient.getPrescriptions()) {
            em.remove(prescription);
        }

        for (Professional professional : patient.getProfessionals()) {
            professional.removePatient(patient);
        }

        em.remove(patient);
    }
}
