package repository.item;

import entity.Item;
import exception.dao.DeleteException;
import exception.dao.FetchException;
import exception.dao.StoreException;
import exception.dao.UpdateException;

import java.util.Collection;

public interface ItemRepository {

    Item getById(Long id) throws FetchException;

    void add(Item item) throws StoreException;

    void update(Long id, Item item) throws UpdateException;

    void remove(Long id) throws DeleteException;

    Collection<Item> itemsWithSimilarName(String name) throws FetchException;

}
