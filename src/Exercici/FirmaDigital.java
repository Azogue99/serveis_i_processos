package Exercici;

import utils.RSAUtils;
import java.security.*;

public class FirmaDigital {

    public static void run() {
        try {
            // Generar el parell de claus RSA
            KeyPair keyPair = RSAUtils.generateRSAKeyPair(2048);

            // Dades a signar
            String document = "Document important";
            byte[] data = document.getBytes();

            // Crear la firma digital amb la clau privada
            byte[] signature = signData(data, keyPair.getPrivate());

            // Verificar la firma amb la clau pública
            boolean isValid = validateSignature(data, signature, keyPair.getPublic());

            System.out.println("Firma vàlida: " + isValid);
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
