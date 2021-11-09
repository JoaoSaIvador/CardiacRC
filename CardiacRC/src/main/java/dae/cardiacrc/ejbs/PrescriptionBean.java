package dae.cardiacrc.ejbs;

import dae.cardiacrc.entities.Patient;
import dae.cardiacrc.entities.Prescription;
import dae.cardiacrc.entities.Professional;
import dae.cardiacrc.exceptions.MyConstraintViolationException;
import dae.cardiacrc.exceptions.MyEntityExistsException;
import dae.cardiacrc.exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless
public class PrescriptionBean {
    @PersistenceContext
    private EntityManager em;

    public void create(String professionalUsername, String patientUsername, String startDateString, String endDateString, String description, String name, int duration) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        Professional professional =  em.find(Professional.class, professionalUsername);
        if(professional == null) {
            throw new MyEntityNotFoundException("Professional not found!");
        }

        Patient patient =  em.find(Patient.class, patientUsername);
        if(patient == null) {
            throw new MyEntityNotFoundException("Patient not found!");
        }

        try {
            Prescription prescription = new Prescription(professional, patient, description, name, duration);
            em.persist(prescription);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public List<Prescription> getAllPrescriptions(){
        return (List<Prescription>) em.createNamedQuery("getAllPrescriptions").getResultList();
    }
}
