package dao.country;

import dao.common.CrudDao;
import entity.Country;
import exception.dao.DeleteException;
import exception.dao.FetchException;

public interface CountryDao extends CrudDao<Integer, Country> {

    Country getByName(String countryName) throws FetchException;

    void deleteByName(String countryName) throws DeleteException;

}
