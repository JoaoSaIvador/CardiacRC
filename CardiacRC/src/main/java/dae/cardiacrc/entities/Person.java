package dae.cardiacrc.entities;

import io.smallrye.common.constraint.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

@MappedSuperclass
@Table(name = "users")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public class Person implements Serializable {
    @Id
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    @Email
    private String email;

    @Version
    private int version;

    public Person(String username, String name, String password, String email) {
        this.username = username;
        this.name = name;
        this.password = hashPassword(password);
        this.email = email;
    }

    public Person() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = hashPassword(password);
    }

    public void setUsername(String username) {
        this.username = username;
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

    public static String hashPassword(String password) {
        char[] encoded = null;
        try {
            ByteBuffer passwdBuffer = Charset.defaultCharset().encode(CharBuffer.wrap(password));
            byte[] passwdBytes = passwdBuffer.array();
            MessageDigest mdEnc = MessageDigest.getInstance("SHA-256");
            mdEnc.update(passwdBytes, 0, password.toCharArray().length);
            encoded = new BigInteger(1, mdEnc.digest()).toString(16).toCharArray();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Person.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new String(encoded);
    }

}
