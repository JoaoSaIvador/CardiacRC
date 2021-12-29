package dae.cardiacrc.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuantitativeDataTypeDTO implements Serializable {
    private int id;
    private String name;
    private String unit;
    private float min;
    private float max;
    private List<QualitativeDataTypeDTO> qualitativeDataTypeDTOs;

    public QuantitativeDataTypeDTO(int id, String name, String unit, float min, float max) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.min = min;
        this.max = max;
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

    public List<QualitativeDataTypeDTO> getQualitativeDataTypeDTOs() {
        return qualitativeDataTypeDTOs;
    }

    public void setQualitativeDataTypeDTOs(List<QualitativeDataTypeDTO> qualitativeDataTypeDTOs) {
        this.qualitativeDataTypeDTOs = qualitativeDataTypeDTOs;
    }
}
