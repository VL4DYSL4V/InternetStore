package main;

import config.InternetStoreConfiguration;
import dao.bankAccount.BankAccountDao;
import dao.bankAccount.HibernateBankAccountDao;
import dao.bankAccount.MySqlBankAccountDao;
import dao.country.CountryDao;
import dao.country.HibernateCountryDao;
import dao.country.MySqlCountryDao;
import dao.currency.CurrencyDao;
import dao.currency.MySqlCurrencyDao;
import dao.item.HibernateItemDao;
import dao.item.ItemDao;
import entity.BankAccount;
import entity.Country;
import entity.Currency;
import entity.Item;
import exception.dao.FetchException;
import exception.dao.StoreException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.EncryptionService;
import service.EncryptionServiceImpl;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.sql.Date;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(InternetStoreConfiguration.class);
        CountryDao mySqlCountryDao = applicationContext.getBean("mySqlCountryDao", MySqlCountryDao.class);
        CountryDao hibCountryDao = applicationContext.getBean("hibernateCountryDao", HibernateCountryDao.class);
        CurrencyDao currencyDao = applicationContext.getBean("mySqlCurrencyDao",
                MySqlCurrencyDao.class);
        BankAccountDao bankAccountDao = applicationContext.getBean("hibernateBankAccountDao", HibernateBankAccountDao.class);
        BankAccountDao sqlBankAccountDao = applicationContext.getBean("mySqlBankAccountDao", MySqlBankAccountDao.class);

        ItemDao itemDao = applicationContext.getBean("hibernateItemDao", HibernateItemDao.class);
        Currency wrongCurrency = new Currency(19, "AAA");
        Currency rightCurrency = new Currency(4, "RUB");

//
//        Country c = new Country();
//        c.setId(11);
//        c.setCountryName("Russia");
//        List<Currency> currencies = new LinkedList<>();
//        currencies.add(rightCurrency);
//        c.setCurrencies(currencies);
//        hibCountryDao.update(7, c);


//        Item item = new Item();
//        item.setId(1L);
//        item.setAmount(2);
//        item.setName("Name");
//        item.setCurrency(wrongCurrency);
//        item.setImageUrl("url");
//        item.setItemDescription("descr");
//        item.setPriceForOne(BigDecimal.ONE);
//        item.setPutUpForSale(new Date(2021, 1, 1));
//        itemDao.save(item);
//        testBankAccountDao(bankAccountDao, currencyDao);
//        testBankAccountDao(bankAccountDao, currencyDao);

    }

    private static void testBankAccountDao(BankAccountDao bankAccountDao, CurrencyDao currencyDao) {
        try {
            Currency eur = currencyDao.getByName("EUR");
            Currency usd = currencyDao.getByName("USD");
//            Currency uah = currencyDao.getByName("UAH");

            Currency uah = new Currency(2, "UaH");

            BankAccount eurAccount = new BankAccount(1L, BigDecimal.valueOf(347782.2), eur);
            BankAccount usdAccount = new BankAccount(2L, BigDecimal.valueOf(56332.42), usd);
            BankAccount uahAccount = new BankAccount(3L, BigDecimal.valueOf(18900.04), uah);
            BankAccount usd2Account = new BankAccount(4L, BigDecimal.valueOf(12345.89), usd);

            bankAccountDao.save(eurAccount);
            bankAccountDao.save(usdAccount);
            bankAccountDao.save(uahAccount);
            bankAccountDao.save(usd2Account);

            bankAccountDao.allEntities().forEach(System.out::println);
            System.out.println("Delete: ");

            bankAccountDao.delete(uahAccount.getId());
            bankAccountDao.allEntities().forEach(System.out::println);

            try {
                System.out.println(bankAccountDao.getById(uahAccount.getId()));
            } catch (FetchException e) {
                System.out.println("Exception occurred");
            }
            System.out.println("Get by id: ");
            System.out.println(bankAccountDao.getById(usdAccount.getId()));

            System.out.println("Update: ");
            bankAccountDao.update(usdAccount.getId(), new BankAccount(usdAccount.getId(), BigDecimal.valueOf(0.45), usd));
            bankAccountDao.allEntities().forEach(System.out::println);
        } catch (Throwable t) {
            t.printStackTrace();
        }
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
            countryDao.allEntities().forEach(System.out::println);
            System.out.println("******************");
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
            currencyDao.delete(currency.getId());
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
