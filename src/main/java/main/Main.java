package main;

import config.InternetStoreConfiguration;
import dao.country.CountryDao;
import dao.country.HibernateCountryDao;
import dao.country.MySqlCountryDao;
import dao.currency.CurrencyDao;
import dao.currency.HibernateCurrencyDao;
import dao.currency.MySqlCurrencyDao;
import entity.Country;
import entity.Currency;
import exception.FetchException;
import exception.StoreException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(InternetStoreConfiguration.class);
//        CurrencyDao currencyDao = applicationContext.getBean("hibernateCurrencyDao", HibernateCurrencyDao.class);
//        DataSource dataSource = applicationContext.getBean("dataSource", BasicDataSource.class);
        CountryDao countryDao = applicationContext.getBean("mySqlCountryDao", MySqlCountryDao.class);
        CurrencyDao currencyDao = applicationContext.getBean("hibernateCurrencyDao",
                HibernateCurrencyDao.class);
//        testCountryDao(countryDao, currencyDao);
    }

    private static Collection<Country> testCountries() {
        Collection<Country> out = new HashSet<>();
        Currency eur = new Currency(1, "EUR");
        List<Currency> currencies = new LinkedList<>();
        currencies.add(eur);
        Country germany = new Country(1, "Germany", currencies);
        Country france = new Country(2, "France", currencies);
        Country belgium = new Country(3, "Belgium", currencies);
        Currency gbp = new Currency(12, "GBP");
        currencies = new LinkedList<>();
        currencies.add(eur);
        currencies.add(gbp);
        Country britain = new Country(4, "Britain", currencies);
        currencies = new LinkedList<>();
        Currency usd = new Currency(10, "USD");
        currencies.add(usd);
        currencies = new LinkedList<>();
        currencies.add(usd);
        Country usa = new Country(5, "USA", currencies);
        Country panama = new Country(6, "Panama", currencies);

        currencies = new LinkedList<>();
        currencies.add(new Currency(4, "RUB"));
        Country russia = new Country(7, "Russia", currencies);

        currencies = new LinkedList<>();
        currencies.add(new Currency(3, "UAH"));
        Country ukraine = new Country(8, "Ukraine", currencies);

        out.add(germany);
        out.add(france);
        out.add(belgium);
        out.add(britain);
        out.add(usa);
        out.add(panama);
        out.add(russia);
        out.add(ukraine);
        return out;
    }

    private static void insertTestCountries(Collection<Country> countries,
                                            CountryDao countryDao) {
        try {
            for (Country country :
                    countries) {
                countryDao.save(country);
            }
        } catch (StoreException e) {
            e.printStackTrace();
        }
    }

    private static Collection<Currency> testCurrencies() {
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

    private static void insertTestCurrencies(Collection<Currency> currencies, CurrencyDao currencyDao) {
        try {
            for (Currency currency :
                    currencies) {
                currencyDao.save(currency);
            }
        } catch (StoreException e) {
            e.printStackTrace();
        }
    }

    private static void testCountryDao(CountryDao countryDao, CurrencyDao currencyDao) {
        try {
//            countryDao.allEntities().forEach(System.out::println);
//            System.out.println("******************");
////            List<Currency> uah = new LinkedList<>();
////            uah.add(new Currency(3, "UAH"));
////            countryDao.update(8, new Country(3, "Ukraine", uah));
//            List<Currency> rub = new LinkedList<>();
//            rub.add(currencyDao.getByName("RUB"));
//            countryDao.save(new Country(7, "Russia", rub));
//            countryDao.allEntities().forEach(System.out::println);
//            System.out.println("******************");
            System.out.println(countryDao.getByName("Britain"));
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    private static void testCurrencyDao(CurrencyDao currencyDao) {
        Currency currency = new Currency(1, "USD");
        try {
//            currencyDao.delete(currency.getId());
            currencyDao.save(currency);
            System.out.println(currencyDao.allEntities());
            System.out.println(currencyDao.getById(currency.getId()));
            System.out.println(currencyDao.getByName(currency.getCurrencyName()));
            currencyDao.insertIgnore(currency);
            currency = new Currency(1, "EUR");
            currencyDao.update(currency.getId(), currency);
            System.out.println(currencyDao.allEntities());
            currencyDao.delete(currency.getId());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
