package entities;

import orm.annotations.Column;
import orm.annotations.Entity;
import orm.annotations.Id;

import java.util.Date;

@Entity
public class User {
    @Id
    private int id;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private int age;
    @Column
    private Date registration;


    public User(String username, String password, int age, Date registration){
        this.username = username;
        this.password = password;
        this.age = age;
        this.registration = registration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getRegistrationDate() {
        return registration;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registration = registrationDate;
    }
}
