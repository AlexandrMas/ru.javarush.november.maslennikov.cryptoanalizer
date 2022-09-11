package ru.javarush.november.maslennikov.cryptoanalizer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Scanner;

public class Menu {
    private static final String LIST_MENU = """
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
    private static final String IN_NUMBER = "Enter the number corresponding to the menu item.";

    private static final String IN_PATH_ENCRYPT = "Enter the path to the file whose text you want to encrypt:";

    private static final String IN_PATH_DECRYPT = "Enter the path to the file whose text you want to decrypt:";

    private static final String IN_ABC = "Enter the path to the file with the alphabet";

    private static final String FALL_PATH = "Incorrect file path has been entered.";

    private static final String IN_KEY = "Enter the key.";

    private static final String FALL_NUMBER = "The number is entered incorrectly.";

    private static final String DECRYPT_FINISH = "The text is decrypted, written to a file with the name decryptedFile.txt\n";

    private static final String FILE_IS_EMPTY = "You have specified the path to an empty file";

    public Menu() throws IOException {
        Menu.settMenu();
    }

    private static String settAbc() {
        Scanner console = new Scanner(System.in);
        String fileText;
        while (true) {
            System.out.println(IN_ABC);
            fileText = console.nextLine();
            if (!Files.isRegularFile(Path.of(fileText))) {
                System.out.println(FALL_PATH);
            } else {
                break;
            }
        }
        return fileText;
    }

    private static String settFileEncrypted() {
        Scanner console = new Scanner(System.in);
        String fileText;
        while (true) {
            System.out.println(IN_PATH_ENCRYPT);
            fileText = console.nextLine();
            if (!Files.isRegularFile(Path.of(fileText))) {
                System.out.println(FALL_PATH);
            } else if (new File(fileText).length() == 0) {
                System.out.println(FILE_IS_EMPTY);
            } else {
                break;
            }
        }
        return fileText;
    }

    private static String settFileDecrypted() {
        Scanner console = new Scanner(System.in);
        String fileText;
        while (true) {
            System.out.println(IN_PATH_DECRYPT);
            fileText = console.nextLine();
            if (!Files.isRegularFile(Path.of(fileText))) {
                System.out.println(FALL_PATH);
            } else if (new File(fileText).length() == 0) {
                System.out.println(FILE_IS_EMPTY);
            } else {
                break;
            }
        }
        return fileText;
    }

    private static int settKey() {
        Scanner console = new Scanner(System.in);
        int key;
        while (true) {
            System.out.println(IN_KEY);
            String s = console.nextLine();
            if (s.equals("")) {
                System.out.println(FALL_NUMBER);
            } else if (!Character.isDigit(s.charAt(0))) {
                System.out.println(FALL_NUMBER);
            } else {
                key = Integer.parseInt(s);
                break;
            }
        }
        return key;
    }

    private static void settMenu() throws IOException {
        while (true) {
            System.out.println(LIST_MENU);
            int menu = Menu.settPositionMenu();
            if (menu == 1) {
                new Encrypt().encryptCaesarCipher(Menu.settFileEncrypted(),
                        new ABC().getRU(), Menu.settKey());
            }
            if (menu == 2) {
                new Encrypt().encryptCaesarCipher(Menu.settFileEncrypted(),
                        new ABC().getEN(), Menu.settKey());
            }
            if (menu == 3) {
                new Encrypt().encryptCaesarCipher(Menu.settFileEncrypted(),
                        new ABC().getUA(), Menu.settKey());
            }
            if (menu == 4) {
                new Encrypt().encryptCaesarCipher(Menu.settFileEncrypted(),
                        new ABC().createAbc(Menu.settAbc()), Menu.settKey());
            }
            if (menu == 5) {
                new Decrypt().decryptCaesarCipher(Menu.settFileDecrypted(),
                        new ABC().getRU(), Menu.settKey());
                System.out.println(DECRYPT_FINISH);
            }
            if (menu == 6) {
                new Decrypt().decryptCaesarCipher(Menu.settFileDecrypted(),
                        new ABC().getEN(), Menu.settKey());
                System.out.println(DECRYPT_FINISH);
            }
            if (menu == 7) {
                new Decrypt().decryptCaesarCipher(Menu.settFileDecrypted(),
                        new ABC().getUA(), Menu.settKey());
                System.out.println(DECRYPT_FINISH);
            }
            if (menu == 8) {
                new Decrypt().decryptCaesarCipher(Menu.settFileDecrypted(),
                        new ABC().createAbc(Menu.settAbc()), Menu.settKey());
                System.out.println(DECRYPT_FINISH);
            }
            if (menu == 9) {
                new Decrypt().decryptBruteForce(Menu.settFileDecrypted(), new ABC().getRU());
                System.out.println(DECRYPT_FINISH);
            }
            if (menu == 10) {
                new Decrypt().decryptBruteForce(Menu.settFileDecrypted(), new ABC().getEN());
                System.out.println(DECRYPT_FINISH);
            }
            if (menu == 11) {
                new Decrypt().decryptBruteForce(Menu.settFileDecrypted(), new ABC().getUA());
                System.out.println(DECRYPT_FINISH);
            }
            if (menu == 12) {
                new Decrypt().decryptBruteForce(Menu.settFileDecrypted(),
                        new ABC().createAbc(Menu.settAbc()));
                System.out.println(DECRYPT_FINISH);
            }
            if (menu == 13) {
                break;
            }
        }
    }

    private static int settPositionMenu() {
        Scanner console = new Scanner(System.in);
        int menu;
        while (true) {
            String s = console.nextLine();
            if (s.equals("")) {
                System.out.println(FALL_NUMBER);
                System.out.println(IN_NUMBER);
            } else if (!Character.isDigit(s.charAt(0))) {
                System.out.println(FALL_NUMBER);
                System.out.println(IN_NUMBER);
            } else if (Integer.parseInt(s) > 13 && Integer.parseInt(s) > 0) {
                System.out.println(FALL_NUMBER);
                System.out.println(IN_NUMBER);
            } else {
                menu = Integer.parseInt(s);
                break;
            }
        }
        return menu;
    }
}
