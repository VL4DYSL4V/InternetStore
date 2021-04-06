package dao.user;

import dao.CrudDao;
import entity.User;
import exception.dao.FetchException;

public interface UserDao extends CrudDao<Long, User> {

    User getByName(String name) throws FetchException;

}
