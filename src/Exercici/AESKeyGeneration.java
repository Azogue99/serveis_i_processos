package Exercici;

import utils.AESUtils;
import utils.Utils;
import javax.crypto.SecretKey;

public class AESKeyGeneration {

    public static void run() {
        String[] users = {"Anna", "Joan", "Laura", "Pau"};
        try {
            for (String user : users) {
                // Generar una clau AES de 128 bits per a cada usuari
                SecretKey aesKey = AESUtils.generateAESKey(128);

                // Convertir la clau a format hexadecimal
                byte[] aesKeyBytes = aesKey.getEncoded();
                String aesKeyHex = Utils.bytesToHex(aesKeyBytes);

                System.out.println("Usuari: " + user + " | Clau AES: " + aesKeyHex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
