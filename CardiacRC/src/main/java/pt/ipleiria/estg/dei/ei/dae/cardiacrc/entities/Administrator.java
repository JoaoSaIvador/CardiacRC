package pt.ipleiria.estg.dei.ei.dae.cardiacrc.entities;

import javax.persistence.Entity;

@Entity
public class Administrator extends Person {
    public Administrator() {
    }

    public Administrator(String username, String password, String name, String email) {
        super(username, password, name, email);
    }
}
