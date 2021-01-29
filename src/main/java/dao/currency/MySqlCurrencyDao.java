package dao.currency;

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
import java.util.Collection;
import java.util.HashSet;

@Repository("mySqlCurrencyDao")
@NotThreadSafe
public final class MySqlCurrencyDao implements CurrencyDao {

    private final DataSource dataSource;

    @Autowired
    public MySqlCurrencyDao(@Qualifier("dataSource") DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public Currency getById(Integer id) throws FetchException {
        String sql = "SELECT currency_id, currency_name FROM is_currency WHERE currency_id = ?;";
        Currency out = null;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    int currency_id = resultSet.getInt("currency_id");
                    String currency_name = resultSet.getString("currency_name");
                    out = new Currency(currency_id, currency_name);
                }
            }
        } catch (SQLException e) {
            throw new FetchException(e);
        }
        if(out == null){
            throw new FetchException("No such currency with id = " + id);
        }
        return out;
    }

    @Override
    public Collection<Currency> allEntities() throws FetchException {
        String sql = "SELECT currency_id, currency_name FROM is_currency;";
        Collection<Currency> out = new HashSet<>();
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
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
        String sql = "INSERT INTO is_currency(currency_id, currency_name) VALUES(?, ?);";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, currency.getId());
            preparedStatement.setString(2, currency.getCurrencyName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new StoreException(e);
        }
    }

    @Override
    public void update(Integer id, Currency currency) throws UpdateException {
        String sql = "UPDATE is_currency SET currency_name = ? WHERE currency_id = ?;";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, currency.getCurrencyName());
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new UpdateException(e);
        }
    }

    @Override
    public void delete(Integer id) throws DeleteException {
        String sql = "DELETE FROM is_currency WHERE currency_id = ?;";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DeleteException(e);
        }
    }

    @Override
    public void insertIgnore(Currency currency) throws StoreException {
        String sql = "INSERT IGNORE INTO is_currency(currency_id, currency_name) VALUES(?, ?);";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, currency.getId());
            preparedStatement.setString(2, currency.getCurrencyName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new StoreException(e);
        }
    }

    @Override
    public Currency getByName(String name) throws FetchException {
        String sql = "SELECT currency_id FROM is_currency WHERE currency_name = ?;";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, name);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    return getById(resultSet.getInt("currency_id"));
                }
                throw new FetchException("No such currency!");
            }
        } catch (SQLException e) {
            throw new FetchException(e);
        }
    }
}
