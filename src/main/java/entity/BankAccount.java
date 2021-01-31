package entity;

import javax.annotation.concurrent.NotThreadSafe;
import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Table
@Entity(name = "is_bank_account")
@NotThreadSafe
public final class BankAccount {

    @Id
    @Column(name = "bank_account_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "funds", nullable = false)
    private BigDecimal funds = BigDecimal.ZERO;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "currency_id", referencedColumnName = "currency_id", nullable = false)
    private Currency currency;

    public BankAccount() {
    }

    public BankAccount(Long id, BigDecimal funds, Currency currency) {
        checkBoundsForFunds(funds);
        this.id = id;
        this.funds = funds.setScale(2, RoundingMode.DOWN);
        this.currency = currency;
    }

    private void checkBoundsForFunds(BigDecimal funds){
        if(funds.compareTo(BigDecimal.ZERO) < 0 || funds.toString().length() > 20){
            throw new IllegalArgumentException("funds must be positive");
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getFunds() {
        return funds;
    }

    public void setFunds(BigDecimal funds) {
        checkBoundsForFunds(funds);
        this.funds = funds;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount that = (BankAccount) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(funds, that.funds) &&
                Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, funds, currency);
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", funds=" + funds +
                ", currency=" + currency +
                '}';
    }
}
