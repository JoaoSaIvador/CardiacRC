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
    private int min;

    @NotNull
    private int max;

    @OneToMany
    private List<QualitativeDataType> dataTypes;

    @Version
    private int version;

    public QuantitativeDataType(String name, String unit, int min, int max) {
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

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public List<QualitativeDataType> getDataTypes() {
        return dataTypes;
    }

    public void setDataTypes(List<QualitativeDataType> dataTypes) {
        this.dataTypes = dataTypes;
    }
}
