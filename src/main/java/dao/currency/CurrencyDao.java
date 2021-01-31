package dao.currency;

import dao.common.CrudDao;
import entity.Currency;
import exception.dao.DeleteException;
import exception.dao.FetchException;
import exception.dao.StoreException;

public interface CurrencyDao extends CrudDao<Integer, Currency> {

    void insertIgnore(Currency currency) throws StoreException;

    Currency getByName(String name) throws FetchException;

    void deleteByName(String name) throws DeleteException;
}
