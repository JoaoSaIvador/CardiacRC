package dae.cardiacrc.dtos;

import java.io.Serializable;

public class QualitativeDataTypeDTO implements Serializable {
    private int id;
    private String name;
    private float min;
    private float max;

    public QualitativeDataTypeDTO(int id, String name, float min, float max) {
        this.id = id;
        this.name = name;
        this.min = min;
        this.max = max;
    }

    public QualitativeDataTypeDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
