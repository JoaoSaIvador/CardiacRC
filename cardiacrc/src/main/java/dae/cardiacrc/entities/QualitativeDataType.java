package dae.cardiacrc.entities;

import io.smallrye.common.constraint.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "qualitativedatatypes")
@NamedQueries({
        @NamedQuery(
                name = "getAllQualitativeDataTypes",
                query = "SELECT d FROM QualitativeDataType d ORDER BY d.name"
        )
})
@SequenceGenerator(name = "qualitativedatatype_id", sequenceName = "qualitativedatatype_id",  initialValue = 1)
@Entity
public class QualitativeDataType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "qualitativedatatype_id")
    private int id;

    @NotNull
    private String name;

    @NotNull
    private float min;

    @NotNull
    private float max;

    @ManyToOne
    @JoinColumn(name = "quantitativeDataType")
    @NotNull
    private QuantitativeDataType quantitativeDataType;

    @Version
    private int version;

    public QualitativeDataType() {
    }

    public QualitativeDataType(String name, float min, float max, QuantitativeDataType quantitativeDataType) {
        this.name = name;
        this.min = min;
        this.max = max;
        this.quantitativeDataType = quantitativeDataType;
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

    public QuantitativeDataType getQuantitativeDataType() {
        return quantitativeDataType;
    }

    public void setQuantitativeDataTypes(QuantitativeDataType quantitativeDataType) {
        this.quantitativeDataType = quantitativeDataType;
    }
}
