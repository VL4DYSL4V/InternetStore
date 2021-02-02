package dao;

import entity.Currency;
import exception.dao.DeleteException;
import exception.dao.FetchException;
import exception.dao.StoreException;

import java.util.Collection;

public interface CurrencyDao extends CrudDao<Integer, Currency> {

    Currency getByName(String name) throws FetchException;

    void deleteByName(String name) throws DeleteException;

    void saveAll(Collection<Currency> currencies) throws StoreException;

}
