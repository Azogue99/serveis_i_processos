package Exercici;

import utils.AESUtils;
import utils.Utils;

import javax.crypto.SecretKey;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;

public class AESFileEncryptionCBC {

    public static void run() {
        try {
            // Generar la clau AES
            SecretKey aesKey = AESUtils.generateAESKey(128);

            // Mostrar el contingut 'entrada.txt' abans de xifrar
            System.out.println("Contingut original del fitxer (entrada.txt):");
            displayFileContent("entrada.txt");

            // Xifrar el fitxer i desar-lo com sortida.enc
            FileInputStream inputFile = new FileInputStream("entrada.txt");
            FileOutputStream outputFile = new FileOutputStream("sortida.enc");
            encryptFile(aesKey, inputFile, outputFile);

            // Mostrar el contingut del fitxer xifrat 'sortida.enc' en hexadecimal
            System.out.println("\nContingut xifrat del fitxer (sortida.enc) en hexadecimal:");
            displayFileContentInHex("sortida.enc");

            // Desxifrar el fitxer i mostrar el contingut original
            FileInputStream inputFileEnc = new FileInputStream("sortida.enc");
            FileOutputStream outputFileDec = new FileOutputStream("sortida_dec.txt");
            decryptFile(aesKey, inputFileEnc, outputFileDec);

            // Mostrar el contingut del fitxer desxifrat
            System.out.println("\nContingut desxifrat del fitxer (sortida_dec.txt):");
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

    // Mostrar el contingut d'un fitxer en format hexadecimal (per al fitxer xifrat)
    public static void displayFileContentInHex(String filePath) throws IOException {
        FileInputStream file = new FileInputStream(filePath);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = file.read(buffer)) != -1) {
            String hex = Utils.bytesToHex(buffer);
            System.out.println(hex);  // Mostrar el contingut xifrat en hexadecimal
        }
        file.close();
    }

    // Mostrar el contingut del fitxer desxifrat per verificar que coincideix amb l'original
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
