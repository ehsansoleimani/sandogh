package com.sandogh.sandogh.base.utils;

import org.apache.commons.lang3.Validate;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

/**
 * @author Ehsan Soleimani (esoleimani@voipfuture.com)
 **/
public class PasswordEncryptionUtils {
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 512;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA512";
    private static final SecureRandom RAND = new SecureRandom();

    public static String generateSalt(final int length) {
        Validate.isTrue(length >= 1, "length must be >= 1");
        byte[] salt = new byte[length];
        RAND.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String hashPassword(String password, String salt) {
        Validate.notNull(password, "password cannot be NULL");
        Validate.notBlank(password, "password cannot be BLANK");
        Validate.notNull(salt, "salt cannot be NULL");
        Validate.notBlank(salt, "salt cannot be BLANK");
        char[] chars = password.toCharArray();
        byte[] bytes = salt.getBytes();

        PBEKeySpec spec = new PBEKeySpec(chars, bytes, ITERATIONS, KEY_LENGTH);

        Arrays.fill(chars, Character.MIN_VALUE);
        try {
            SecretKeyFactory fac = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] secretPassword = fac.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(secretPassword);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        } finally {
            spec.clearPassword();
        }
    }

    public static boolean verifyPassword(String password, String key, String salt) {
        Validate.notNull(password, "password cannot be NULL");
        Validate.notBlank(password, "password cannot be BLANK");
        Validate.notNull(key, "key cannot be NULL");
        Validate.notBlank(key, "key cannot be BLANK");
        Validate.notNull(key, "salt cannot be NULL");
        Validate.notBlank(key, "salt cannot be BLANK");
        String optEncrypted = hashPassword(password, salt);
        return optEncrypted.equals(password);
    }

    public static String getHashedPasswordAndSaltCombination(String password) {
        Validate.notNull(password, "password cannot be NULL");
        String salt = generateSalt(KEY_LENGTH);
        String hashedPassword = hashPassword(password, salt);
        return salt + "|" + hashedPassword;
    }

    public static boolean verifyPasswordFromHashAndSaltCombination(String password, String hashAndSalt) {
        Validate.notNull(password, "password cannot be NULL");
        Validate.notBlank(password, "password cannot be BLANK");
        Validate.notNull(hashAndSalt, "hashAndSalt cannot be NULL");
        Validate.notBlank(hashAndSalt, "hashAndSalt cannot be BLANK");
        String[] split = hashAndSalt.split("\\|");
        Validate.isTrue(split.length == 2, "Invalid salt and hash combination");
        return verifyPassword(password, split[0], split[1]);
    }

}
