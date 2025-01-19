package Exercici;

import utils.AESUtils;
import utils.RSAUtils;
import utils.Utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.security.KeyPair;

import static utils.Utils.bytesToHex;

public class AES_RSA_Fitxer {

    public static void run() {
        try {
            // Generar el parell de claus RSA
            System.out.println("Pas 1: Generar el parell de claus RSA (publica i privada)");
            KeyPair keyPair = RSAUtils.generateRSAKeyPair(2048);
            System.out.println("Clau pública RSA: " + keyPair.getPublic());
            System.out.println("Clau privada RSA: " + keyPair.getPrivate());

            // Generar la clau AES per xifrar el fitxer
            System.out.println("\nPas 2: Generar la clau AES per xifrar el fitxer.");
            SecretKey aesKey = AESUtils.generateAESKey(128);
            System.out.println("Clau AES generada (simètrica): " + bytesToHex(aesKey.getEncoded()));

            // Mostrar el contingut original de 'entrada.txt'
            System.out.println("\nPas 3: Mostrar el contingut original del fitxer (entrada.txt):");
            displayFileContent("entrada.txt");

            // Xifrar el fitxer amb AES
            System.out.println("\nPas 4: Xifrar el contingut del fitxer amb la clau AES.");
            FileInputStream inputFile = new FileInputStream("entrada.txt");
            FileOutputStream outputFile = new FileOutputStream("sortida.enc");
            encryptFile(aesKey, inputFile, outputFile);

            // Xifrar la clau AES amb la clau pública RSA
            System.out.println("\nPas 5: Xifrar la clau AES amb la clau pública RSA.");
            byte[] encryptedKey = RSAUtils.encryptAESKey(keyPair.getPublic(), aesKey);
            System.out.println("Clau AES xifrada amb RSA (clau embolcallada): " + bytesToHex(encryptedKey));

            // Desa la clau AES embolcallada
            FileOutputStream keyOutFile = new FileOutputStream("clau_embolcallada.key");
            keyOutFile.write(encryptedKey);
            keyOutFile.close();

            // Desxifrar la clau AES amb la clau privada
            System.out.println("\nPas 6: Desxifrar la clau AES amb la clau privada RSA.");
            SecretKey decryptedKey = RSAUtils.decryptAESKey(keyPair.getPrivate(), encryptedKey);
            System.out.println("Clau AES desxifrada: " + bytesToHex(decryptedKey.getEncoded()));

            // Desxifrar el fitxer amb la clau AES desxifrada
            System.out.println("\nPas 7: Desxifrar el fitxer amb la clau AES desxifrada.");
            FileInputStream inputFileEnc = new FileInputStream("sortida.enc");
            FileOutputStream outputFileDec = new FileOutputStream("sortida_dec.txt");
            decryptFile(decryptedKey, inputFileEnc, outputFileDec);

            // Mostrar el contingut del fitxer desxifrat
            System.out.println("\nPas 8: Mostrar el contingut del fitxer desxifrat (sortida_dec.txt):");
            displayDecryptedContent("sortida_dec.txt");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    // Mostrar el contingut d'un fitxer abans de xifrar-lo
    public static void displayFileContent(String filePath) throws IOException {
        FileInputStream file = new FileInputStream(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);  // Mostrar línia per línia el contingut del fitxer
        }
        reader.close();
    }

    // Mostrar el contingut del fitxer desxifrat
    public static void displayDecryptedContent(String decryptedFilePath) throws IOException {
        FileInputStream decryptedFile = new FileInputStream(decryptedFilePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(decryptedFile));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);  // Mostrar línia per línia el contingut desxifrat
        }
        reader.close();
    }
}
