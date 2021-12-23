package dae.cardiacrc.ejbs;

import dae.cardiacrc.entities.QuantitativeDataType;
import dae.cardiacrc.exceptions.MyConstraintViolationException;
import dae.cardiacrc.exceptions.MyEntityNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Stateless
public class QuantityDataTypeBean {
    @PersistenceContext
    private EntityManager em;

    public void create(String name, String unit, int min, int max) throws MyConstraintViolationException {
        try {
            QuantitativeDataType quantitativeDataType = new QuantitativeDataType(name, unit, min, max);
            em.persist(quantitativeDataType);
        }catch (ConstraintViolationException e){
            throw new MyConstraintViolationException(e);
        }
    }

    public QuantitativeDataType findDataType(int id) throws MyEntityNotFoundException {
        QuantitativeDataType quantitativeDataType = em.find(QuantitativeDataType.class, id);
        if (quantitativeDataType == null){
            throw new MyEntityNotFoundException("DataType not found!");
        }
        return quantitativeDataType;
    }

    public List<QuantitativeDataType> getAllDataTypes(){
        return (List<QuantitativeDataType>) em.createNamedQuery("getAllQuantitativeDataTypes").getResultList();
    }

    public void update(int id, String name, String unit, int min, int max) throws MyEntityNotFoundException {
        QuantitativeDataType quantitativeDataType = findDataType(id);
        em.lock(quantitativeDataType, LockModeType.OPTIMISTIC);
        quantitativeDataType.setName(name);
        quantitativeDataType.setUnit(unit);
        quantitativeDataType.setMin(min);
        quantitativeDataType.setMax(max);
        em.merge(quantitativeDataType);
    }

    public void delete(int id) throws MyEntityNotFoundException {
        QuantitativeDataType quantitativeDataType = findDataType(id);
        em.remove(quantitativeDataType);
    }

}
