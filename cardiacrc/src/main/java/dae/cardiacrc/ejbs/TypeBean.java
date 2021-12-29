package dae.cardiacrc.ejbs;

import dae.cardiacrc.entities.Administrator;
import dae.cardiacrc.entities.Type;
import dae.cardiacrc.exceptions.MyConstraintViolationException;
import dae.cardiacrc.exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless
public class TypeBean {
    @PersistenceContext
    private EntityManager em;

    public void create(String name) throws MyConstraintViolationException {
        try {
            Type type = new Type(name);
            em.persist(type);
        }catch (ConstraintViolationException e){
            throw new MyConstraintViolationException(e);
        }
    }

    public List<Type> getAllTypes(){
        return (List<Type>) em.createNamedQuery("getAllTypes").getResultList();
    }

    public Type findType(int id) throws MyEntityNotFoundException {
        Type type = em.find(Type.class,id);
        if(type == null){
            throw new MyEntityNotFoundException("Type not found!");
        }
        return type;
    }
}
