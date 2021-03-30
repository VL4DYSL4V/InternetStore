package dao.sql.mysql.country;

import dao.sql.SqlCountryDao;
import entity.Country;
import exception.dao.DeleteException;
import exception.dao.FetchException;
import exception.dao.StoreException;
import exception.dao.UpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;

@Component("mySqlCountryDao")
@SuppressWarnings("Duplicates")
public final class MySqlCountryDao implements SqlCountryDao {

    private final DataSource dataSource;

    @Autowired
    public MySqlCountryDao(@Qualifier("dataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Country getById(Integer id) throws FetchException {
        Objects.requireNonNull(id);
        String sql = "SELECT country_name FROM country WHERE country_id = ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Country country = new Country();
                    country.setId(id);
                    country.setCountryName(resultSet.getString("country_name"));
                    return country;
                }
                throw new FetchException("No such country with id = " + id);
            }
        } catch (SQLException e) {
            throw new FetchException(e);
        }
    }

    @Override
    public Collection<Country> allEntities() throws FetchException {
        String fetchCountrySql = "SELECT country.country_id, country_name FROM country;";
        Collection<Country> out = new LinkedList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement countryPrepStatement = connection.prepareStatement(fetchCountrySql)) {
            try (ResultSet resultSet = countryPrepStatement.executeQuery()) {
                while (resultSet.next()) {
                    Integer countryId = resultSet.getInt("country_id");
                    String countryName = resultSet.getString("country_name");
                    out.add(new Country(countryId, countryName));
                }
            }
        } catch (SQLException e) {
            throw new FetchException(e);
        }
        return out;
    }

    @Override
    public void save(Country country) throws StoreException {
        Objects.requireNonNull(country);
        String saveCountrySql = "INSERT INTO country (country_id, country_name) VALUES (?, ?);";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement countryPreparedStatement = connection.prepareStatement(saveCountrySql)) {
            countryPreparedStatement.setInt(1, country.getId());
            countryPreparedStatement.setString(2, country.getCountryName());
            countryPreparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new StoreException(e);
        }
    }

    @Override
    public void saveIgnoreId(Country country) throws StoreException {
        Objects.requireNonNull(country);
        String saveCountrySql = "INSERT INTO country (country_name) VALUES (?);";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement countryPreparedStatement = connection.prepareStatement(saveCountrySql)) {
            countryPreparedStatement.setString(1, country.getCountryName());
            countryPreparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new StoreException(e);
        }
    }

    @Override
    public void update(Integer id, Country country) throws UpdateException {
        Objects.requireNonNull(id);
        Objects.requireNonNull(country);
        String updateNameSql = "UPDATE country SET country_name = ? WHERE country_id = ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement updateNamePreparedStatement = connection.prepareStatement(updateNameSql)) {
            updateNamePreparedStatement.setString(1, country.getCountryName());
            updateNamePreparedStatement.setInt(2, id);
            updateNamePreparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new UpdateException(e);
        }
    }

    @Override
    public void delete(Integer id) throws DeleteException {
        Objects.requireNonNull(id);
        String sql = "DELETE FROM country WHERE country_id = ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DeleteException(e);
        }
    }

    @Override
    public Country getByName(String countryName) throws FetchException {
        Objects.requireNonNull(countryName);
        String sql = "SELECT country_id FROM country WHERE BINARY country_name = ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement getIdPrepStatement = connection.prepareStatement(sql)) {
            getIdPrepStatement.setString(1, countryName);

            try (ResultSet resultSet = getIdPrepStatement.executeQuery()) {
                if (resultSet.next()) {
                    Integer id = resultSet.getInt("country_id");
                    return getById(id);
                }
                throw new FetchException("No such country with name = " + countryName);
            }
        } catch (SQLException e) {
            throw new FetchException(e);
        }
    }

    @Override
    public void deleteByName(String countryName) throws DeleteException {
        Objects.requireNonNull(countryName);
        String sql = "DELETE FROM country WHERE BINARY country_name = ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement getIdPrepStatement = connection.prepareStatement(sql)) {
            getIdPrepStatement.setString(1, countryName);
            getIdPrepStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DeleteException(e);
        }
    }

    @Override
    public void saveAll(Collection<Country> countries) throws StoreException {
        Objects.requireNonNull(countries);
        String saveCountrySql = "INSERT INTO country (country_id, country_name) VALUES (?, ?);";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement countryPreparedStatement = connection.prepareStatement(saveCountrySql)) {
            connection.setAutoCommit(false);
            try {
                for (Country country : countries) {
                    Objects.requireNonNull(country);
                    countryPreparedStatement.setInt(1, country.getId());
                    countryPreparedStatement.setString(2, country.getCountryName());
                    countryPreparedStatement.executeUpdate();
                }
            } catch (Throwable t) {
                connection.rollback();
                throw new StoreException(t);
            }
            connection.commit();
        } catch (SQLException e) {
            throw new StoreException(e);
        }
    }

    @Override
    public void saveAllIgnoreId(Collection<Country> countries) throws StoreException {
        Objects.requireNonNull(countries);
        String saveCountrySql = "INSERT INTO country (country_name) VALUES (?);";
       try (Connection connection = dataSource.getConnection();
            PreparedStatement countryPreparedStatement = connection.prepareStatement(saveCountrySql)) {
            connection.setAutoCommit(false);
            try{
                for (Country country : countries) {
                    countryPreparedStatement.setString(1, country.getCountryName());
                    countryPreparedStatement.executeUpdate();
                }
            } catch (Throwable t) {
                connection.rollback();
                throw new StoreException(t);
            }
            connection.commit();
        } catch (SQLException e) {
            throw new StoreException(e);
        }
    }
}
