package dao.sql;

import dao.UserDao;
import entity.User;

public interface SqlUserDao extends UserDao, IdIgnoreDao<User> {
}
