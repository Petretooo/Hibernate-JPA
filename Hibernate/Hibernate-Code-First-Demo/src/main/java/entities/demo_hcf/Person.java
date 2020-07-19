package entities.demo_hcf;

import entities.BaseEntity;

import javax.persistence.*;


@MappedSuperclass
public abstract class Person extends BaseEntity {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    public Person(){

    }

    @Column(name = "fist_name", nullable = false, unique = true)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", nullable = false, unique = true)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "phone_number", nullable = false, unique = true)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
