package ru.javarush.november.maslennikov.cryptoanalizer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private static final List<String> LIST_MENU = Arrays.asList
            ("1.  Encrypt the text using the \"Caesar cipher\" in Russian;",
                    "2.  Encrypt the text using the \"Caesar cipher\" in English;",
                    "3.  Encrypt the text using the \"Caesar cipher\" in Ukrainian;",
                    "4.  Encrypt the text using the \"Caesar cipher\" in the language suggested by the user;",
                    "5.  Decrypt the text in Russian using the \"Caesar cipher\" key;",
                    "6.  Decrypt the text in English using the \"Caesar cipher\" key;",
                    "7.  Decrypt the text in Ukrainian using the key of the \"Caesar cipher\";",
                    "8.  Decrypt the text in the language suggested by the user using the \"Caesar cipher\" key;",
                    "9.  Decrypt the text in Russian, without using a key;",
                    "10. Decrypt the text in English, without using a key;",
                    "11. Decrypt the text in Ukrainian, without using a key;",
                    "12. Decrypt the text in the language suggested by the user, without using a key;",
                    "13. Exit.\n");

    private static final int SIZE_MENU = LIST_MENU.size();
    private static final String START_MENU = "Enter the number corresponding to the menu item:";

    private static final String ENTER_NUMBER = "Enter the number corresponding to the menu item.";

    private static final String ENTER_PATH_ENCRYPT = "Enter the path to the file whose text you want to encrypt:";

    private static final String ENTER_PATH_DECRYPT = "Enter the path to the file whose text you want to decrypt:";

    private static final String ENTER_ABC = "Enter the path to the file with the alphabet";

    private static final String FALL_PATH = "Incorrect file path has been entered.";

    private static final String ENTER_KEY = "Enter the key.";

    private static final String FALL_NUMBER = "The number is entered incorrectly.";

    private static final String FILE_IS_EMPTY = "You have specified the path to an empty file";

    public Menu() {
        try {
            settMenu();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void printString(String string) {
        System.out.println(string);
    }

    private static void printMenu() {
        for (String positionMenu : LIST_MENU) {
            printString(positionMenu);
        }
    }

    private static String settAbc() {
        Scanner console = new Scanner(System.in);
        String fileText = "";
        boolean sett = false;
        while (!sett) {
            printString(ENTER_ABC);
            fileText = console.nextLine();
            if (!Files.isRegularFile(Path.of(fileText))) {
                printString(FALL_PATH);
            } else {
                sett = true;
            }
        }
        return fileText;
    }

    private static String settFileEncrypted() {
        Scanner console = new Scanner(System.in);
        String fileText = "";
        boolean sett = false;
        while (!sett) {
            printString(ENTER_PATH_ENCRYPT);
            fileText = console.nextLine();
            if (!Files.isRegularFile(Path.of(fileText))) {
                printString(FALL_PATH);
            } else if (new File(fileText).length() == 0) {
                printString(FILE_IS_EMPTY);
            } else {
                sett = true;
            }
        }
        return fileText;
    }

    private static String settFileDecrypted() {
        Scanner console = new Scanner(System.in);
        String fileText = "";
        boolean sett = false;
        while (!sett) {
            printString(ENTER_PATH_DECRYPT);
            fileText = console.nextLine();
            if (!Files.isRegularFile(Path.of(fileText))) {
                printString(FALL_PATH);
            } else if (new File(fileText).length() == 0) {
                System.out.println(FILE_IS_EMPTY);
            } else {
                sett = true;
            }
        }
        return fileText;
    }


    private static int settKey() {
        Scanner console = new Scanner(System.in);
        int key = 0;
        boolean sett = false;
        while (!sett) {
            printString(ENTER_KEY);
            String inString = console.nextLine();
            if (inString.equals("")
                    || !Character.isDigit(inString.charAt(0))) {
                printString(FALL_NUMBER);
            } else {
                key = Integer.parseInt(inString);
                sett = true;
            }
        }
        return key;
    }


    private void settMenu() throws IOException {
        Cryptographer cryptographer = new Cryptographer();
        Decoder decoder = new Decoder();
        ABC abc = new ABC();
        boolean sett = false;
        while (!sett) {
            printString(START_MENU);
            printMenu();
            int menu = settPositionMenu();
            if (menu == 1) {
                cryptographer.encryptCaesarCipher(settFileEncrypted(),
                        abc.getRU(), settKey());
            } else if (menu == 2) {
                cryptographer.encryptCaesarCipher(settFileEncrypted(),
                        abc.getEN(), settKey());
            } else if (menu == 3) {
                cryptographer.encryptCaesarCipher(settFileEncrypted(),
                        abc.getUA(), settKey());
            } else if (menu == 4) {
                cryptographer.encryptCaesarCipher(settFileEncrypted(),
                        abc.createAbc(settAbc()), settKey());
            } else if (menu == 5) {
                decoder.decryptCaesarCipher(settFileDecrypted(),
                        abc.getRU(), settKey());
            } else if (menu == 6) {
                decoder.decryptCaesarCipher(settFileDecrypted(),
                        abc.getEN(), settKey());
            } else if (menu == 7) {
                decoder.decryptCaesarCipher(Menu.settFileDecrypted(),
                        abc.getUA(), settKey());
            } else if (menu == 8) {
                decoder.decryptCaesarCipher(Menu.settFileDecrypted(),
                        abc.createAbc(settAbc()), Menu.settKey());
            } else if (menu == 9) {
                decoder.decryptBruteForce(settFileDecrypted(), abc.getRU());
            } else if (menu == 10) {
                decoder.decryptBruteForce(settFileDecrypted(), abc.getEN());
            } else if (menu == 11) {
                decoder.decryptBruteForce(settFileDecrypted(), abc.getUA());
            } else if (menu == 12) {
                decoder.decryptBruteForce(settFileDecrypted(),
                        abc.createAbc(settAbc()));
            } else if (menu == 13) {
                sett = true;
            }
        }
    }

    private static int settPositionMenu() {
        Scanner console = new Scanner(System.in);
        int positionMenu = 0;
        boolean sett = false;
        while (!sett) {
            String inputString = console.nextLine();
            if (inputString.equals("")
                    || !Character.isDigit(inputString.charAt(0))
                    || Integer.parseInt(inputString) > SIZE_MENU
                    || Integer.parseInt(inputString) <= 0) {
                printString(FALL_NUMBER);
                printString(ENTER_NUMBER);
            } else {
                positionMenu = Integer.parseInt(inputString);
                sett = true;
            }
        }
        return positionMenu;
    }
}

