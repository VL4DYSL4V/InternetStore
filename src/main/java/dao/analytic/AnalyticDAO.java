package dao.analytic;

import entity.Country;
import entity.Currency;
import exception.dao.FetchException;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

public interface AnalyticDAO {

    Collection<String> usersWhoGotReplyFromSeller();

    Map<Country, Collection<String>> countryToItemNames() throws FetchException;

    long countUsersWhoPostedItemsBetweenDates(LocalDate start, LocalDate end) throws FetchException;

    //    Сопоставьте количества товаров с каждой валютой.
    Map<Currency, Integer> currencyToItemAmount() throws FetchException;

}
