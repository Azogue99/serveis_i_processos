package Exercici;

import utils.RSAUtils;
import utils.Utils;

import java.security.*;
import java.util.Scanner;

public class MissatgeriaRSA {

    public static void run() {
        try {
            // Pas 1: Generar el parell de claus RSA per l'emissor i el receptor
            System.out.println("Pas 1: Generar el parell de claus RSA per l'emissor i el receptor.");
            KeyPair keyPairEmissor = RSAUtils.generateRSAKeyPair(2048);
            KeyPair keyPairReceptor = RSAUtils.generateRSAKeyPair(2048);
            System.out.println("Clau pública de l'emissor: " + keyPairEmissor.getPublic());
            System.out.println("Clau privada de l'emissor: " + keyPairEmissor.getPrivate());
            System.out.println("Clau pública del receptor: " + keyPairReceptor.getPublic());
            System.out.println("Clau privada del receptor: " + keyPairReceptor.getPrivate());

            // Pas 2: Permetre a l'emissor introduir un missatge
            Scanner scanner = new Scanner(System.in);
            System.out.println("\nPas 2: Introdueix el missatge a enviar:");
            String missatge = scanner.nextLine();
            System.out.println("Missatge original: " + missatge);

            // Pas 3: Xifrar el missatge amb la clau pública del receptor
            System.out.println("\nPas 3: Xifrar el missatge amb la clau pública del receptor.");
            byte[] encryptedMessage = RSAUtils.encryptWithRSA(missatge, keyPairReceptor.getPublic());
            System.out.println("Missatge xifrat (en hexadecimal): " + Utils.bytesToHex(encryptedMessage));

            // Pas 4: Desxifrar el missatge amb la clau privada del receptor
            System.out.println("\nPas 4: Desxifrar el missatge amb la clau privada del receptor.");
            String decryptedMessage = RSAUtils.decryptWithRSA(encryptedMessage, keyPairReceptor.getPrivate());
            System.out.println("Missatge desxifrat: " + decryptedMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
