package dae.cardiacrc.dtos;

import java.io.Serializable;

public class QualitativeDataTypeDTO implements Serializable {
    private int id;
    private String name;
    private double min;
    private double max;

    public QualitativeDataTypeDTO(int id, String name, double min, double max) {
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
}
