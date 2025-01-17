package dae.cardiacrc.entities;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllAdministrators",
                query = "SELECT a FROM Administrator a ORDER BY a.name"
        )
})
public class Administrator extends Person {
    public Administrator() {
    }

    public Administrator(String username, String password, String name, String email) {
        super(username, password, name, email);
    }
}
