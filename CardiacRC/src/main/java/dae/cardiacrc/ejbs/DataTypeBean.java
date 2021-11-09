package dae.cardiacrc.ejbs;

import dae.cardiacrc.entities.DataType;
import dae.cardiacrc.exceptions.MyConstraintViolationException;
import dae.cardiacrc.exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless
public class DataTypeBean {
    @PersistenceContext
    private EntityManager em;

    public void create(String name, String unit, int min, int max) throws MyConstraintViolationException {
        try {
            DataType dataType = new DataType(name, unit, min, max);
            em.persist(dataType);
        }catch (ConstraintViolationException e){
            throw new MyConstraintViolationException(e);
        }
    }

    public DataType findDataType(int id) throws MyEntityNotFoundException {
        DataType dataType = em.find(DataType.class, id);
        if (dataType == null){
            throw new MyEntityNotFoundException("DataType not found!");
        }
        return dataType;
    }

    public List<DataType> getAllDataTypes(){
        return (List<DataType>) em.createNamedQuery("getAllDataTypes").getResultList();
    }

    public void updateDataType(int id, String name, String unit, int min, int max) throws MyEntityNotFoundException {
        DataType dataType = findDataType(id);
        em.lock(dataType, LockModeType.OPTIMISTIC);
        dataType.setName(name);
        dataType.setUnit(unit);
        dataType.setMin(min);
        dataType.setMax(max);
        em.merge(dataType);
    }

    public void deleteDataType(int id) throws MyEntityNotFoundException {
        DataType dataType = findDataType(id);
        em.remove(dataType);
    }

}
