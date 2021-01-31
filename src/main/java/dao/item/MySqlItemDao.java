package dao.item;

import dao.utils.SimilarNameCreator;
import entity.Currency;
import entity.Item;
import exception.dao.DeleteException;
import exception.dao.FetchException;
import exception.dao.StoreException;
import exception.dao.UpdateException;
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

@Repository("mySqlItemDao")
@NotThreadSafe
public final class MySqlItemDao implements ItemDao {

    private final DataSource dataSource;

    @Autowired
    public MySqlItemDao(@Qualifier("dataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Item getById(Long id) throws FetchException {
        Objects.requireNonNull(id);
        String sql = "SELECT item_name, amount, price_for_one, is_item.currency_id, " +
                "img_url, item_description, put_up_for_sale, currency_name" +
                " FROM is_item INNER JOIN is_currency ic ON item_id = ?" +
                " AND is_item.currency_id = ic.currency_id;";
        Item out = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    out = new Item(id,
                            rs.getString("item_name"),
                            rs.getInt("amount"),
                            rs.getBigDecimal("price_for_one"),
                            new Currency(rs.getInt("currency_id"), rs.getString("currency_name")),
                            rs.getString("img_url"),
                            rs.getString("item_description"),
                            rs.getDate("put_up_for_sale"));
                }
            }
        } catch (SQLException e) {
            throw new FetchException(e);
        }
        if (out == null) {
            throw new FetchException("No such item with id = " + id);
        }
        return out;
    }

    @Override
    public Collection<Item> allEntities() throws FetchException {
        String sql = "SELECT item_id, item_name, amount, price_for_one, currency_id, " +
                "img_url, item_description, put_up_for_sale, currency_name" +
                " FROM is_item INNER JOIN is_currency USING (currency_id);";
        Collection<Item> out = new HashSet<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    out.add(parseFullResultSet(rs));
                }
            }
        } catch (SQLException e) {
            throw new FetchException(e);
        }
        return out;
    }

    @Override
    public void save(Item item) throws StoreException {
        Objects.requireNonNull(item);
        Objects.requireNonNull(item.getCurrency());
        String sql = "INSERT INTO is_item(item_id, item_name," +
                " amount, price_for_one, " +
                "currency_id, img_url, " +
                "item_description, put_up_for_sale)" +
                " VALUES (?, ?, ?, ?, " +
                "(SELECT currency_id FROM is_currency WHERE BINARY currency_name = ?), ?, ?, ?);";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, item.getId());
            preparedStatement.setString(2, item.getName());
            preparedStatement.setInt(3, item.getAmount());
            preparedStatement.setBigDecimal(4, item.getPriceForOne());
            preparedStatement.setString(5, item.getCurrency().getCurrencyName());
            preparedStatement.setString(6, item.getImageUrl());
            preparedStatement.setString(7, item.getItemDescription());
            preparedStatement.setDate(8, item.getPutUpForSale());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new StoreException(e);
        }
    }

    @Override
    @SuppressWarnings("Duplicates")
    public void saveIgnoreId(Item item) throws StoreException {
        Objects.requireNonNull(item);
        Objects.requireNonNull(item.getCurrency());
        String sql = "INSERT INTO is_item(item_name," +
                " amount, price_for_one, " +
                "currency_id, img_url, " +
                "item_description, put_up_for_sale)" +
                " VALUES (?, ?, ?, " +
                "(SELECT currency_id FROM is_currency WHERE BINARY currency_name = ?), ?, ?, ?);";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, item.getName());
            preparedStatement.setInt(2, item.getAmount());
            preparedStatement.setBigDecimal(3, item.getPriceForOne());
            preparedStatement.setString(4, item.getCurrency().getCurrencyName());
            preparedStatement.setString(5, item.getImageUrl());
            preparedStatement.setString(6, item.getItemDescription());
            preparedStatement.setDate(7, item.getPutUpForSale());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new StoreException(e);
        }
    }

    @Override
    @SuppressWarnings("Duplicates")
    public void update(Long id, Item item) throws UpdateException {
        Objects.requireNonNull(id);
        Objects.requireNonNull(item);
        Objects.requireNonNull(item.getCurrency());
        String sql = "UPDATE is_item SET item_name = ?, amount = ?, price_for_one = ?," +
                " currency_id = (SELECT currency_id FROM is_currency WHERE BINARY currency_name = ?)," +
                " img_url = ?, item_description = ?, put_up_for_sale = ? " +
                "WHERE item_id = ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(8, id);
            preparedStatement.setString(1, item.getName());
            preparedStatement.setInt(2, item.getAmount());
            preparedStatement.setBigDecimal(3, item.getPriceForOne());
            preparedStatement.setString(4, item.getCurrency().getCurrencyName());
            preparedStatement.setString(5, item.getImageUrl());
            preparedStatement.setString(6, item.getItemDescription());
            preparedStatement.setDate(7, item.getPutUpForSale());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new UpdateException(e);
        }
    }

    @Override
    public void delete(Long id) throws DeleteException {
        Objects.requireNonNull(id);
        String sql = "DELETE FROM is_item WHERE item_id = ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DeleteException(e);
        }
    }

    @Override
    public Collection<Item> itemsWithSimilarName(String name) throws FetchException {
        Objects.requireNonNull(name);
        Collection<Item> out = new LinkedList<>();
        Collection<String> similarStrings = SimilarNameCreator.createSimilarStrings(name);
        String sql = "SELECT item_id, item_name, amount, price_for_one, is_item.currency_id, " +
                "img_url, item_description, put_up_for_sale " +
                "FROM is_item INNER JOIN is_currency ic" +
                " ON item_name LIKE ? AND is_item.currency_id = ic.currency_id;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (String similar : similarStrings) {
                preparedStatement.setString(1, similar);
                try(ResultSet rs = preparedStatement.executeQuery()){
                    while(rs.next()){
                        out.add(parseFullResultSet(rs));
                    }
                }
            }
        } catch (SQLException e) {
            throw new FetchException(e);
        }
        return out;
    }

    private Item parseFullResultSet(ResultSet rs) throws SQLException {
        return new Item(rs.getLong("item_id"),
                rs.getString("item_name"),
                rs.getInt("amount"),
                rs.getBigDecimal("price_for_one"),
                new Currency(rs.getInt("currency_id"), rs.getString("currency_name")),
                rs.getString("img_url"),
                rs.getString("item_description"),
                rs.getDate("put_up_for_sale"));
    }

}
