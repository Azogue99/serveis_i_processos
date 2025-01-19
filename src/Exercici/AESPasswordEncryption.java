package Exercici;

import utils.AESUtils;
import javax.crypto.SecretKey;

public class AESPasswordEncryption {

    public static void run() {
        try {
            String password = "projecte2025";

            // Generar una clau AES a partir de la contrasenya
            SecretKey aesKey = AESUtils.generateAESKeyFromPassword(password, 128);

            // Xifrar el missatge
            String message = "Document confidencial";
            byte[] encryptedMessage = AESUtils.encryptWithAES(aesKey, message.getBytes());

            // Desxifrar el missatge
            byte[] decryptedMessage = AESUtils.decryptWithAES(aesKey, encryptedMessage);
            String originalMessage = new String(decryptedMessage);

            System.out.println("Missatge original: " + message);
            System.out.println("Missatge xifrat: " + encryptedMessage);
            System.out.println("Missatge desxifrat: " + originalMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
