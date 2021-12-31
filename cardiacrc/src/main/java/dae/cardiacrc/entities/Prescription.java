package dae.cardiacrc.entities;

import io.smallrye.common.constraint.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "prescriptions")
@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllPrescriptions",
                query = "SELECT p FROM Prescription p ORDER BY p.name"
        )
})
@SequenceGenerator(name = "prescription_id", sequenceName = "prescription_id",  initialValue = 1)
public class Prescription implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prescription_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "professional_username")
    @NotNull
    private Professional professional;

    @NotNull
    private String description;

    @NotNull
    private String name;

    @NotNull
    private int frequency;

    @NotNull
    @OneToOne
    private Type type;

    @NotNull
    @ManyToOne
    private Program program;

    @Version
    private int version;

    public Prescription() {
    }

    public Prescription(Professional professional, String description, String name, Type type, Program program) {
        this.professional = professional;
        this.description = description;
        this.name = name;
        this.type = type;
        this.program = program;
    }

    public int getId() {
        return id;
    }

    public Professional getProfessional() {
        return professional;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProfessional(Professional professional) {
        this.professional = professional;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }
}
