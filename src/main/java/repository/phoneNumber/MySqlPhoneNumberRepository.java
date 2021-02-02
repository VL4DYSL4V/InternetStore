package repository.phoneNumber;

import dao.sql.SqlPhoneNumberDao;
import entity.PhoneNumber;
import exception.dao.DeleteException;
import exception.dao.FetchException;
import exception.dao.StoreException;
import exception.dao.UpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("mySqlPhoneNumberRepository")
public final class MySqlPhoneNumberRepository implements PhoneNumberRepository{

    private final SqlPhoneNumberDao phoneNumberDao;

    @Autowired
    public MySqlPhoneNumberRepository(@Qualifier("mySqlPhoneNumberDao") SqlPhoneNumberDao phoneNumberDao) {
        this.phoneNumberDao = phoneNumberDao;
    }

    @Override
    public PhoneNumber get(Long id) throws FetchException {
        return phoneNumberDao.getById(id);
    }

    @Override
    public void add(PhoneNumber phoneNumber) throws StoreException {
        if(phoneNumber.getId() != null) {
            phoneNumberDao.save(phoneNumber);
        }else{
            phoneNumberDao.saveIgnoreId(phoneNumber);
        }
    }

    @Override
    public void update(PhoneNumber phoneNumber) throws UpdateException {
        phoneNumberDao.update(phoneNumber.getId(), phoneNumber);
    }

    @Override
    public void remove(PhoneNumber phoneNumber) throws DeleteException {
        phoneNumberDao.delete(phoneNumber.getId());
    }
}
