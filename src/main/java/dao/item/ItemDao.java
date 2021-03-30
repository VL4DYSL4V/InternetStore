package dao.item;

import dao.CrudDao;
import entity.Item;
import exception.dao.FetchException;

import java.util.Collection;

public interface ItemDao extends CrudDao<Long, Item> {

    Collection<Item> itemsWithSimilarName(String name) throws FetchException;

}
