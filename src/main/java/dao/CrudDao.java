package dao;

import exception.dao.DeleteException;
import exception.dao.FetchException;
import exception.dao.StoreException;
import exception.dao.UpdateException;

import java.util.Collection;

public interface CrudDao<Key, Entity> {

    Entity getById(Key id) throws FetchException;

    Collection<Entity> allEntities() throws FetchException;

    void save(Entity entity) throws StoreException;

    void update(Key id, Entity entity) throws UpdateException;

    void delete(Key id) throws DeleteException;

}
