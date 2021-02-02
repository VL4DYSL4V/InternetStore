package entity;

import constraint.CurrencyConstraint;

import javax.annotation.concurrent.NotThreadSafe;
import javax.persistence.*;
import java.util.Objects;

@Table
@Entity(name = "is_currency")
@CurrencyConstraint
@NotThreadSafe
public final class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "currency_id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "currency_name", unique = true, nullable = false)
    private String currencyName;

    public Currency() {
    }

    public Currency(int id, String currencyName) {
        this.id = id;
        this.currencyName = currencyName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return Objects.equals(id, currency.id) &&
                Objects.equals(currencyName, currency.currencyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, currencyName);
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", currencyName='" + currencyName + '\'' +
                '}';
    }
}
