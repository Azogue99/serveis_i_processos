package Exercici;

import utils.RSAUtils;
import java.security.*;
import java.util.Scanner;

public class MissatgeriaRSA {

    public static void run() {
        try {
            // Generar el parell de claus RSA per l'emissor i el receptor
            KeyPair keyPairEmissor = RSAUtils.generateRSAKeyPair(2048);
            KeyPair keyPairReceptor = RSAUtils.generateRSAKeyPair(2048);

            // Permetre l'emissor introduir un missatge
            Scanner scanner = new Scanner(System.in);
            System.out.println("Introdueix el missatge:");
            String missatge = scanner.nextLine();

            // Xifrar el missatge amb la clau pública del receptor
            byte[] encryptedMessage = RSAUtils.encryptWithRSA(missatge, keyPairReceptor.getPublic());

            // Desxifrar el missatge amb la clau privada del receptor
            String decryptedMessage = RSAUtils.decryptWithRSA(encryptedMessage, keyPairReceptor.getPrivate());

            System.out.println("Missatge original: " + missatge);
            System.out.println("Missatge desxifrat: " + decryptedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
