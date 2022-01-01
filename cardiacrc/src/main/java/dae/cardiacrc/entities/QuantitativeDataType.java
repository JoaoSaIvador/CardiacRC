package dae.cardiacrc.entities;

import io.smallrye.common.constraint.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Table(name = "quantitativedatatypes")
@NamedQueries({
        @NamedQuery(
                name = "getAllQuantitativeDataTypes",
                query = "SELECT d FROM QuantitativeDataType d ORDER BY d.name"
        ),
        @NamedQuery(
                name = "getQuantitativeDataTypes",
                query = "SELECT d FROM QuantitativeDataType d WHERE d.deleted = false ORDER BY d.name"
        )
})
@SequenceGenerator(name = "quantitativedatatype_id", sequenceName = "quantitativedatatype_id",  initialValue = 1)
@Entity
public class QuantitativeDataType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "quantitativedatatype_id")
    private int id;

    @NotNull
    private String name;

    @NotNull
    private String unit;

    @NotNull
    private double min;

    @NotNull
    private double max;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Observation> observations;

    @OneToMany(fetch = FetchType.LAZY)
    private List<QualitativeDataType> dataTypes;

    private boolean deleted;

    @Version
    private int version;

    public QuantitativeDataType(String name, String unit, double min, double max) {
        this.name = name;
        this.unit = unit;
        this.min = min;
        this.max = max;
        deleted = false;
        dataTypes = new ArrayList<QualitativeDataType>();
        observations = new ArrayList<Observation>();
    }

    public QuantitativeDataType() {
        dataTypes = new ArrayList<QualitativeDataType>();
        observations = new ArrayList<Observation>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public List<QualitativeDataType> getDataTypes() {
        return dataTypes;
    }

    public List<Observation> getObservations() {
        return observations;
    }

    public void addQualityDataType(QualitativeDataType dataType) {
        dataTypes.add(dataType);
    }

    public void removeQualityDataType(QualitativeDataType dataType){
        dataTypes.remove(dataType);
    }

    public String getQualitativeDataTypeName(double value){
        String valueName = null;
        if (value < min){
            valueName = String.valueOf(min);
        }else if (value > max){
            valueName = String.valueOf(max);
        }
        else{
            for (QualitativeDataType dataType : dataTypes) {
                if (dataType.getMin() <= value && value <= dataType.getMax()){
                    valueName = dataType.getName();
                }
            }
        }
        return valueName;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
