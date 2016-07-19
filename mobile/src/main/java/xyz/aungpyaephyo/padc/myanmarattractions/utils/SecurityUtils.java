package xyz.aungpyaephyo.padc.myanmarattractions.utils;

import android.util.Base64;
import android.util.Log;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import xyz.aungpyaephyo.padc.myanmarattractions.MyanmarAttractionsApp;

/**
 * Created by aung on 7/14/16.
 */
public class SecurityUtils {

    private static final Byte syncHolder = 1;

    public static String encryptMD5(String originalText) {
        if (originalText == null) {
            return null;
        }

        try {
            MessageDigest messageDigest = MessageDigest.getInstance(MyanmarAttractionsConstants.ENCRYPT_MD5);
            messageDigest.reset();
            messageDigest.update(originalText.getBytes());
            byte[] encryptedPassword;
            synchronized (syncHolder) {
                encryptedPassword = messageDigest.digest();
            }
            BigInteger bigInt = new BigInteger(1, encryptedPassword);
            String hashPassword = bigInt.toString(16);

            StringBuffer passwordBuffer = new StringBuffer(hashPassword);
            while (passwordBuffer.length() < 32) {
                passwordBuffer.insert(0, 0);
            }

            return passwordBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            Log.d(MyanmarAttractionsApp.TAG, e.getMessage());
        }

        return null;
    }

    public static String encodeBase64(String originalText) {
        if(originalText == null) {
            return null;
        }

        return Base64.encodeToString(originalText.getBytes(), 0);
    }

    public static String decodeBase64(String encodedText) {
        if(encodedText == null) {
            return null;
        }

        return new String(Base64.decode(encodedText, 0));
    }
}
