package service.encryption;

import exception.encryption.EncryptionException;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service("defaultEncryptionService")
public final class MD5EncryptionService implements EncryptionService {

    @Override
    public String getEncrypted(String password) throws EncryptionException {
        byte[] passwordBytes = password.getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(passwordBytes);
            byte[] result = messageDigest.digest();
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : result) {
                stringBuilder.append(b);
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptionException(e);
        }
    }

}
