package service.encryption;

import exception.encryption.EncryptionException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface EncryptionService {

    String getEncrypted(String password) throws EncryptionException;

}
