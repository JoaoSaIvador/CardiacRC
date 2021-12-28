package dae.cardiacrc.ejbs;

import dae.cardiacrc.entities.*;
import dae.cardiacrc.exceptions.MyConstraintViolationException;
import dae.cardiacrc.exceptions.MyEntityExistsException;
import dae.cardiacrc.exceptions.MyEntityNotFoundException;
import dae.cardiacrc.exceptions.MyIllegalArgumentException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.Date;

@Stateless
public class ObservationBean {
    @PersistenceContext
    private EntityManager em;

    public void create(String patientUsername, int value, int dataTypeId) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException, MyIllegalArgumentException {
        Patient patient =  em.find(Patient.class, patientUsername);

        if(patient == null) {
            throw new MyEntityExistsException("Patient does not exist!");
        }

        QuantitativeDataType quantitativeDataType = em.find(QuantitativeDataType.class, dataTypeId);

        if(quantitativeDataType == null) {
            throw new MyEntityExistsException("Data type does not exist!");
        }

        try {
            Observation observation = new Observation(patient, value, quantitativeDataType);
            em.persist(observation);
            patient.addPatientData(observation);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public Observation findObservation(int id) throws MyEntityNotFoundException {
        Observation observation = em.find(Observation.class, id);
        if(observation == null) {
            throw new MyEntityNotFoundException("Observation not found!");
        }
        return observation;
    }

    public void delete(int id) throws MyEntityNotFoundException {
        Observation observation = findObservation(id);
        observation.getPatient().removePatientData(observation);
        em.remove(observation);
    }
}
