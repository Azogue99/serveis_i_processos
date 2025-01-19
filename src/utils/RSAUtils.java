package utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;

public class RSAUtils {

    // Generar parell de claus RSA
    public static KeyPair generateRSAKeyPair(int length) throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(length);
        return keyGen.genKeyPair();
    }

    // Xifrar missatge amb la clau pública RSA
    public static byte[] encryptWithRSA(String message, PublicKey pubKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return cipher.doFinal(message.getBytes());
    }

    // Desxifrar missatge amb la clau privada RSA
    public static String decryptWithRSA(byte[] encryptedMessage, PrivateKey privKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedMessage);
        return new String(decryptedBytes);
    }

    // Xifrar la clau AES amb RSA
    public static byte[] encryptAESKey(PublicKey pubKey, SecretKey aesKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return cipher.doFinal(aesKey.getEncoded());
    }

    // Desxifrar la clau AES amb RSA
    public static SecretKey decryptAESKey(PrivateKey privKey, byte[] encryptedKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privKey);
        byte[] decryptedKey = cipher.doFinal(encryptedKey);
        return new SecretKeySpec(decryptedKey, "AES");
    }
}
