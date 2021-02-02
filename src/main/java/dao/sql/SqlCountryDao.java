package dao.sql;

import dao.CountryDao;
import entity.Country;
import exception.dao.StoreException;

import java.util.Collection;

public interface SqlCountryDao extends CountryDao, IdIgnoreDao<Country> {

    void saveAllIgnoreId(Collection<Country> entities) throws StoreException;

}
