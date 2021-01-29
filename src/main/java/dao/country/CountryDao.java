package dao.country;

import dao.common.CrudDao;
import entity.Country;
import exception.FetchException;

public interface CountryDao extends CrudDao<Integer, Country> {

    Country getByName(String countryName) throws FetchException;

}
