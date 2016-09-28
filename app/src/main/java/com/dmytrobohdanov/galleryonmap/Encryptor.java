package com.dmytrobohdanov.galleryonmap;


import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Class that keeps method(s) for encrypting
 */
public class Encryptor {

    /**
     * Get md5 string
     */
    public static String getMd5(String stringToEncrypt) {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(stringToEncrypt.getBytes());
            digest = messageDigest.digest();
        } catch (Exception e) {
            e.printStackTrace();
        }

        BigInteger bigInt = new BigInteger(1, digest);
        String encryptedString = bigInt.toString(16);

        while (encryptedString.length() < 32) {
            encryptedString = "0" + encryptedString;
        }

        return encryptedString;
    }
}
