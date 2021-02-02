package dao.sql;

import dao.ItemDao;
import entity.Item;

public interface SqlItemDao extends ItemDao, IdIgnoreDao<Item> {

}
