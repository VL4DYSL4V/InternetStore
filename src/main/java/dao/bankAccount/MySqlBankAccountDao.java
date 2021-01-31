package dao.bankAccount;

import entity.BankAccount;
import entity.Currency;
import exception.dao.DeleteException;
import exception.dao.FetchException;
import exception.dao.StoreException;
import exception.dao.UpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.annotation.concurrent.NotThreadSafe;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Repository("mySqlBankAccountDao")
@NotThreadSafe
public final class MySqlBankAccountDao implements BankAccountDao {

    private final DataSource dataSource;

    @Autowired
    public MySqlBankAccountDao(@Qualifier("dataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public BankAccount getById(Long id) throws FetchException {
        Objects.requireNonNull(id);
        String sql = "SELECT funds, is_bank_account.currency_id, currency_name " +
                "FROM is_bank_account INNER JOIN is_currency ic ON bank_account_id = ? AND is_bank_account.currency_id = ic.currency_id;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    BigDecimal funds = rs.getBigDecimal("funds");
                    Currency currency = new Currency(rs.getInt("currency_id"), rs.getString("currency_name"));
                    return new BankAccount(id, funds, currency);
                }
                throw new FetchException("Bank account with id = " + id + " doesn't exist");
            }
        } catch (SQLException e) {
            throw new FetchException(e);
        }
    }

    @Override
    public Collection<BankAccount> allEntities() throws FetchException {
        Collection<BankAccount> out = new HashSet<>();
        String sql = "SELECT bank_account_id, funds, is_bank_account.currency_id, currency_name " +
                "FROM is_bank_account INNER JOIN is_currency USING (currency_id);";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Long id = resultSet.getLong("bank_account_id");
                    BigDecimal funds = resultSet.getBigDecimal("funds");
                    int currencyId = resultSet.getInt("currency_id");
                    String currencyName = resultSet.getString("currency_name");
                    out.add(new BankAccount(id, funds, new Currency(currencyId, currencyName)));
                }
            }
        } catch (SQLException e) {
            throw new FetchException(e);
        }
        return out;
    }

    @Override
    public void save(BankAccount bankAccount) throws StoreException {
        Objects.requireNonNull(bankAccount);
        String sql = "INSERT INTO is_bank_account(bank_account_id, funds, currency_id) " +
                "VALUES (?, ?, (SELECT currency_id FROM is_currency WHERE BINARY currency_name = ?));";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, bankAccount.getId());
            preparedStatement.setBigDecimal(2, bankAccount.getFunds());
            preparedStatement.setString(3, bankAccount.getCurrency().getCurrencyName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new StoreException(e);
        }
    }

    @Override
    public void saveIgnoreId(BankAccount bankAccount) throws StoreException {
        Objects.requireNonNull(bankAccount);
        Objects.requireNonNull(bankAccount.getCurrency());
        String sql = "INSERT INTO is_bank_account(funds, currency_id)" +
                " VALUES (?, (SELECT currency_id FROM is_currency WHERE BINARY currency_name = ?));";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setBigDecimal(1, bankAccount.getFunds());
            preparedStatement.setString(2, bankAccount.getCurrency().getCurrencyName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new StoreException(e);
        }
    }

    @Override
    public void update(Long id, BankAccount bankAccount) throws UpdateException {
        Objects.requireNonNull(id);
        Objects.requireNonNull(bankAccount);
        String sql = "UPDATE is_bank_account SET funds = ? WHERE bank_account_id = ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setBigDecimal(1, bankAccount.getFunds());
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new UpdateException(e);
        }
    }

    @Override
    public void delete(Long id) throws DeleteException {
        Objects.requireNonNull(id);
        String sql = "DELETE FROM is_bank_account WHERE bank_account_id = ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DeleteException(e);
        }

    }
}
