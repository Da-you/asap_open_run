package com.asap.openrun.global.utils.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.springframework.stereotype.Component;

@Component
public class EncoderServiceImpl implements EncoderService {

  @Override
  public String encoded(String asapName, String rawPassword) {
    // 유일한 아이디인 asapName으로 솔트값을 생성 사용자별 다른 솔트값으로 생성 가능.
    try {
      KeySpec spec = new PBEKeySpec(rawPassword.toCharArray(), getSalt(asapName), 85319, 128);
      SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

      byte[] hash = factory.generateSecret(spec).getEncoded();
      return Base64.getEncoder().encodeToString(hash);

    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new RuntimeException(e);
    }
  }

  private static byte[] getSalt(String asapName) throws NoSuchAlgorithmException {
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    byte[] salt = asapName.getBytes();
    return digest.digest(salt);
  }
}
