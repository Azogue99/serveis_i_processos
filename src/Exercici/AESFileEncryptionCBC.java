package Exercici;

import utils.AESUtils;

import javax.crypto.SecretKey;
import java.io.*;

import static utils.FileEncrypt.decryptFile;
import static utils.FileEncrypt.encryptFile;
import static utils.Utils.displayFileContentInHex;
import static utils.Utils.readFileContent;

public class AESFileEncryptionCBC {

    public static void run() {
        try {
            // Generar la clau AES
            SecretKey aesKey = AESUtils.generateAESKey(128);

            // Mostrar el contingut 'entrada.txt' abans de xifrar
            System.out.println("Contingut original del fitxer (entrada.txt):");
            System.out.println(readFileContent("entrada.txt"));

            // Xifrar el fitxer i desar-lo com sortida.enc
            FileInputStream inputFile = new FileInputStream("entrada.txt");
            FileOutputStream outputFile = new FileOutputStream("sortida.enc");
            encryptFile(aesKey, inputFile, outputFile);

            // Mostrar el contingut del fitxer xifrat 'sortida.enc' en hexadecimal
            System.out.println("\nContingut xifrat del fitxer (sortida.enc):");
            displayFileContentInHex("sortida.enc");

            // Desxifrar el fitxer i mostrar el contingut original
            FileInputStream inputFileEnc = new FileInputStream("sortida.enc");
            FileOutputStream outputFileDec = new FileOutputStream("sortida_dec.txt");
            decryptFile(aesKey, inputFileEnc, outputFileDec);

            // Mostrar el contingut del fitxer desxifrat
            System.out.println("\nContingut desxifrat del fitxer (sortida_dec.txt):");
            System.out.println(readFileContent("sortida_dec.txt"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
