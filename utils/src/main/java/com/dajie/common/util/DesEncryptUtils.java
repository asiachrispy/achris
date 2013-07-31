package com.dajie.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.SecureRandom;

/**
 * Something
 * <pre>
 * User: xuehui.li
 * Date: 13-3-12
 * Time: 下午5:51
 * </pre>
 */
public class DesEncryptUtils {
    private static final Logger LOG = LoggerFactory.getLogger(DesEncryptUtils.class);

    public static byte[] desEncrypt(byte[] plainBytes, byte[] keyBytes) {
        return crypt(plainBytes, Cipher.ENCRYPT_MODE, keyBytes);
    }

    public static byte[] desDecrypt(byte[] cipherBytes, byte[] keyBytes) {
        return crypt(cipherBytes, Cipher.DECRYPT_MODE, keyBytes);
    }

    public static byte[] crypt(byte[] text, int mode, byte[] keyBytes) {
        SecureRandom sr = new SecureRandom();
        byte cryptBytes[] = null;
        try {
            DESKeySpec dks = new DESKeySpec(keyBytes);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(mode, key, sr);
            byte encryptedData[] = text;
            cryptBytes = cipher.doFinal(encryptedData);
        } catch (Exception e) {
            LOG.error("error occur!error msg:{}", e.getMessage(), e);
        }
        return cryptBytes;
    }

    /**
     * @param plainText string need to encrypt
     * @param key       string which length is  longer than or equal to 8
     * @return string after encrypting
     * @throws Exception
     */
    public static String encrypt(String plainText, String key) {
        byte[] cipherBytes = null;
        try {
            cipherBytes = desEncrypt(plainText.getBytes(), key.getBytes());
        } catch (Exception e) {
            LOG.error("error occur!error msg:{}", e.getMessage(), e);
        }
        if (cipherBytes != null) {
            return base64Encode(cipherBytes);
        }
        return "";
    }

    public static String decrypt(String cipherText, String key) {
        byte[] decode = base64Decode(cipherText);
        String plainText = "";
        try {
            plainText = new String(desDecrypt(decode, key.getBytes()));
        } catch (Exception e) {
            LOG.error("error occur!error msg:{}", e.getMessage(), e);
        }
        return plainText;
    }

    public static String base64Encode(byte[] s) {
        if (s == null)
            return null;
        BASE64Encoder b = new sun.misc.BASE64Encoder();
        return b.encode(s);
    }

    public static byte[] base64Decode(String s) {
        if (s == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b = new byte[0];
        try {
            b = decoder.decodeBuffer(s);
        } catch (IOException e) {
            LOG.error("error occur!error msg:{}", e.getMessage(), e);
        }
        return b;
    }

    public static void main(String[] args) throws Exception {
        String key = "aaaaeactf";
        String input = "group1/M00/09/13/CgogqlE3JAaASLgdAAAFm5OC7Ag939.txt";
        String cipherText = DesEncryptUtils.encrypt(input, key);
        System.out.println("Encode:" + cipherText);
        System.out.println("Decode:" + DesEncryptUtils.decrypt(cipherText, key));
        System.out.print(URLEncoder.encode(cipherText, "UTF-8"));

    }
}
