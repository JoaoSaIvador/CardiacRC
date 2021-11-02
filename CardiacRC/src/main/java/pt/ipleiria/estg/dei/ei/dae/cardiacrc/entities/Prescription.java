package pt.ipleiria.estg.dei.ei.dae.cardiacrc.entities;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "prescriptions")
@Entity
public class Prescription implements Serializable {
    @Id
    private int id;
    @ManyToOne
    @JoinColumn(name = "professional_username")
    private Professional professional;
    @ManyToOne
    @JoinColumn(name = "pacient_username")
    private Pacient pacient;
    //TODO attributes missing
}
