package dao.analytic;

import dao.country.CountryDao;
import dao.currency.CurrencyDao;
import entity.Country;
import entity.Currency;
import exception.dao.FetchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@Repository
public final class MySQLAnalyticDAO implements AnalyticDAO{

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final DataSource dataSource;
    private final CurrencyDao currencyDao;
    private final CountryDao countryDao;

    @Autowired
    public MySQLAnalyticDAO(DataSource dataSource, CurrencyDao currencyDao, CountryDao countryDao) {
        this.dataSource = dataSource;
        this.currencyDao = currencyDao;
        this.countryDao = countryDao;
    }

    @Override
    public Collection<String> usersWhoGotReplyFromSeller(){
        String sql = "SELECT DISTINCT a_user.user_name AS u_name" +
                "    FROM a_user" +
                "    INNER JOIN comment" +
                "    ON a_user.user_id = comment.user_id" +
                "    AND comment_id IN (" +
                "                            /*ID OF COMMENTS, THAT WHERE REPLIED BY OWNERS OF ITEMS*/" +
                "            SELECT reply" +
                "            FROM comment" +
                "            INNER JOIN item" +
                "            ON reply IS NOT NULL" +
                "            AND comment.item_id = item.item_id" +
                "            AND comment.user_id = item.user_id)" +
                "    AND a_user.user_id <> (SELECT user_id FROM item WHERE item.item_id = comment.item_id)" +
                "    ORDER BY user_name DESC;";
        Collection<String> out = new LinkedList<>();
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()){
                out.add(resultSet.getString("u_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return out;
    }

    @Override
    public Map<Country, Collection<String>> countryToItemNames() throws FetchException {
        String sql = " SELECT country.country_id, item_name" +
                "    FROM country" +
                "    LEFT JOIN item i USING (country_id);";
        Map<Integer, Collection<String>> countryIdToItems = new HashMap<>();
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery()){
            while(resultSet.next()){
                Integer id = resultSet.getInt("country_id");
                Collection<String> items = countryIdToItems.computeIfAbsent(id, k -> new LinkedList<>());
                items.add(resultSet.getString("item_name"));
            }
        }catch (SQLException e){
            throw new FetchException(e);
        }
        Map<Country, Collection<String>> out = new HashMap<>();
        Collection<Country> countries = countryDao.allEntities();
        for (Country country: countries){
            Collection<String> itemNames = countryIdToItems.get(country.getId());
            if(itemNames != null){
                out.put(country, itemNames);
            }
        }
        return out;
    }

    @Override
    public long countUsersWhoPostedItemsBetweenDates(LocalDate start, LocalDate end) throws FetchException {
        String sql = "SELECT COUNT(DISTINCT(u.user_id)) AS user_amount" +
                "    FROM a_user u" +
                "    INNER JOIN item i" +
                "    ON u.user_id = i.user_id" +
                "    AND (i.put_up_for_sale BETWEEN CAST(? AS DATE) AND CAST(? AS DATE));";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, DATE_TIME_FORMATTER.format(start));
            preparedStatement.setString(2, DATE_TIME_FORMATTER.format(end));
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()) {
                    return resultSet.getLong("user_amount");
                }
            }
        } catch (SQLException e) {
            throw new FetchException(e);
        }
        return -1;
    }

//    Сопоставьте количества товаров с каждой валютой.
    @Override
    public Map<Currency, Integer> currencyToItemAmount() throws FetchException {
        String sql = "SELECT IF(item_id IS NULL, 0, COUNT(currency_name)) AS amount_of_items, currency.currency_id" +
                "    FROM item" +
                "    RIGHT JOIN currency USING (currency_id)" +
                "    GROUP BY currency_name" +
                "    ORDER BY amount_of_items;";
        Map<Integer, Integer> currencyIdToAmount = new HashMap<>();
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery()){
            while(resultSet.next()){
                currencyIdToAmount.put(resultSet.getInt("currency_id"),
                        resultSet.getInt("amount_of_items"));
           }
        }catch (SQLException e){
            throw new FetchException(e);
        }
        Map<Currency, Integer> out = new HashMap<>();
        Collection<Currency> currencies = currencyDao.allEntities();
        for (Currency currency: currencies){
            Integer amount = currencyIdToAmount.get(currency.getId());
            if(amount != null){
                out.put(currency, amount);
            }
        }
        return out;
    }

}
