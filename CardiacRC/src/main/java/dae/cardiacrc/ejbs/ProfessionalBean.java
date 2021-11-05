package dae.cardiacrc.ejbs;

import dae.cardiacrc.entities.Professional;
import dae.cardiacrc.exceptions.MyConstraintViolationException;
import dae.cardiacrc.exceptions.MyEntityExistsException;
import dae.cardiacrc.exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless
public class ProfessionalBean {
    @PersistenceContext
    private EntityManager em;

    public void create(String username, int licenseNumber, String name, String password, String email) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        Professional professional =  em.find(Professional.class, username);

        if(professional != null) {
            throw new MyEntityExistsException("Professional already exists!");
        }

        try {
            professional = new Professional(username, licenseNumber, name, password, email);
            em.persist(professional);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public List<Professional> getAllProfessionals() {
        return (List<Professional>) em.createNamedQuery("getAllProfessionals").getResultList();
    }

    public Professional findProfessional(String username) throws MyEntityNotFoundException {
        Professional professional = em.find(Professional.class, username);
        if(professional == null) {
            throw new MyEntityNotFoundException("Professional not found!");
        }
        return professional;
    }

    public void updateProfessional(String username, int licenseNumber, String name, String password, String email) throws MyEntityNotFoundException {
        Professional professional = em.find(Professional.class, username);
        if(professional == null) {
            throw new MyEntityNotFoundException("Professional not found!");
        }

        em.lock(professional, LockModeType.OPTIMISTIC);
        professional.setLicenseNumber(licenseNumber);
        professional.setName(name);
        professional.setEmail(email);
        professional.setPassword(password);
        em.merge(professional);
    }

    public void deleteProfessional(String username) throws MyEntityNotFoundException {
        Professional professional = em.find(Professional.class, username);
        if(professional == null) {
            throw new MyEntityNotFoundException("Professional not found!");
        }

        em.remove(professional);

        //TODO: WHEN DELETING PROFESSIONALS SHOULD THEIR PATIENTS HAVE NO PROFESSIONAL?
    }
}
