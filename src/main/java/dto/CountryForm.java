package dto;

import java.util.Objects;

public final class CountryForm {

    private Integer id;

    private String countryName;

    public CountryForm() {
    }

    public CountryForm(Integer id, String countryName) {
        this.id = id;
        this.countryName = countryName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountryForm that = (CountryForm) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(countryName, that.countryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, countryName);
    }

    @Override
    public String toString() {
        return "CountryForm{" +
                "id=" + id +
                ", countryName='" + countryName + '\'' +
                '}';
    }
}
