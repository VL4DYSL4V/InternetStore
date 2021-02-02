package dao.sql;

import exception.dao.StoreException;


public interface IdIgnoreDao<Entity> {

    void saveIgnoreId(Entity entity) throws StoreException;

}
