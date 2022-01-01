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
    private double min;

    @NotNull
    private double max;

    @ManyToOne
    @JoinColumn(name = "quantitativeDataType")
    @NotNull
    private QuantitativeDataType quantitativeDataType;

    @Version
    private int version;

    public QualitativeDataType() {
    }

    public QualitativeDataType(String name, double min, double max, QuantitativeDataType quantitativeDataType) {
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

    public QuantitativeDataType getQuantitativeDataType() {
        return quantitativeDataType;
    }

    public void setQuantitativeDataTypes(QuantitativeDataType quantitativeDataType) {
        this.quantitativeDataType = quantitativeDataType;
    }
}
