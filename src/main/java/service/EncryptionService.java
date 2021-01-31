package service;

import exception.encryption.EncryptionException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface EncryptionService {

    byte[] getEncrypted(String password) throws EncryptionException;

}
