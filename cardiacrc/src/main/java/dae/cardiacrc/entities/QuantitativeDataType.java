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
    private float min;

    @NotNull
    private float max;

    @OneToMany
    private List<QualitativeDataType> dataTypes;

    @Version
    private int version;

    public QuantitativeDataType(String name, String unit, float min, float max) {
        this.name = name;
        this.unit = unit;
        this.min = min;
        this.max = max;
        dataTypes = new ArrayList<QualitativeDataType>();
    }

    public QuantitativeDataType() {
        dataTypes = new ArrayList<QualitativeDataType>();
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

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public List<QualitativeDataType> getDataTypes() {
        return dataTypes;
    }

    public void addQualityDataType(QualitativeDataType dataType) {
        dataTypes.add(dataType);
    }

    public void removeQualityDataType(QualitativeDataType dataType){
        dataTypes.remove(dataType);
    }

    public String getQualitativeDataTypeName(float value){
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
}
