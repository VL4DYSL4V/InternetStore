package main;

import config.InternetStoreConfiguration;
import dao.orm.OrmCountryDao;
import dao.orm.OrmCurrencyDao;
import dao.orm.OrmUserDao;
import dao.orm.hibernate.user.HibernateUserDao;
import entity.Country;
import entity.Currency;
import entity.User;
import filler.UserTestDataFiller;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(InternetStoreConfiguration.class);
//        OrmCurrencyDao hibCurrencyDao =
//                applicationContext.getBean("hibernateCurrencyDao", HibernateCurrencyDao.class);
//        OrmCountryDao hibCountryDao =
//                applicationContext.getBean("hibernateCountryDao", HibernateCountryDao.class);
//        OrmItemDao hibItemDao =
//                applicationContext.getBean("hibernateItemDao", HibernateItemDao.class);
          OrmUserDao hibUserDao =
                applicationContext.getBean("hibernateUserDao", HibernateUserDao.class);
//        OrmCommentDao hibCommentDao =
//                applicationContext.getBean("hibernateCommentDao", HibernateCommentDao.class);

//        try(Session session = HibernateUtils.openSession()){
//            Transaction transaction = session.beginTransaction();
//            User user = session.get(User.class, 1L);
//            System.out.println(user);
//            transaction.commit();
//        }

//        UserTestDataFiller userTestDataFiller = new UserTestDataFiller(hibUserDao);
//        userTestDataFiller.fillTable();
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
