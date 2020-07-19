package entities.billsPaymentSystem;

import entities.BaseEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "billing_details")
public class BillingDetail extends BaseEntity {
    private String number;
    private User user;
    private creditCard creditCard;
    private bankAccount bankAccount;
    private PaymenyType paymenyType;

    public BillingDetail() {
    }

    public BillingDetail(User user, creditCard creditCard){
        this.user = user;
        this.creditCard = creditCard;
        this.paymenyType = PaymenyType.CreditCard;
    }

    public BillingDetail(User user, bankAccount bankAccount){
        this.user = user;
        this.bankAccount = bankAccount;
        this.paymenyType = PaymenyType.BankAccount;
    }

    @Column(name = "number", nullable = false, unique = true)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
