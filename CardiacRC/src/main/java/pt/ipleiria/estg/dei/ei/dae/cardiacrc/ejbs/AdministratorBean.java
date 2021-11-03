package pt.ipleiria.estg.dei.ei.dae.cardiacrc.ejbs;

import pt.ipleiria.estg.dei.ei.dae.cardiacrc.entities.Administrator;
import pt.ipleiria.estg.dei.ei.dae.cardiacrc.entities.Professional;
import pt.ipleiria.estg.dei.ei.dae.cardiacrc.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.cardiacrc.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.cardiacrc.exceptions.MyEntityNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

public class AdministratorBean {
    @PersistenceContext
    private EntityManager em;

    public void create(String username, String name, String password, String email) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        Administrator administrator =  em.find(Administrator.class, username);

        if(administrator != null) {
            throw new MyEntityExistsException("Administrator already exists!");
        }

        try {
            administrator = new Administrator(username, password, name, email);
            em.persist(administrator);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public List<Administrator> getAllAdministrators() {
        return (List<Administrator>) em.createNamedQuery("getAllAdministrators").getResultList();
    }

    public Administrator findAdministrator(String username) throws MyEntityNotFoundException {
        Administrator administrator =  em.find(Administrator.class, username);
        if(administrator == null) {
            throw new MyEntityNotFoundException("Administrator not found!");
        }
        return administrator;
    }

    public void updateAdministrator(String username, String password, String name, String email) throws MyEntityNotFoundException {
        Administrator administrator = em.find(Administrator.class, username);
        if(administrator == null) {
            throw new MyEntityNotFoundException("Administrator not found!");
        }

        em.lock(administrator, LockModeType.OPTIMISTIC);
        administrator.setPassword(password);
        administrator.setName(name);
        administrator.setEmail(email);
        em.merge(administrator);
    }

    public void deleteAdministrator(String username) throws MyEntityNotFoundException {
        Administrator administrator = em.find(Administrator.class, username);

        if(administrator == null) {
            throw new MyEntityNotFoundException("Administrator not found!");
        }

        em.remove(administrator);
    }
}
