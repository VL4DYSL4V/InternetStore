package dao.sql.mysql.phoneNumber;

import dao.sql.SqlPhoneNumberDao;
import entity.PhoneNumber;
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

@Component("mySqlPhoneNumberDao")
public class MySqlPhoneNumberDao implements SqlPhoneNumberDao {

    private final DataSource dataSource;

    @Autowired
    public MySqlPhoneNumberDao(@Qualifier("dataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public PhoneNumber getById(Long id) throws FetchException {
        Objects.requireNonNull(id);
        String sql = "SELECT phone_number_id, phone_number FROM is_phone_number WHERE phone_number_id = ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    long phone_number_id = resultSet.getLong("phone_number_id");
                    String phone_number = resultSet.getString("phone_number");
                    return new PhoneNumber(phone_number_id, phone_number);
                }
                throw new FetchException("No such phone number with id = " + id);
            }
        } catch (SQLException e) {
            throw new FetchException(e);
        }
    }

    @Override
    public Collection<PhoneNumber> allEntities() throws FetchException {
        String sql = "SELECT phone_number_id, phone_number FROM is_phone_number;";
        Collection<PhoneNumber> out = new HashSet<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    long phone_number_id = resultSet.getLong("phone_number_id");
                    String phone_number = resultSet.getString("phone_number");
                    out.add(new PhoneNumber(phone_number_id, phone_number));
                }
            }
        } catch (SQLException e) {
            throw new FetchException(e);
        }
        return out;
    }

    @Override
    public void save(PhoneNumber phoneNumber) throws StoreException {
        Objects.requireNonNull(phoneNumber);
        String sql = "INSERT INTO is_phone_number(phone_number_id, phone_number) VALUES(?, ?);";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, phoneNumber.getId());
            preparedStatement.setString(2, phoneNumber.getNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new StoreException(e);
        }
    }

    @Override
    public void saveIgnoreId(PhoneNumber phoneNumber) throws StoreException {
        Objects.requireNonNull(phoneNumber);
        String sql = "INSERT INTO is_phone_number(phone_number) VALUES(?);";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, phoneNumber.getNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new StoreException(e);
        }
    }

    @Override
    public void update(Long id, PhoneNumber phoneNumber) throws UpdateException {
        Objects.requireNonNull(id);
        Objects.requireNonNull(phoneNumber);
        String sql = "UPDATE is_phone_number SET phone_number = ? WHERE phone_number_id = ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, phoneNumber.getNumber());
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new UpdateException(e);
        }
    }

    @Override
    public void delete(Long id) throws DeleteException {
        Objects.requireNonNull(id);
        String sql = "DELETE FROM is_phone_number WHERE phone_number_id = ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DeleteException(e);
        }
    }
}
