package dao.sql;

import dao.PhoneNumberDao;
import entity.PhoneNumber;

public interface SqlPhoneNumberDao extends PhoneNumberDao, IdIgnoreDao<PhoneNumber> {
}
