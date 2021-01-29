package entity;

import javax.persistence.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Table
@Entity(name = "is_country")
public final class Country {

    @Id
    @Column(name = "country_id")
    private int id;

    @Column(name = "country_name", unique = true)
    private String countryName;

    @ManyToMany
    @JoinTable(name = "is_country_to_currency",
            joinColumns = @JoinColumn(name = "country_id", unique = false),
            inverseJoinColumns = @JoinColumn(name = "currency_id", unique = false))
    private List<Currency> currencies = new LinkedList<>();

    public Country() {
    }

    public Country(int id, String countryName, List<Currency> currencies){
        this.id = id;
        this.countryName = countryName;
        this.currencies = currencies;
    }

    public void addCurrency(Currency currency){
        currencies.add(currency);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public List<Currency> getCurrencies() {
        return Collections.unmodifiableList(currencies);
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return id == country.id &&
                Objects.equals(countryName, country.countryName) &&
                Objects.equals(currencies, country.currencies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, countryName, currencies);
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", countryName='" + countryName + '\'' +
                ", currencies=" + currencies +
                '}';
    }
}
