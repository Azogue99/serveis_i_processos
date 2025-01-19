import Exercici.*;

import java.util.Scanner;

public class MainMenu {
    public static void showMenu(Scanner scanner) {
        int option;

        do {
            System.out.println("Menu:");
            System.out.println("0 - Sortir");
            System.out.println("1 - Exercici 1");
            System.out.println("2 - Exercici 2");
            System.out.println("3 - Exercici 3");
            System.out.println("4 - Exercici 4");
            System.out.println("5 - Exercici 5");
            System.out.println("6 - Exercici 6");

            option = scanner.nextInt();

            switch (option) {
                case 0:
                    System.out.println("Adeu!");
                    break;
                case 1:
                    AESKeyGeneration.run();
                    break;
                case 2:
                    AESPasswordEncryption.run();
                    break;
                case 3:
                    AESFileEncryptionCBC.run();
                    break;
                case 4:
                    AES_RSA_Fitxer.run();
                    break;
                case 5:
                    FirmaDigital.run();
                    break;
                case 6:
                    MissatgeriaRSA.run();
                    break;
                default:
                    System.out.println("Opcio invalida, selecciona el numero correcte.");
            }
        } while (option != 0);
    }
}
