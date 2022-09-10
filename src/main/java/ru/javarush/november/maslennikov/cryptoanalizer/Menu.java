package ru.javarush.november.maslennikov.cryptoanalizer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Scanner;

public class Menu {
    static String menu = """
            Enter the number corresponding to the menu item:
            1.  Encrypt the text using the "Caesar cipher" in Russian;
            2.  Encrypt the text using the "Caesar cipher" in English;
            3.  Encrypt the text using the "Caesar cipher" in Ukrainian;
            4.  Encrypt the text using the "Caesar cipher" in the language suggested by the user;
            5.  Decrypt the text in Russian using the "Caesar cipher" key;
            6.  Decrypt the text in English using the "Caesar cipher" key;
            7.  Decrypt the text in Ukrainian using the key of the "Caesar cipher";
            8.  Decrypt the text in the language suggested by the user using the "Caesar cipher" key;
            9.  Decrypt the text in Russian, without using a key;
            10. Decrypt the text in English, without using a key;
            11. Decrypt the text in Ukrainian, without using a key;
            12. Decrypt the text in the language suggested by the user, without using a key;
            13. Exit.
            """;
    static String inNumber = "Enter the number corresponding to the menu item.";

    static String inPathEncrypt = "Enter the path to the file whose text you want to encrypt:";

    static String inPathDecrypt = "Enter the path to the file whose text you want to decrypt:";

    static String inAbc = "Enter the path to the file with the alphabet";

    static String fallPath = "Incorrect file path has been entered.";

    static String inKey = "Enter the key.";

    static String fallNumber = "The number is entered incorrectly.";

    static String startEncrypt = "File encryption has started.";

    static String startDecrypt = "Decryption of the file has begun.";

    static String encryptFinish = "The text is encrypted, written to a file with the name encryptedFile.txt\n";

    static String decryptFinish = "The text is decrypted, written to a file with the name decryptedFile.txt\n";

    public Menu() throws IOException {
        while (true) {
            System.out.println(menu);

            int menu = Menu.setMenu();

            if (menu == 1) {
                Encrypt.encryptCaesar(Menu.settFileEncrypted(),
                        new ABC().getRU(), Menu.setKey());
                System.out.println(startEncrypt);
                System.out.println(encryptFinish);
            }

            if (menu == 2) {
                Encrypt.encryptCaesar(Menu.settFileEncrypted(),
                        new ABC().getEN(), Menu.setKey());
                System.out.println(startEncrypt);
                System.out.println(encryptFinish);
            }

            if (menu == 3) {
                Encrypt.encryptCaesar(Menu.settFileEncrypted(),
                        new ABC().getUA(), Menu.setKey());
                System.out.println(startEncrypt);
                System.out.println(encryptFinish);
            }

            if (menu == 4) {
                Encrypt.encryptCaesar(Menu.settFileEncrypted(),
                        new ABC().universal(Menu.settAbc()), Menu.setKey());
                System.out.println(startEncrypt);
                System.out.println(encryptFinish);
            }

            if (menu == 5) {
                Decrypt.decryptCaesar(Menu.settFileDecrypted(),
                        new ABC().getRU(), Menu.setKey());
                System.out.println(startDecrypt);
                System.out.println(decryptFinish);
            }

            if (menu == 6) {
                Decrypt.decryptCaesar(Menu.settFileDecrypted(),
                        new ABC().getEN(), Menu.setKey());
                System.out.println(startDecrypt);
                System.out.println(decryptFinish);
            }

            if (menu == 7) {
                Decrypt.decryptCaesar(Menu.settFileDecrypted(),
                        new ABC().getUA(), Menu.setKey());
                System.out.println(startDecrypt);
                System.out.println(decryptFinish);
            }
            if (menu == 8) {
                Decrypt.decryptCaesar(Menu.settFileDecrypted(),
                        new ABC().universal(Menu.settAbc()), Menu.setKey());
                System.out.println(startDecrypt);
                System.out.println(decryptFinish);
            }


            if (menu == 9) {
                Decrypt.decryptBruteForce(Menu.settFileDecrypted(), new ABC().getRU());
                System.out.println(startDecrypt);
                System.out.println(decryptFinish);
            }

            if (menu == 10) {
                Decrypt.decryptBruteForce(Menu.settFileDecrypted(), new ABC().getEN());
                System.out.println(startDecrypt);
                System.out.println(decryptFinish);
            }

            if (menu == 11) {
                Decrypt.decryptBruteForce(Menu.settFileDecrypted(), new ABC().getUA());
                System.out.println(startDecrypt);
                System.out.println(decryptFinish);
            }
            if (menu == 12) {
                Decrypt.decryptBruteForce(Menu.settFileDecrypted(),
                        new ABC().universal(Menu.settAbc()));
                System.out.println(startDecrypt);
                System.out.println(decryptFinish);
            }
            if (menu == 13) {
                break;
            }
        }
    }
    public static String settAbc() {
        Scanner console = new Scanner(System.in);
        String fileText;
        while (true) {
            System.out.println(inAbc);
            fileText = console.nextLine();
            if (!Files.isRegularFile(Path.of(fileText))) {
                System.out.println(fallPath);
            } else {
                break;
            }
        }
        return fileText;
    }
    public static String settFileEncrypted() {
        Scanner console = new Scanner(System.in);
        String fileText;
        while (true) {
            System.out.println(inPathEncrypt);
            fileText = console.nextLine();
            if (!Files.isRegularFile(Path.of(fileText))) {
                System.out.println(fallPath);
            } else {
                break;
            }
        }
        return fileText;
    }

    public static String settFileDecrypted() {
        Scanner console = new Scanner(System.in);
        String fileText;
        while (true) {
            System.out.println(inPathDecrypt);
            fileText = console.nextLine();
            if (!Files.isRegularFile(Path.of(fileText))) {
                System.out.println(fallPath);
            } else {
                break;
            }
        }
        return fileText;
    }

    public static int setKey() {
        Scanner console = new Scanner(System.in);
        int key;
        while (true) {
            System.out.println(inKey);
            String s = console.nextLine();
            if (s.equals("")) {
                System.out.println(fallNumber);
            } else if (!Character.isDigit(s.charAt(0))) {
                System.out.println(fallNumber);
            } else {
                key = Integer.parseInt(s);
                break;
            }
        }
        return key;
    }

    public static int setMenu() {
        Scanner console = new Scanner(System.in);
        int menu;
        while (true) {
            String s = console.nextLine();
            if (s.equals("")) {
                System.out.println(fallNumber);
                System.out.println(inNumber);
            } else if (!Character.isDigit(s.charAt(0))) {
                System.out.println(fallNumber);
                System.out.println(inNumber);
            } else if (Integer.parseInt(s) > 13 && Integer.parseInt(s) > 0) {
                System.out.println(fallNumber);
                System.out.println(inNumber);
            } else {
                menu = Integer.parseInt(s);
                break;
            }
        }
        return menu;
    }
}

