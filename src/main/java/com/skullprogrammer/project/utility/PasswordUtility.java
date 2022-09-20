package com.skullprogrammer.project.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

public class PasswordUtility {
    private static Logger logger = LoggerFactory.getLogger(PasswordUtility.class);

    public static String getSHA512(String passwordToHash){
        return getSHA512(passwordToHash, "");
    }
    public static String getSHA512(String passwordToHash, String salt){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public static String encodePassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("Empty password");
        }
        String hashString = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            hashString = "";
            for (int i = 0; i < hash.length; i++) {
                hashString += Integer.toHexString(
                        (hash[i] & 0xFF) | 0x100).toLowerCase().substring(1, 3);
            }
        } catch (NoSuchAlgorithmException e) {
        }
        return hashString;
    }

    private static final String ALGORITHM = "AES";
    private static final int ITERATIONS = 2;
    private static final byte[] keyValue =
            new byte[]{'@', '?', 'q', 'g', 'E', '0', '0', '?', 'r', '{', 'x', '4', '$', 'r', 'a', '&'};
    //'t', '#', 'N', '7', 'W', '{', '<', '$', 'p', '"', '5', '$', 'o', '`', '$', '~'};

    public static String encrypt(String value, String salt) {
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, key);

            String valueToEnc = null;
            String eValue = value;
            for (int i = 0; i < ITERATIONS; i++) {
                valueToEnc = salt + eValue;
                byte[] encValue = c.doFinal(valueToEnc.getBytes());
                eValue = Base64.getEncoder().encodeToString(encValue);
            }
            return eValue;
        } catch (Exception e) {
            logger.error("Error encrypting: " + e.getLocalizedMessage());
            return null;
        }
    }

    public static String decrypt(String value, String salt) {
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, key);

            String dValue = null;
            String valueToDecrypt = value;
            for (int i = 0; i < ITERATIONS; i++) {
                byte[] decordedValue
                        = Base64.getDecoder().decode(valueToDecrypt);
                byte[] decValue = c.doFinal(decordedValue);
                dValue = new String(decValue).substring(salt.length());
                valueToDecrypt = dValue;
            }
            return dValue;
        } catch (Exception e) {
            logger.error("Error decrypting: " + e.getLocalizedMessage());
            return null;
        }
    }

    private static Key generateKey() throws Exception {
        Key key = new SecretKeySpec(keyValue, ALGORITHM);
        return key;
    }

    public static String getCNonce() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static String createLoginHash(String password, String nonce, String cnonce) {
        String loginNonce = encrypt(password, nonce);
        String loginCNonce = encrypt(loginNonce, cnonce);
        return loginCNonce;
    }
}
