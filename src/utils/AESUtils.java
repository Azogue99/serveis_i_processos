package utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Arrays;

public class AESUtils {

    // Generar clau AES
    public static SecretKey generateAESKey(int keySize) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(keySize);
        return keyGen.generateKey();
    }

    // Generar una clau AES a partir d'una contrasenya
    public static SecretKey generateAESKeyFromPassword(String password, int keySize) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = Arrays.copyOf(md.digest(password.getBytes()), keySize / 8);
        return new SecretKeySpec(keyBytes, "AES");
    }

    // Xifrar dades amb AES
    public static byte[] encryptWithAES(SecretKey aesKey, byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivSpec = new IvParameterSpec(new byte[16]);
        cipher.init(Cipher.ENCRYPT_MODE, aesKey, ivSpec);
        return cipher.doFinal(data);
    }

    // Desxifrar dades amb AES
    public static byte[] decryptWithAES(SecretKey aesKey, byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivSpec = new IvParameterSpec(new byte[16]);
        cipher.init(Cipher.DECRYPT_MODE, aesKey, ivSpec);
        return cipher.doFinal(data);
    }
}
