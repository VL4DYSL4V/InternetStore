package dto;

import java.util.Objects;

public final class CurrencyForm {

    private String currencyName;

    public CurrencyForm() {
    }

    public CurrencyForm(String currencyName) {
        this.currencyName = currencyName;
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
        CurrencyForm that = (CurrencyForm) o;
        return Objects.equals(currencyName, that.currencyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currencyName);
    }

    @Override
    public String toString() {
        return "CurrencyForm{" +
                "currencyName='" + currencyName + '\'' +
                '}';
    }
}
