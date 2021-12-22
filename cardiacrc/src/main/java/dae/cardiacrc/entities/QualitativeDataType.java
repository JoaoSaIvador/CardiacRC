package dae.cardiacrc.entities;

import io.smallrye.common.constraint.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "quanlitativedatatypes")
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
    private int min;

    @NotNull
    private int max;

    @ManyToOne
    @JoinColumn(name = "quantitativeDataType")
    @NotNull
    private QuantitativeDataType quantitativeDataType;

    @Version
    private int version;

    public QualitativeDataType() {
    }

    public QualitativeDataType(String name, int min, int max, QuantitativeDataType quantitativeDataType) {
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

    public QuantitativeDataType getQuantitativeDataTypes() {
        return quantitativeDataType;
    }

    public void setQuantitativeDataTypes(QuantitativeDataType quantitativeDataType) {
        this.quantitativeDataType = quantitativeDataType;
    }
}
