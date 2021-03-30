package dto;

import java.util.Objects;

public final class CountryForm {

    private String countryName;

    public CountryForm() {
    }

    public CountryForm(String countryName) {
        this.countryName = countryName;
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
        CountryForm that = (CountryForm) o;
        return Objects.equals(countryName, that.countryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryName);
    }

    @Override
    public String toString() {
        return "CountryForm{" +
                "countryName='" + countryName + '\'' +
                '}';
    }
}
