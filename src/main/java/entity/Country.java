package entity;

import javax.annotation.concurrent.NotThreadSafe;
import javax.persistence.*;
import java.util.Objects;

@Table
@Entity(name = "is_country")
@NotThreadSafe
public final class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "country_name", unique = true, nullable = false)
    private String countryName;

    public Country() {
    }

    public Country(String countryName) {
        this.countryName = countryName;
    }

    public Country(Integer id, String countryName) {
        this.id = id;
        this.countryName = countryName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(id, country.id) &&
                Objects.equals(countryName, country.countryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, countryName);
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", countryName='" + countryName + '\'' +
                '}';
    }
}
