package service.encryption;

import exception.encryption.EncryptionException;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public final class EncryptionServiceImpl implements EncryptionService {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    @Override
    public String getEncrypted(String password) throws EncryptionException {
//        byte[] salt = new byte[16];
//        SECURE_RANDOM.nextBytes(salt);
//        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
//        try {
//            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//            return secretKeyFactory.generateSecret(keySpec).getEncoded();
//        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
//            throw new EncryptionException(e);
//        }
        throw new UnsupportedOperationException();
    }

}
