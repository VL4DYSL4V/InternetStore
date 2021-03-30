package filler;

import dao.currency.CurrencyDao;
import entity.Currency;
import exception.dao.StoreException;

import java.util.Collection;
import java.util.HashSet;

public final class CurrencyTestDataFiller {

    private final CurrencyDao currencyDao;

    public CurrencyTestDataFiller(CurrencyDao currencyDao) {
        this.currencyDao = currencyDao;
    }

    public void fillTable() throws StoreException {
        currencyDao.saveAll(testCurrencies());
    }

    private Collection<Currency> testCurrencies() {
        Collection<Currency> out = new HashSet<>();
        out.add(new Currency(1, "EUR"));
        out.add(new Currency(2, "KOD"));
        out.add(new Currency(3, "UAH"));
        out.add(new Currency(4, "RUB"));
        out.add(new Currency(5, "NOK"));
        out.add(new Currency(6, "INR"));
        out.add(new Currency(7, "NZD"));
        out.add(new Currency(8, "PLN"));
        out.add(new Currency(9, "SEK"));
        out.add(new Currency(10, "USD"));
        out.add(new Currency(11, "SGD"));
        out.add(new Currency(12, "GBP"));
        return out;
    }
}
