package dae.cardiacrc.entities;

import io.smallrye.common.constraint.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "types")
@Entity
@SequenceGenerator(name = "type_id", sequenceName = "type_id",  initialValue = 1)
public class Type implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "type_id")
    private int id;

    @NotNull
    private String name;

    public Type(String name) {
        this.name = name;
    }

    public Type() {
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
}
