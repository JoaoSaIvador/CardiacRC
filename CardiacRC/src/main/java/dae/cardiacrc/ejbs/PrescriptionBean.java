package dae.cardiacrc.ejbs;

import dae.cardiacrc.entities.Patient;
import dae.cardiacrc.entities.Prescription;
import dae.cardiacrc.entities.Professional;
import dae.cardiacrc.exceptions.MyConstraintViolationException;
import dae.cardiacrc.exceptions.MyEntityExistsException;
import dae.cardiacrc.exceptions.MyEntityNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

public class PrescriptionBean {
    @PersistenceContext
    private EntityManager em;

    public void create(int id, String professionalUsername, String patientUsername, String startDateString, String endDateString, String description, String name, int duration) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        Prescription prescription =  em.find(Prescription.class, id);
        if(prescription != null) {
            throw new MyEntityExistsException("Prescription already exists!");
        }

        Professional professional =  em.find(Professional.class, professionalUsername);
        if(professional == null) {
            throw new MyEntityNotFoundException("Professional not found!");
        }

        Patient patient =  em.find(Patient.class, patientUsername);
        if(patient == null) {
            throw new MyEntityNotFoundException("Patient not found!");
        }

        try {
            prescription = new Prescription(professional, patient, description, name, duration);
            em.persist(prescription);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }
}
