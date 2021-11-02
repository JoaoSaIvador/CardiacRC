package pt.ipleiria.estg.dei.ei.dae.cardiacrc.entities;

import io.smallrye.common.constraint.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;

@MappedSuperclass
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
public class User implements Serializable {
    @NotNull
    private String password;
    @NotNull
    private String name;
    @NotNull
    @Email
    private String email;

    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public User() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
