package repository.phoneNumber;

import entity.PhoneNumber;
import exception.dao.DeleteException;
import exception.dao.FetchException;
import exception.dao.StoreException;
import exception.dao.UpdateException;

public interface PhoneNumberRepository {

    PhoneNumber get(Long id) throws FetchException;

    void add(PhoneNumber phoneNumber) throws StoreException;

    void update(PhoneNumber phoneNumber) throws UpdateException;

    void remove(PhoneNumber phoneNumber) throws DeleteException;

}
