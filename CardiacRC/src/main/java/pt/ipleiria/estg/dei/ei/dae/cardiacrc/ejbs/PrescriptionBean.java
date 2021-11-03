package pt.ipleiria.estg.dei.ei.dae.cardiacrc.ejbs;

import pt.ipleiria.estg.dei.ei.dae.cardiacrc.entities.Administrator;
import pt.ipleiria.estg.dei.ei.dae.cardiacrc.entities.Patient;
import pt.ipleiria.estg.dei.ei.dae.cardiacrc.entities.Prescription;
import pt.ipleiria.estg.dei.ei.dae.cardiacrc.entities.Professional;
import pt.ipleiria.estg.dei.ei.dae.cardiacrc.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.cardiacrc.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.cardiacrc.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.cardiacrc.exceptions.MyIllegalArgumentException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PrescriptionBean {
    @PersistenceContext
    private EntityManager em;

    public void create(int id, String professionalUsername, String patientUsername, String startDateString, String endDateString, String description) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException, MyIllegalArgumentException {
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
            LocalDate startDate = LocalDate.parse(startDateString, DateTimeFormatter.ISO_DATE);
            LocalDate endDate = LocalDate.parse(endDateString, DateTimeFormatter.ISO_DATE);

            prescription = new Prescription(id, professional, patient, startDate, endDate, description);
            em.persist(prescription);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }
}
