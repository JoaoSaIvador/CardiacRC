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

@Stateless
public class QualityDataTypeBean {
    @PersistenceContext
    private EntityManager em;

    public void create(String name, int min, int max, int dataTypeId) throws MyEntityNotFoundException, MyConstraintViolationException {
        QuantitativeDataType dataType = em.find(QuantitativeDataType.class, dataTypeId);
        if (dataType == null){
            throw new MyEntityNotFoundException("Datatype not found!");
        }
        try {
            QualitativeDataType qualitativeDataType = new QualitativeDataType(name,min,max,dataType);
            dataType.addQualityDataType(qualitativeDataType);
            em.persist(qualitativeDataType);
        }catch (ConstraintViolationException e){
            throw new MyConstraintViolationException(e);
        }
    }

    public QualitativeDataType findQualityDataType(int id) throws MyEntityNotFoundException {
        QualitativeDataType qualitativeDataType = em.find(QualitativeDataType.class,id);
        if (qualitativeDataType == null){
            throw new MyEntityNotFoundException("Quality Data Type not found!");
        }
        return qualitativeDataType;
    }

    public void update(int id, String name, int min, int max) throws MyEntityNotFoundException {
        QualitativeDataType qualitativeDataType = findQualityDataType(id);
        em.lock(qualitativeDataType, LockModeType.OPTIMISTIC);
        qualitativeDataType.setName(name);
        qualitativeDataType.setMin(min);
        qualitativeDataType.setMax(max);
        em.merge(qualitativeDataType);
    }

    public void delete(int id) throws MyEntityNotFoundException {
        QualitativeDataType qualitativeDataType = findQualityDataType(id);
        qualitativeDataType.getQuantitativeDataTypes().removeQualityDataType(qualitativeDataType);
        em.remove(qualitativeDataType);
    }
}
