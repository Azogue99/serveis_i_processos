package utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileEncrypt {
    // Xifrar el contingut del fitxer
    public static void encryptFile(SecretKey aesKey, FileInputStream inputFile, FileOutputStream outputFile) throws Exception {
        byte[] iv = new byte[16]; // IV per CBC
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, aesKey, ivSpec);

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputFile.read(buffer)) != -1) {
            byte[] encryptedData = cipher.update(buffer, 0, bytesRead);
            outputFile.write(encryptedData, 0, encryptedData.length);
        }

        byte[] finalData = cipher.doFinal();
        if (finalData != null) {
            outputFile.write(finalData);
        }

        inputFile.close();
        outputFile.close();
    }

    // Desxifrar el contingut del fitxer
    public static void decryptFile(SecretKey aesKey, FileInputStream inputFile, FileOutputStream outputFile) throws Exception {
        byte[] iv = new byte[16]; // IV per CBC
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, aesKey, ivSpec);

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputFile.read(buffer)) != -1) {
            byte[] decryptedData = cipher.update(buffer, 0, bytesRead);
            outputFile.write(decryptedData, 0, decryptedData.length);
        }

        byte[] finalData = cipher.doFinal();
        if (finalData != null) {
            outputFile.write(finalData);
        }

        inputFile.close();
        outputFile.close();
    }
}
