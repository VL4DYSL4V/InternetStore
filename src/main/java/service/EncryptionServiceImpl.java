package service;

import exception.encryption.EncryptionException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

@Service
public final class EncryptionServiceImpl implements EncryptionService {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    @Override
    public byte[] getEncrypted(String password) throws EncryptionException {
        byte[] salt = new byte[16];
        SECURE_RANDOM.nextBytes(salt);
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        try {
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return secretKeyFactory.generateSecret(keySpec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new EncryptionException(e);
        }
    }

}
