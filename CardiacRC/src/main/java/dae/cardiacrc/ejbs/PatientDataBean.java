package dae.cardiacrc.ejbs;

import dae.cardiacrc.entities.*;
import dae.cardiacrc.exceptions.MyConstraintViolationException;
import dae.cardiacrc.exceptions.MyEntityExistsException;
import dae.cardiacrc.exceptions.MyEntityNotFoundException;
import dae.cardiacrc.exceptions.MyIllegalArgumentException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PatientDataBean {
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

        Date date = new Date();
        //SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        //String dateString = formatter.format(date);

        try {
            PatientData patientData = new PatientData(patient, value, dataType, date);
            em.persist(patientData);
            patient.addPatientData(patientData);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public PatientData findPatientData(int id) throws MyEntityNotFoundException {
        PatientData patientData = em.find(PatientData.class, id);
        if(patientData == null) {
            throw new MyEntityNotFoundException("Patient data not found!");
        }
        return patientData;
    }

    public void deletePatientData (int id) throws MyEntityNotFoundException {
        PatientData patientData = em.find(PatientData.class, id);
        patientData.getPatient().removePatientData(patientData);
        em.remove(patientData);
    }
}
