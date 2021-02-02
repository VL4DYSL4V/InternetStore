package dao.sql.mysql.user;

import dao.sql.SqlUserDao;
import entity.User;
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
import java.sql.SQLException;
import java.util.Collection;
import java.util.Objects;

@Component("mySqlUserDao")
public class MySqlUserDao implements SqlUserDao {

    private final DataSource dataSource;

    @Autowired
    public MySqlUserDao(@Qualifier("dataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User getById(Long id) throws FetchException {
        User out = null;
        String userSql = "SELECT user_id, user_name, user_password, email, phone_number_id, phone_number " +
                "FROM is_user INNER JOIN is_phone_number user_to_phone USING(phone_number_id) " +
                "WHERE user_id = ?;";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement userPreparedStatement = connection.prepareStatement(userSql)){
            connection.setAutoCommit(false);


        }catch (SQLException e){
            throw new FetchException(e);
        }
        return out;
    }

    @Override
    public Collection<User> allEntities() throws FetchException {
        String sql = "";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){

        }catch (SQLException e){
            throw new FetchException(e);
        }
        return null;
    }

    @Override
    public void save(User user) throws StoreException {
        String sql = "";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){

        }catch (SQLException e){
            throw new StoreException(e);
        }
    }

    @Override
    public void saveIgnoreId(User user) throws StoreException {
        String sql = "";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){

        }catch (SQLException e){
            throw new StoreException(e);
        }
    }

    @Override
    public void update(Long id, User user) throws UpdateException {
        String sql = "";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){

        }catch (SQLException e){
            throw new UpdateException(e);
        }
    }

    @Override
    public void delete(Long id) throws DeleteException {
        Objects.requireNonNull(id);
        String sql = "DELETE FROM is_user WHERE user_id = ?;";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new DeleteException(e);
        }
    }
}
