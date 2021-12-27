package dae.cardiacrc.dtos;

import java.io.Serializable;

public class QualitativeDataTypeDTO implements Serializable {
    private int id;
    private String name;
    private int min;
    private int max;

    public QualitativeDataTypeDTO(int id, String name, int min, int max) {
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

//    public int getQuantitativeId() {
//        return quantitativeId;
//    }
//
//    public void setQuantitativeId(int quantitativeId) {
//        this.quantitativeId = quantitativeId;
//    }
//
//    public String getQuantitativeName() {
//        return quantitativeName;
//    }
//
//    public void setQuantitativeName(String quantitativeName) {
//        this.quantitativeName = quantitativeName;
//    }
}
