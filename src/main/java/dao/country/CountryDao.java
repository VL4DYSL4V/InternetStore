package dao.country;

import dao.CrudDao;
import entity.Country;
import exception.dao.DeleteException;
import exception.dao.FetchException;
import exception.dao.StoreException;

import java.util.Collection;

public interface CountryDao extends CrudDao<Integer, Country> {

    Country getByName(String countryName) throws FetchException;

    void deleteByName(String countryName) throws DeleteException;

    void saveAll(Collection<Country> countries) throws StoreException;

}
