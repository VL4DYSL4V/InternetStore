package main;

import config.InternetStoreConfiguration;
import dao.orm.*;
import dao.orm.hibernate.comment.HibernateCommentDao;
import dao.orm.hibernate.country.HibernateCountryDao;
import dao.orm.hibernate.currency.HibernateCurrencyDao;
import dao.orm.hibernate.item.HibernateItemDao;
import dao.orm.hibernate.phoneNumber.HibernatePhoneNumberDao;
import dao.orm.hibernate.user.HibernateUserDao;
import entity.Country;
import entity.Currency;
import entity.Item;
import entity.PhoneNumber;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(InternetStoreConfiguration.class);
        OrmCurrencyDao hibCurrencyDao =
                applicationContext.getBean("hibernateCurrencyDao", HibernateCurrencyDao.class);
        OrmCountryDao hibCountryDao =
                applicationContext.getBean("hibernateCountryDao", HibernateCountryDao.class);
        OrmItemDao hibItemDao =
                applicationContext.getBean("hibernateItemDao", HibernateItemDao.class);
        OrmPhoneNumberDao hibPhoneNumberDao  =
                applicationContext.getBean("hibernatePhoneNumberDao", HibernatePhoneNumberDao.class);
        OrmUserDao hibUserDao =
                applicationContext.getBean("hibernateUserDao", HibernateUserDao.class);
        OrmCommentDao hibCommentDao =
                applicationContext.getBean("hibernateCommentDao", HibernateCommentDao.class);

//        PhoneNumber phoneNumber = new PhoneNumber(1L, "0970367789");
//        hibPhoneNumberDao.save(phoneNumber);
//        Country country = null;
//        Currency currency = hibCurrencyDao.getByName("USD");
//
//        Item item = new Item();
//        item.setId(1L);
//        item.setName("computer");
//        item.setAmount(1);
//        item.setPriceForOne(BigDecimal.valueOf(2365.55));
//        item.setPutUpForSale(new Date(System.currentTimeMillis()));
//        item.setItemDescription("Dell computer, almost new");
//        item.setImageUrl("");
//        item.setCurrency(currency);
//        item.setCountry(country);
//        item.setPhoneNumber(phoneNumber);
//
//        System.out.println(hibCommentDao.commentsWithUserId(1L));
//        System.out.println(hibCommentDao.commentsWithItemId(4L));
//        hibItemDao.save(item);

        System.out.println(hibItemDao.getById(4L));
        System.out.println(hibUserDao.getById(1L));
        System.out.println(hibUserDao.allEntities());

//        System.out.println(hibItemDao.allEntities());
//        System.out.println(hibItemDao.itemsWithSimilarName("com3uter"));

//        item.setPriceForOne(BigDecimal.valueOf(1999.99));
//        hibItemDao.update(4L, item);
//
//        System.out.println(hibItemDao.allEntities());
//        hibItemDao.delete(3L);

//        System.out.println(hibItemDao.allEntities());

    }

    private static void removeAllCountries(OrmCountryDao ormCountryDao) throws Exception {
        Random random = ThreadLocalRandom.current();
        for (Country country : ormCountryDao.allEntities()) {
            int chance = random.nextInt(10);
            if (chance < 5) {
                ormCountryDao.deleteByName(country.getCountryName());
            } else {
                ormCountryDao.delete(country.getId());
            }
        }
    }

    private static void removeAllCurrencies(OrmCurrencyDao ormCurrencyDao) throws Exception {
        Random random = ThreadLocalRandom.current();
        for (Currency currency : ormCurrencyDao.allEntities()) {
            int chance = random.nextInt(10);
            if (chance < 5) {
                ormCurrencyDao.deleteByName(currency.getCurrencyName());
            } else {
                ormCurrencyDao.delete(currency.getId());
            }
        }
    }

    private static Collection<Country> testCountries() {
        Collection<Country> out = new HashSet<>();
        Country germany = new Country(1, "Germany");
        Country france = new Country(2, "France");
        Country belgium = new Country(3, "Belgium");
        Country britain = new Country(4, "Britain");
        Country usa = new Country(5, "USA");
        Country panama = new Country(6, "Panama");
        Country russia = new Country(7, "Russia");
        Country ukraine = new Country(8, "Ukraine");

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


}
