package entities.billsPaymentSystem;

import entities.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "credit_cards")
public class creditCard extends BaseEntity{
    private String cardType;
    private LocalDateTime expirationMonth;
    private LocalDateTime expirationYear;

    public creditCard() {
    }

    @Column(name = "card_type")
    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    @Column(name = "expiration_month")
    public LocalDateTime getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(LocalDateTime expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    @Column(name = "expiration_year")
    public LocalDateTime getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(LocalDateTime expirationYear) {
        this.expirationYear = expirationYear;
    }
}
