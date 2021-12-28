package dae.cardiacrc.ejbs;

import dae.cardiacrc.entities.Patient;
import dae.cardiacrc.entities.Professional;
import dae.cardiacrc.entities.Program;
import dae.cardiacrc.exceptions.MyConstraintViolationException;
import dae.cardiacrc.exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless
public class ProgramBean {
    @PersistenceContext
    private EntityManager em;

    public void create(String duration, String patientUserName, String professionalUsername) throws MyEntityNotFoundException, MyConstraintViolationException {
        Patient patient = em.find(Patient.class, patientUserName);
        if (patient == null){
            throw new MyEntityNotFoundException("Patient not found!");
        }
        Professional professional = em.find(Professional.class, professionalUsername);
        if (professional == null){
            throw new MyEntityNotFoundException("Professional not found!");
        }

        try {
            Program program = new Program(duration, patient, professional);
            professional.addProgram(program);
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

    public void update(int id, String duration) throws MyEntityNotFoundException {
        Program program = findProgram(id);

        em.lock(program, LockModeType.OPTIMISTIC);
        if (duration != null){
            program.setDuration(duration);
        }
        em.merge(program);
    }

    public void delete(int id) throws MyEntityNotFoundException {
        Program program = findProgram(id);
        Patient patient = program.getPatient();
        Professional professional = program.getProfessional();

        patient.removeProgram(program);
        professional.removeProgram(program);
        em.remove(program);
    }
}
