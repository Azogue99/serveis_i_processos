package Exercici;

import utils.RSAUtils;
import utils.Utils;

import java.security.*;

import static utils.Utils.readFileContent;

public class FirmaDigital {

    public static void run() {
        try {
            KeyPair keyPair = RSAUtils.generateRSAKeyPair(2048);

            // Pas 1: Llegir el contingut del fitxer document.txt
            System.out.println("\nPas 1: Llegir el contingut del fitxer document.txt:");
            String document = readFileContent("document.txt");
            System.out.println("Contingut del document: " + document);

            // Pas 2: Generar la firma digital amb la clau privada
            System.out.println("\nPas 2: Generar la firma digital amb la clau privada RSA.");
            byte[] data = document.getBytes();
            byte[] signature = signData(data, keyPair.getPrivate());
            System.out.println("Firma generada (en hexadecimal): " + Utils.bytesToHex(signature));

            // Pas 3: Verificar la firma amb la clau pública
            System.out.println("\nPas 3: Verificar la firma amb la clau pública RSA.");
            boolean isValid = validateSignature(data, signature, keyPair.getPublic());
            System.out.println("La firma és vàlida? " + isValid);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] signData(byte[] data, PrivateKey priv) throws Exception {
        Signature signer = Signature.getInstance("SHA1withRSA");
        signer.initSign(priv);
        signer.update(data);
        return signer.sign();
    }

    public static boolean validateSignature(byte[] data, byte[] signature, PublicKey pub) throws Exception {
        Signature signer = Signature.getInstance("SHA1withRSA");
        signer.initVerify(pub);
        signer.update(data);
        return signer.verify(signature);
    }
}
