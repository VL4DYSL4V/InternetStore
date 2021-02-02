package dao.sql;

import dao.CurrencyDao;
import entity.Currency;
import exception.dao.StoreException;

import java.util.Collection;

public interface SqlCurrencyDao extends CurrencyDao, IdIgnoreDao<Currency> {

    void saveAllIgnoreId(Collection<Currency> entities) throws StoreException;

}
