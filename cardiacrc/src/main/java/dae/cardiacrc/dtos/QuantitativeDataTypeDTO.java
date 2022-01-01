package dae.cardiacrc.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuantitativeDataTypeDTO implements Serializable {
    private int id;
    private String name;
    private String unit;
    private double min;
    private double max;
    private List<QualitativeDataTypeDTO> qualitativeDataTypeDTOs;
    private boolean deleted;

    public QuantitativeDataTypeDTO(int id, String name, String unit, double min, double max, boolean deleted) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.min = min;
        this.max = max;
        this.deleted = deleted;
        qualitativeDataTypeDTOs = new ArrayList<QualitativeDataTypeDTO>();
    }

    public QuantitativeDataTypeDTO() {
        qualitativeDataTypeDTOs = new ArrayList<QualitativeDataTypeDTO>();
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

    public List<QualitativeDataTypeDTO> getQualitativeDataTypeDTOs() {
        return qualitativeDataTypeDTOs;
    }

    public void setQualitativeDataTypeDTOs(List<QualitativeDataTypeDTO> qualitativeDataTypeDTOs) {
        this.qualitativeDataTypeDTOs = qualitativeDataTypeDTOs;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}
