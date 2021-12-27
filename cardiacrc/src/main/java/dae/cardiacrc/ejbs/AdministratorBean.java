package dae.cardiacrc.ejbs;

import dae.cardiacrc.entities.Administrator;
import dae.cardiacrc.entities.Person;
import dae.cardiacrc.entities.Professional;
import dae.cardiacrc.exceptions.MyConstraintViolationException;
import dae.cardiacrc.exceptions.MyEntityExistsException;
import dae.cardiacrc.exceptions.MyEntityNotFoundException;
import dae.cardiacrc.exceptions.MyIllegalArgumentException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.io.Console;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Stateless
public class AdministratorBean {
    @PersistenceContext
    private EntityManager em;

    public Person authenticate(final String username, final String password) throws Exception {
        Person person = em.find(Person.class, username);
        if (person != null && person.getPassword().equals(Person.hashPassword(password))) {
            return person;
        }
        throw new Exception("Failed logging in with username '" + username + "': unknown username or wrong password");
    }

    public void create(String username, String name, String password, String email) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        Administrator administrator =  em.find(Administrator.class, username);

        if(administrator != null) {
            throw new MyEntityExistsException("Administrator already exists!");
        }

        try {
            administrator = new Administrator(username, name, password, email);
            em.persist(administrator);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public List<Administrator> getAllAdministrators() {
        return (List<Administrator>) em.createNamedQuery("getAllAdministrators").getResultList();
    }

    public List counts(){
//        System.out.println(em.createNamedQuery("countAdministrators").getResultList());
        List total = Arrays.asList(
                em.createNamedQuery("countAdministrators").getResultList(),
                em.createNamedQuery("countProfessionals").getResultList(),
                em.createNamedQuery("countPatients").getResultList(),
                em.createNamedQuery("countDataTypes").getResultList());
        return total;
    }

    public Administrator findAdministrator(String username) throws MyEntityNotFoundException {
        Administrator administrator =  em.find(Administrator.class, username);
        if(administrator == null) {
            throw new MyEntityNotFoundException("Administrator not found!");
        }
        return administrator;
    }

    public void update(String username, String password, String name, String newPassword, String email) throws MyEntityNotFoundException, MyIllegalArgumentException {
        Administrator administrator = findAdministrator(username);

        if (!administrator.getPassword().equals(Administrator.hashPassword(password))){
            throw new MyIllegalArgumentException("Incorrect password");
        }

        em.lock(administrator, LockModeType.OPTIMISTIC);
        if (name != null){
            administrator.setName(name);
        }
        if (email != null){
            administrator.setEmail(email);
        }
        if (newPassword != null){
            administrator.setPassword(newPassword);
        }

        em.merge(administrator);
    }

    public void delete(String username) throws MyEntityNotFoundException {
        Administrator administrator = findAdministrator(username);
        em.remove(administrator);
    }
}
