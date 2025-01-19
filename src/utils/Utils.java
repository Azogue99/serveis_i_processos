package utils;

import java.io.*;

public class Utils {

    // Convertir bytes a format hexadecimal
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    // Llegir el contingut del fitxer
    public static String readFileContent(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line).append("\n");
        }
        reader.close();
        return content.toString();
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
}
