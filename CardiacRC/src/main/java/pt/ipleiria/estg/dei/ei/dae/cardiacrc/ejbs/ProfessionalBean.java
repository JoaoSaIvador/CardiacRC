package pt.ipleiria.estg.dei.ei.dae.cardiacrc.ejbs;

import pt.ipleiria.estg.dei.ei.dae.cardiacrc.entities.Professional;
import pt.ipleiria.estg.dei.ei.dae.cardiacrc.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.cardiacrc.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.cardiacrc.exceptions.MyEntityNotFoundException;

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

    public void create(String username, int licenceNumber, String name, String password, String email) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        Professional professional =  em.find(Professional.class, username);

        if(professional != null) {
            throw new MyEntityExistsException("Professional already exists!");
        }

        try {
            professional = new Professional(username, licenceNumber, name, password, email);
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

    public void updateProfessional(String username, int licenceNumber, String name, String password, String email) throws MyEntityNotFoundException {
        Professional professional = em.find(Professional.class, username);
        if(professional == null) {
            throw new MyEntityNotFoundException("Professional not found!");
        }

        em.lock(professional, LockModeType.OPTIMISTIC);
        professional.setLicenseNumber(licenceNumber);
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
