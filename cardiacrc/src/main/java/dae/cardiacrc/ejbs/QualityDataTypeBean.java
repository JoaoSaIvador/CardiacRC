package dae.cardiacrc.ejbs;

import dae.cardiacrc.entities.QualitativeDataType;
import dae.cardiacrc.entities.QuantitativeDataType;
import dae.cardiacrc.exceptions.MyConstraintViolationException;
import dae.cardiacrc.exceptions.MyEntityNotFoundException;
import dae.cardiacrc.exceptions.MyIllegalArgumentException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

@Stateless
public class QualityDataTypeBean {
    @PersistenceContext
    private EntityManager em;

    public void create(String name, double min, double max, int dataTypeId) throws MyEntityNotFoundException, MyConstraintViolationException, MyIllegalArgumentException {
        QuantitativeDataType dataType = em.find(QuantitativeDataType.class, dataTypeId);
        if (dataType == null){
            throw new MyEntityNotFoundException("Datatype not found!");
        }
        if (dataType.getMin() > min){
            throw new MyIllegalArgumentException("Invalid minimum for qualitative dataType!");
        }
        if (dataType.getMax() < max){
            throw new MyIllegalArgumentException("Invalid maximum for qualitative dataType!");
        }
        verifyIntervals(dataType, min, max);
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

    public void update(int dataTypeId, int qualityid, String name, double min, double max) throws MyEntityNotFoundException, MyIllegalArgumentException {
        QualitativeDataType qualitativeDataType = findQualityDataType(qualityid);
        QuantitativeDataType dataType = em.find(QuantitativeDataType.class,dataTypeId);

        if (!dataType.getDataTypes().contains(qualitativeDataType)){
            throw new MyIllegalArgumentException("Qualitative dataType not found in this dataType!");
        }

        em.lock(qualitativeDataType, LockModeType.OPTIMISTIC);
        if (name != null){
            qualitativeDataType.setName(name);
        }
        if (min != -1 && min > dataType.getMin()){
            qualitativeDataType.setMin(min);
        }
        if (max != -1 && max < dataType.getMax()){
            qualitativeDataType.setMax(max);
        }
        verifyIntervals(dataType,qualitativeDataType.getMin(), qualitativeDataType.getMax());
        em.merge(qualitativeDataType);
    }

    public void delete(int dataTypeId, int qualitativeID) throws MyEntityNotFoundException, MyIllegalArgumentException {
        QualitativeDataType qualitativeDataType = findQualityDataType(qualitativeID);

        QuantitativeDataType dataType = em.find(QuantitativeDataType.class,dataTypeId);
        if (!dataType.getDataTypes().contains(qualitativeDataType)){
            throw new MyIllegalArgumentException("Qualitative dataType not found in this dataType!");
        }

        qualitativeDataType.getQuantitativeDataType().removeQualityDataType(qualitativeDataType);
        em.remove(qualitativeDataType);
    }

    protected void verifyIntervals(QuantitativeDataType dataType, double min, double max) throws MyIllegalArgumentException {
        for (QualitativeDataType type : dataType.getDataTypes()) {
            if (min >= type.getMax()){
                continue;
            }
            if (!(max <= dataType.getMin())){
                throw new MyIllegalArgumentException("Invalid interval for qualitative dataType!");
            }
        }
    }
}
