package dae.cardiacrc.ejbs;

import dae.cardiacrc.entities.Professional;
import dae.cardiacrc.entities.Type;
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

    public void updateProfessional(String username, String newUsername, int newLicenseNumber, String newName, String newPassword, String newEmail) throws Exception {
        Professional professional = findProfessional(username);
//        if (!professional.getPassword().equals(Professional.hashPassword(password))){
//            throw new Exception("Incorrect password");
//        }

        if (newUsername.equals((findProfessional(newUsername)).getUsername())){
            throw new MyEntityExistsException("There\'s already a professional with that username");
        }

        em.lock(professional, LockModeType.OPTIMISTIC);
        professional.setUsername(newUsername);
        professional.setLicenseNumber(newLicenseNumber);
        professional.setName(newName);
        professional.setEmail(newEmail);
        professional.setPassword(newPassword);
        em.merge(professional);
    }

    public void deleteProfessional(String username) throws MyEntityNotFoundException {
        Professional professional = findProfessional(username);
        em.remove(professional);
    }
}
