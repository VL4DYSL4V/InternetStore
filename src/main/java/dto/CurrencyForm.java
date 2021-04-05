package dto;

import java.util.Objects;

public final class CurrencyForm {

    private Integer id;

    private String currencyName;

    public CurrencyForm() {
    }

    public CurrencyForm(Integer id, String currencyName) {
        this.id = id;
        this.currencyName = currencyName;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
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
        CurrencyForm that = (CurrencyForm) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(currencyName, that.currencyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, currencyName);
    }

    @Override
    public String toString() {
        return "CurrencyForm{" +
                "id=" + id +
                ", currencyName='" + currencyName + '\'' +
                '}';
    }
}
