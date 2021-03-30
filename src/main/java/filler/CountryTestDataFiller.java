package filler;

import dao.orm.OrmCountryDao;
import entity.Country;
import exception.dao.StoreException;

import java.util.Collection;
import java.util.HashSet;

public final class CountryTestDataFiller {

    private final OrmCountryDao countryDao;

    public CountryTestDataFiller(OrmCountryDao countryDao) {
        this.countryDao = countryDao;
    }

    public void fillTable() throws StoreException {
        countryDao.saveAll(testCountries());
    }

    private Collection<Country> testCountries() {
        Collection<Country> out = new HashSet<>();
        Country germany = new Country(1, "Germany");
        Country france = new Country(2, "France");
        Country belgium = new Country(3, "Belgium");
        Country britain = new Country(4, "Britain");
        Country usa = new Country(5, "USA");
        Country panama = new Country(6, "Panama");
        Country russia = new Country(7, "Russia");
        Country ukraine = new Country(8, "Ukraine");

        out.add(germany);
        out.add(france);
        out.add(belgium);
        out.add(britain);
        out.add(usa);
        out.add(panama);
        out.add(russia);
        out.add(ukraine);
        return out;
    }

}
