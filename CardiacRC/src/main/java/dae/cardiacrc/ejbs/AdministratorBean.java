package dae.cardiacrc.ejbs;

import dae.cardiacrc.entities.Administrator;
import dae.cardiacrc.entities.Person;
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
public class AdministratorBean {
    @PersistenceContext
    private EntityManager em;

    public Person authenticate(final String username, final String password) throws
            Exception {
        Person person = em.find(Person.class, username);
        if (person != null &&
                person.getPassword().equals(Person.hashPassword(password))) {
            return person;
        }
        throw new Exception("Failed logging in with username '" + username + "': unknown username or wrong password");
    }

    public void create(String username, String password, String name, String email) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
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
        Administrator administrator = findAdministrator(username);

        em.lock(administrator, LockModeType.OPTIMISTIC);
        administrator.setPassword(password);
        administrator.setName(name);
        administrator.setEmail(email);
        em.merge(administrator);
    }

    public void deleteAdministrator(String username) throws MyEntityNotFoundException {
        Administrator administrator = findAdministrator(username);
        em.remove(administrator);
    }
}
