package Exercici;

import utils.AESUtils;
import utils.Utils;
import javax.crypto.SecretKey;

public class AESPasswordEncryption {

    public static void run() {
        try {
            // Definir la contrasenya comuna
            String password = "projecte2025";

            // Pas 1: Generar una clau AES a partir de la contrasenya
            System.out.println("Pas 1: Generar una clau AES a partir de la contrasenya '" + password + "'");
            SecretKey aesKey = AESUtils.generateAESKeyFromPassword(password, 128);
            System.out.println("Clau AES generada: " + Utils.bytesToHex(aesKey.getEncoded()));

            // Pas 2: Xifrar el missatge
            String message = "Document confidencial";
            System.out.println("\nPas 2: Xifrar el missatge.");
            byte[] encryptedMessage = AESUtils.encryptWithAES(aesKey, message.getBytes());
            System.out.println("Missatge original: " + message);
            System.out.println("Missatge xifrat: " + Utils.bytesToHex(encryptedMessage));

            // Pas 3: Desxifrar el missatge
            System.out.println("\nPas 3: Desxifrar el missatge.");
            byte[] decryptedMessage = AESUtils.decryptWithAES(aesKey, encryptedMessage);
            String originalMessage = new String(decryptedMessage);

            // Mostrar els resultats
            System.out.println("Missatge desxifrat: " + originalMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
