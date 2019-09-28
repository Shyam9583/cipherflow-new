package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "cf_user")
public class User implements Serializable {

    private static final long serialVersionUID = 975917579178975919L;

    @Id
    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "email_id", nullable = false)
    private String email;
    @Column(name = "salt", nullable = false)
    private String salt;
    @Column(name = "secret_key", nullable = false)
    private String secretKey;
    @Column(name = "iv_key", nullable = false)
    private String ivKey;

    public User() {
    }

    public User(String userId, String password, String firstName, String lastName, String email, String salt, String secretKey, String ivKey) {
        this.userId = userId;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.salt = salt;
        this.secretKey = secretKey;
        this.ivKey = ivKey;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getIvKey() {
        return ivKey;
    }

    public void setIvKey(String ivKey) {
        this.ivKey = ivKey;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", salt='" + salt + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", ivKey='" + ivKey + '\'' +
                '}';
    }
}
