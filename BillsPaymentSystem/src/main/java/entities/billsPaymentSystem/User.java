package entities.billsPaymentSystem;

import entities.BaseEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<BillingDetail> billingDetail;

    public User() {
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @OneToMany(mappedBy = "user", targetEntity = BillingDetail.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Set<BillingDetail> getBillingDetail() {
        return billingDetail;
    }

    public void setBillingDetail(Set<BillingDetail> billingDetail) {
        this.billingDetail = billingDetail;
    }
}
