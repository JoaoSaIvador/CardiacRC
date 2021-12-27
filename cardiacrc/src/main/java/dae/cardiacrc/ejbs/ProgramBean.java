package dae.cardiacrc.ejbs;

import dae.cardiacrc.entities.Patient;
import dae.cardiacrc.entities.Program;
import dae.cardiacrc.exceptions.MyConstraintViolationException;
import dae.cardiacrc.exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless
public class ProgramBean {
    @PersistenceContext
    private EntityManager em;

    public void create(int duration, String patientUserName) throws MyEntityNotFoundException, MyConstraintViolationException {
        Patient patient = em.find(Patient.class, patientUserName);
        if (patient == null){
            throw new MyEntityNotFoundException("Patient not found!");
        }

        try {
            Program program = new Program(duration, patient);
            patient.addProgram(program);
            em.persist(program);
        }catch (ConstraintViolationException e){
            throw new MyConstraintViolationException(e);
        }
    }

    public List<Program> getAllPrograms(){
        return (List<Program>) em.createNamedQuery("getAllPrograms").getResultList();
    }

    public Program findProgram(int id) throws MyEntityNotFoundException {
        Program program = em.find(Program.class, id);
        if (program == null){
            throw new MyEntityNotFoundException("Program not found!");
        }
        return program;
    }

    public void delete(int id) throws MyEntityNotFoundException {
        Program program = findProgram(id);
        Patient patient = program.getPatient();

        patient.removeProgram(program);
        em.remove(program);
    }
}
