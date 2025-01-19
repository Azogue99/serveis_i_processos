package Exercici;

import utils.AESUtils;
import utils.RSAUtils;

import javax.crypto.SecretKey;
import java.io.*;
import java.security.KeyPair;

import static utils.FileEncrypt.decryptFile;
import static utils.FileEncrypt.encryptFile;
import static utils.Utils.*;

public class AES_RSA_Fitxer {

    public static void run() {
        try {

            /// PAS 1

            // Generar el parell de claus RSA
            System.out.println("Pas 1.1: Generar el parell de claus RSA (publica i privada)");
            KeyPair keyPair = RSAUtils.generateRSAKeyPair(2048);
            System.out.println("Clau pública RSA: " + keyPair.getPublic());
            System.out.println("Clau privada RSA: " + keyPair.getPrivate());

            // Generar la clau AES per xifrar el fitxer
            System.out.println("\nPas 1.2: Generar la clau AES per xifrar el fitxer.");
            SecretKey aesKey = AESUtils.generateAESKey(128);
            System.out.println("Clau AES generada (simètrica): " + bytesToHex(aesKey.getEncoded()));

            /// PAS 2

            // Xifrar la clau AES amb la clau pública RSA
            System.out.println("\nPas 2: Xifrar la clau AES amb la clau pública RSA.");
            byte[] encryptedKey = RSAUtils.encryptAESKey(keyPair.getPublic(), aesKey);
            System.out.println("Clau AES xifrada amb RSA (clau embolcallada): " + bytesToHex(encryptedKey));

            /// PAS 3

            // Xifrar el fitxer amb AES
            System.out.println("\nPas 3.1: Xifrar el contingut del fitxer amb la clau AES.");
            FileInputStream inputFile = new FileInputStream("entrada.txt");
            FileOutputStream outputFile = new FileOutputStream("sortida.enc");
            encryptFile(aesKey, inputFile, outputFile);
            outputFile.flush();
            outputFile.close();

            // Mostrar els continguts
            System.out.println("\nFitxer original:");
            System.out.println(readFileContent("entrada.txt"));

            // Mostrar el contingut del fitxer de sortida
            System.out.println("\nFitxer de sortida:");
            displayFileContentInHex("sortida.enc");


            // Desa la clau AES embolcallada
            System.out.println("\nPas 3.2: Desa la clau AES embolcallada.");
            FileOutputStream keyOutFile = new FileOutputStream("clau_embolcallada.key");
            keyOutFile.write(encryptedKey);
            keyOutFile.flush();
            keyOutFile.close();

            // Mostrar el contingut del fitxer amb la clau embolcallada
            System.out.println("Fitxer amb la clau_embolcallada.key:");
            displayFileContentInHex("clau_embolcallada.key");

            /// PAS 4

            // Desxifrar la clau AES amb la clau privada
            System.out.println("\nPas 4: Desxifrar la clau AES amb la clau privada RSA.");
            SecretKey decryptedKey = RSAUtils.decryptAESKey(keyPair.getPrivate(), encryptedKey);
            System.out.println("Clau AES desxifrada: " + bytesToHex(decryptedKey.getEncoded()));

            // Desxifrar el fitxer amb la clau AES desxifrada
            FileInputStream inputFileEnc = new FileInputStream("sortida.enc");
            FileOutputStream outputFileDec = new FileOutputStream("sortida_dec.txt");
            decryptFile(decryptedKey, inputFileEnc, outputFileDec);
            outputFileDec.flush();
            outputFileDec.close();

            // Mostrar el contingut del fitxer desxifrat
            System.out.println("\nContingut del fitxer desxifrat (sortida_dec.txt):");
            System.out.println(readFileContent("sortida_dec.txt"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
