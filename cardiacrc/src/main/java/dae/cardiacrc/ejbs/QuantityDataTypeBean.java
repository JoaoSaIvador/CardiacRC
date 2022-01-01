package dae.cardiacrc.ejbs;

import dae.cardiacrc.entities.QualitativeDataType;
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

    public void create(String name, String unit, double min, double max) throws MyConstraintViolationException {
        try {
            QuantitativeDataType quantitativeDataType = new QuantitativeDataType(name, unit, min, max);
            em.persist(quantitativeDataType);
        }catch (ConstraintViolationException e){
            throw new MyConstraintViolationException(e);
        }
    }

    public QuantitativeDataType findDataType(int id) throws MyEntityNotFoundException {
        QuantitativeDataType quantitativeDataType = em.find(QuantitativeDataType.class, id);
        if (quantitativeDataType == null || quantitativeDataType.isDeleted()){
            throw new MyEntityNotFoundException("DataType not found!");
        }
        return quantitativeDataType;
    }

    public List<QuantitativeDataType> getAllDataTypes(String type){
        if (type.equals("full")){
            return (List<QuantitativeDataType>) em.createNamedQuery("getAllQuantitativeDataTypes").getResultList();
        }
        else {
            return (List<QuantitativeDataType>) em.createNamedQuery("getQuantitativeDataTypes").getResultList();
        }
    }

    public void update(int id, String name, String unit, double min, double max) throws MyEntityNotFoundException {
        QuantitativeDataType quantitativeDataType = findDataType(id);
        em.lock(quantitativeDataType, LockModeType.OPTIMISTIC);
        if (name != null){
            quantitativeDataType.setName(name);
        }
        if (unit != null){
            quantitativeDataType.setUnit(unit);
        }
        if (min != -1){
            quantitativeDataType.setMin(min);
        }
        if (max != -1){
            quantitativeDataType.setMax(max);
        }
        em.merge(quantitativeDataType);
    }

    public void delete(int id) throws MyEntityNotFoundException {
        QuantitativeDataType quantitativeDataType = findDataType(id);
        if (quantitativeDataType.getObservations().isEmpty()){
            //NORMAL DELETE
            for (QualitativeDataType qualitativeDataType : quantitativeDataType.getDataTypes()) {
                em.remove(qualitativeDataType);
            }
            em.remove(quantitativeDataType);
        }
        else {
            //SOFTDELETE
            em.lock(quantitativeDataType,LockModeType.OPTIMISTIC);
            quantitativeDataType.setDeleted(true);
            em.merge(quantitativeDataType);
        }
    }
}
