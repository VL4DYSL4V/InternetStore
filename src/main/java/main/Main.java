package main;

import config.InternetStoreConfiguration;
import dao.user.HibernateUserDao;
import dao.user.UserDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(InternetStoreConfiguration.class);
        UserDao hibUserDao =
                applicationContext.getBean("userDao", HibernateUserDao.class);

        System.exit(0);

    }

//    private static void removeAllCountries(OrmCountryDao ormCountryDao) throws Exception {
//        Random random = ThreadLocalRandom.current();
//        for (Country country : ormCountryDao.allEntities()) {
//            int chance = random.nextInt(10);
//            if (chance < 5) {
//                ormCountryDao.deleteByName(country.getCountryName());
//            } else {
//                ormCountryDao.delete(country.getId());
//            }
//        }
//    }
//
//    private static void removeAllCurrencies(OrmCurrencyDao ormCurrencyDao) throws Exception {
//        Random random = ThreadLocalRandom.current();
//        for (Currency currency : ormCurrencyDao.allEntities()) {
//            int chance = random.nextInt(10);
//            if (chance < 5) {
//                ormCurrencyDao.deleteByName(currency.getCurrencyName());
//            } else {
//                ormCurrencyDao.delete(currency.getId());
//            }
//        }
//    }

}
