package dae.cardiacrc.entities;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllAdministrators",
                query = "SELECT a FROM Administrator a ORDER BY a.name"
        )
})
@Table(name = "administrators")
public class Administrator extends Person {
    public Administrator() {
    }

    public Administrator(String username, String name, String password, String email) {
        super(username, name, password, email);
    }
}
