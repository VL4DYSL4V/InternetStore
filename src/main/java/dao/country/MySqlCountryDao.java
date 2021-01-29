package dao.country;

import dao.currency.CurrencyDao;
import entity.Country;
import entity.Currency;
import exception.DeleteException;
import exception.FetchException;
import exception.StoreException;
import exception.UpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.annotation.concurrent.NotThreadSafe;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository("mySqlCountryDao")
@NotThreadSafe
public final class MySqlCountryDao implements CountryDao {

    private final DataSource dataSource;
    private final CurrencyDao currencyDao;

    @Autowired
    public MySqlCountryDao(@Qualifier("dataSource") DataSource dataSource,
                           @Qualifier("hibernateCurrencyDao") CurrencyDao currencyDao) {
        this.dataSource = dataSource;
        this.currencyDao = currencyDao;
    }

    @Override
    public Country getById(Integer id) throws FetchException {
        Objects.requireNonNull(id);
        String sql = "SELECT country_id, country_name, is_currency.currency_id, currency_name FROM " +
                "is_country INNER JOIN is_country_to_currency USING(country_id) " +
                "INNER JOIN is_currency USING(currency_id) " +
                "WHERE country_id = ?;";
        Country country = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    if (country == null) {
                        country = new Country();
                    }
                    if (!Objects.equals(id, 0) && Objects.equals(country.getId(), 0)) {
                        country.setId(resultSet.getInt("country_id"));
                    }
                    if (country.getCountryName() == null) {
                        country.setCountryName(resultSet.getString("country_name"));
                    }

                    Currency currency = new Currency(resultSet.getInt("currency_id"),
                            resultSet.getString("currency_name"));
                    country.addCurrency(currency);
                }
            }
        } catch (SQLException e) {
            throw new FetchException(e);
        }
        if(country == null){
            throw new FetchException("No such country with id = " + id);
        }
        return country;
    }

    @Override
    public Collection<Country> allEntities() throws FetchException {
        String fetchCountrySql = "SELECT is_country.country_id, country_name, currency_id FROM is_country " +
                "INNER JOIN is_country_to_currency USING (country_id);";
        Map<Integer, Country> outValuesHolder = new HashMap<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement countryPrepStatement = connection.prepareStatement(fetchCountrySql)) {
            try(ResultSet resultSet = countryPrepStatement.executeQuery()){
                while(resultSet.next()){
                    Integer countryId= resultSet.getInt("country_id");
                    String countryName = resultSet.getString("country_name");
                    Country country = outValuesHolder.get(countryId);
                    if(country == null){
                        country = new Country();
                        country.setId(countryId);
                        country.setCountryName(countryName);
                        outValuesHolder.put(countryId, country);
                    }
                    Currency currency = currencyDao.getById(resultSet.getInt("currency_id"));
                    country.addCurrency(currency);
                }
            }
        } catch (SQLException e) {
            throw new FetchException(e);
        }
        return outValuesHolder.values();
    }

    @Override
    public void save(Country country) throws StoreException {
        Objects.requireNonNull(country);
        String saveCountrySql = "INSERT INTO is_country (country_id, country_name) VALUES (?, ?);";
        String saveCountryToCurrency = "INSERT INTO is_country_to_currency(country_id, currency_id) VALUES (?, ?);";
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement countryPreparedStatement = connection.prepareStatement(saveCountrySql);
                 PreparedStatement countryToCurrencyPreparedStatement = connection.prepareStatement(saveCountryToCurrency)) {
                countryPreparedStatement.setInt(1, country.getId());
                countryPreparedStatement.setString(2, country.getCountryName());
                countryPreparedStatement.executeUpdate();
                for (Currency currency : country.getCurrencies()) {
                    countryToCurrencyPreparedStatement.setInt(1, country.getId());
                    countryToCurrencyPreparedStatement.setInt(2, currency.getId());
                    countryToCurrencyPreparedStatement.executeUpdate();
                    currencyDao.insertIgnore(currency);
                }
            } catch (SQLException | StoreException e) {
                connection.rollback();
                throw new StoreException(e);
            }

            connection.commit();
        } catch (SQLException e) {
            throw new StoreException(e);
        }
    }

    @Override
    public void update(Integer id, Country country) throws UpdateException {
        Objects.requireNonNull(id);
        Objects.requireNonNull(country);
        String updateNameSql = "UPDATE is_country SET country_name = ? WHERE country_id = ?;";
        String deleteCountryToCurrency = "DELETE FROM is_country_to_currency WHERE country_id = ?;";
        String saveCountryToCurrency = "INSERT INTO is_country_to_currency(country_id, currency_id) VALUES (?, ?);";
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement updateNamePreparedStatement = connection.prepareStatement(updateNameSql);
                PreparedStatement deleteCountryToCurrencyPreparedStatement = connection.prepareStatement(deleteCountryToCurrency);
                PreparedStatement saveCountryToCurrencyPreparedStatement = connection.prepareStatement(saveCountryToCurrency)) {
                updateNamePreparedStatement.setString(1, country.getCountryName());
                updateNamePreparedStatement.setInt(2, id);
                updateNamePreparedStatement.executeUpdate();

                deleteCountryToCurrencyPreparedStatement.setInt(1, id);
                deleteCountryToCurrencyPreparedStatement.executeUpdate();

                for (Currency currency : country.getCurrencies()) {
                    saveCountryToCurrencyPreparedStatement.setInt(1, id);
                    saveCountryToCurrencyPreparedStatement.setInt(2, currency.getId());
                    saveCountryToCurrencyPreparedStatement.executeUpdate();
                }
            }catch (Throwable t){
                connection.rollback();
                throw new UpdateException(t);
            }

            connection.commit();
        } catch (SQLException e) {
            throw new UpdateException(e);
        }
    }

    @Override
    public void delete(Integer id) throws DeleteException {
        Objects.requireNonNull(id);
        String sql = "DELETE FROM is_country WHERE country_id = ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DeleteException(e);
        }
    }

    @Override
    public Country getByName(String countryName) throws FetchException{
        Objects.requireNonNull(countryName);
        String sql = "SELECT country_id FROM is_country WHERE country_name = ?;";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement getIdPrepStatement = connection.prepareStatement(sql)){
            getIdPrepStatement.setString(1, countryName);

            try(ResultSet resultSet = getIdPrepStatement.executeQuery()){
                if (resultSet.next()){
                    Integer id = resultSet.getInt("country_id");
                    return getById(id);
                }
                throw new FetchException("No such country with name = " + countryName);
            }
        }catch (SQLException e){
            throw new FetchException(e);
        }
    }


}
