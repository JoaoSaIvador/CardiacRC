package dae.cardiacrc.ejbs;

import dae.cardiacrc.entities.*;
import dae.cardiacrc.exceptions.MyConstraintViolationException;
import dae.cardiacrc.exceptions.MyEntityExistsException;
import dae.cardiacrc.exceptions.MyEntityNotFoundException;
import dae.cardiacrc.exceptions.MyIllegalArgumentException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.Date;

public class ObservationBean {
    @PersistenceContext
    private EntityManager em;

    public void create(String patientUsername, int value, String name, int dataTypeId) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException, MyIllegalArgumentException {
        Patient patient =  em.find(Patient.class, patientUsername);

        if(patient == null) {
            throw new MyEntityExistsException("Patient does not exist!");
        }

        DataType dataType = em.find(DataType.class, dataTypeId);

        if(dataType == null) {
            throw new MyEntityExistsException("Data type does not exist!");
        }

        try {
            Date date = new Date();
            Observation observation = new Observation(patient, value, dataType, date);
            em.persist(observation);
            patient.addPatientData(observation);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public Observation findPatientData(int id) throws MyEntityNotFoundException {
        Observation observation = em.find(Observation.class, id);
        if(observation == null) {
            throw new MyEntityNotFoundException("Patient data not found!");
        }
        return observation;
    }

    public void deletePatientData (int id) throws MyEntityNotFoundException {
        Observation observation = em.find(Observation.class, id);
        observation.getPatient().removePatientData(observation);
        em.remove(observation);
    }
}
