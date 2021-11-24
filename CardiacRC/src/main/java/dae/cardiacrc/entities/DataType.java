package dae.cardiacrc.entities;

import io.smallrye.common.constraint.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "datatypes")
@NamedQueries({
        @NamedQuery(
                name = "getAllDataTypes",
                query = "SELECT d FROM DataType d ORDER BY d.name"
        )
})
@SequenceGenerator(name = "datatype_id", sequenceName = "datatype_id",  initialValue = 1)
@Entity
public class DataType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "datatype_id")
    private int id;

    @NotNull
    private String name;

    @NotNull
    private String unit;

    @NotNull
    private int min;

    @NotNull
    private int max;

    @Version
    private int version;

    public DataType(String name, String unit, int min, int max) {
        this.name = name;
        this.unit = unit;
        this.min = min;
        this.max = max;
    }

    public DataType() {
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
}
