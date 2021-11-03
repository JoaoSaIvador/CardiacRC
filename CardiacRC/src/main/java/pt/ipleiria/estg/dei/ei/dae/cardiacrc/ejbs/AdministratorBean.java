package pt.ipleiria.estg.dei.ei.dae.cardiacrc.ejbs;

import pt.ipleiria.estg.dei.ei.dae.cardiacrc.entities.Administrator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class AdministratorBean {
    @PersistenceContext
    private EntityManager em;

    public void create(String username, String password, String name, String email) {
        Administrator administrator = new Administrator(username, password, name, email);
        em.persist(administrator);
    }

    public Administrator findAdministrator(String username) {
        return  em.find(Administrator.class, username);
    }

    public void update(String username, String password, String name, String email) {
        Administrator administrator = em.find(Administrator.class, username);

        if (administrator != null) {
            administrator.setPassword(password);
            administrator.setName(name);
            administrator.setEmail(email);
        }
    }

    public void remove (String username) {
        Administrator administrator = em.find(Administrator.class, username);

        if (administrator != null) {
            em.remove(administrator);
        }
    }
}
