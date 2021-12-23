package dae.cardiacrc.ejbs;

import dae.cardiacrc.entities.Person;
import dae.cardiacrc.entities.Professional;
import dae.cardiacrc.entities.Type;
import dae.cardiacrc.exceptions.MyConstraintViolationException;
import dae.cardiacrc.exceptions.MyEntityExistsException;
import dae.cardiacrc.exceptions.MyEntityNotFoundException;
import dae.cardiacrc.exceptions.MyIllegalArgumentException;

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

    public void create(String username, int licenseNumber, String name, String password, String email, int typeId) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        Professional professional =  em.find(Professional.class, username);
        if(professional != null) {
            throw new MyEntityExistsException("Professional already exists!");
        }

        Type type = em.find(Type.class, typeId);
        if (type == null){
            throw new MyEntityNotFoundException("Type not found!");
        }

        try {
            professional = new Professional(username, licenseNumber, name, password, email, type);
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

    public void update(String authUsername, String professionalUsername, String password, int licenseNumber, String name, String newPassword, String email) throws MyIllegalArgumentException, MyEntityNotFoundException {
        Person person = em.find(Person.class, authUsername);
        if (!person.getPassword().equals(Person.hashPassword(password))){
            throw new MyIllegalArgumentException("Incorrect password");
        }
        Professional professional = em.find(Professional.class, professionalUsername);

        em.lock(professional, LockModeType.OPTIMISTIC);
        if (licenseNumber > 0){
            professional.setLicenseNumber(licenseNumber);
        }
        if (name != null){
            professional.setName(name);
        }
        if (email != null){
            professional.setEmail(email);
        }
        if (newPassword != null){
            professional.setPassword(newPassword);
        }
        em.merge(professional);
    }

    public void delete(String username) throws MyEntityNotFoundException {
        Professional professional = findProfessional(username);
        em.remove(professional);
    }
}
