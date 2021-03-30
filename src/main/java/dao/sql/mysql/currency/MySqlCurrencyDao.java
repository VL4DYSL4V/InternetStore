package dao.sql.mysql.currency;

import dao.sql.SqlCurrencyDao;
import entity.Currency;
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
import java.util.HashSet;
import java.util.Objects;

@Component("mySqlCurrencyDao")
public final class MySqlCurrencyDao implements SqlCurrencyDao {

    private final DataSource dataSource;

    @Autowired
    public MySqlCurrencyDao(@Qualifier("dataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Currency getById(Integer id) throws FetchException {
        Objects.requireNonNull(id);
        String sql = "SELECT currency_name FROM currency WHERE currency_id = ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String currency_name = resultSet.getString("currency_name");
                    return new Currency(id, currency_name);
                }
                throw new FetchException("No such currency with id = " + id);
            }
        } catch (SQLException e) {
            throw new FetchException(e);
        }
    }

    @Override
    public Collection<Currency> allEntities() throws FetchException {
        String sql = "SELECT currency_id, currency_name FROM currency;";
        Collection<Currency> out = new HashSet<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int currency_id = resultSet.getInt("currency_id");
                    String currency_name = resultSet.getString("currency_name");
                    out.add(new Currency(currency_id, currency_name));
                }
            }
        } catch (SQLException e) {
            throw new FetchException(e);
        }
        return out;
    }

    @Override
    public void save(Currency currency) throws StoreException {
        Objects.requireNonNull(currency);
        String sql = "INSERT INTO currency(currency_id, currency_name) VALUES(?, ?);";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, currency.getId());
            preparedStatement.setString(2, currency.getCurrencyName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new StoreException(e);
        }
    }

    @Override
    public void saveIgnoreId(Currency currency) throws StoreException {
        Objects.requireNonNull(currency);
        String sql = "INSERT INTO currency(currency_name) VALUES(?);";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, currency.getCurrencyName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new StoreException(e);
        }
    }

    @Override
    public void update(Integer id, Currency currency) throws UpdateException {
        Objects.requireNonNull(id);
        Objects.requireNonNull(currency);
        String sql = "UPDATE currency SET currency_name = ? WHERE currency_id = ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, currency.getCurrencyName());
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new UpdateException(e);
        }
    }

    @Override
    public void delete(Integer id) throws DeleteException {
        Objects.requireNonNull(id);
        String sql = "DELETE FROM currency WHERE currency_id = ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DeleteException(e);
        }
    }

    @Override
    public Currency getByName(String name) throws FetchException {
        Objects.requireNonNull(name);
        String sql = "SELECT currency_id FROM currency WHERE BINARY currency_name = ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return getById(resultSet.getInt("currency_id"));
                }
                throw new FetchException("No such currency!");
            }
        } catch (SQLException e) {
            throw new FetchException(e);
        }
    }

    @Override
    public void deleteByName(String name) throws DeleteException {
        Objects.requireNonNull(name);
        String sql = "DELETE FROM currency WHERE BINARY currency_name = ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DeleteException(e);
        }
    }

    @Override
    public void saveAll(Collection<Currency> currencies) throws StoreException {
        Objects.requireNonNull(currencies);
        String sql = "INSERT INTO currency(currency_id, currency_name) VALUES(?, ?);";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            try{
                for (Currency currency : currencies) {
                    Objects.requireNonNull(currency);
                    preparedStatement.setInt(1, currency.getId());
                    preparedStatement.setString(2, currency.getCurrencyName());
                    preparedStatement.executeUpdate();
                }
            }catch (Throwable t){
                connection.rollback();
                throw new StoreException(t);
            }
            connection.commit();
        } catch (SQLException e) {
            throw new StoreException(e);
        }
    }

    @Override
    public void saveAllIgnoreId(Collection<Currency> currencies) throws StoreException {
        Objects.requireNonNull(currencies);
        String sql = "INSERT INTO currency(currency_name) VALUES(?);";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            try{
                for (Currency currency : currencies) {
                    Objects.requireNonNull(currency);
                    preparedStatement.setString(1, currency.getCurrencyName());
                    preparedStatement.executeUpdate();
                }
            }catch (Throwable t){
                connection.rollback();
                throw new StoreException(t);
            }
            connection.commit();
        } catch (SQLException e) {
            throw new StoreException(e);
        }
    }
}
