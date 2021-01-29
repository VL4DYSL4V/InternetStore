package dao.currency;

import dao.common.CrudDao;
import entity.Currency;
import exception.FetchException;
import exception.StoreException;

public interface CurrencyDao extends CrudDao<Integer, Currency> {

     void insertIgnore(Currency currency) throws StoreException;

     Currency getByName(String name) throws FetchException;

}
