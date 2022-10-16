package ru.javarush.november.maslennikov.cryptoanalizer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Menu {

    private static final List<String> LIST_MENU = Arrays.asList
            ("1.  Encrypt the text using the \"Caesar cipher\" in Russian;",
                    "2.  Encrypt the text using the \"Caesar cipher\" in English;",
                    "3.  Encrypt the text using the \"Caesar cipher\" in Ukrainian;",
                    "4.  Encrypt the text using the \"Caesar cipher\" in the language suggested by the user;",
                    "5.  Decrypt the text in Russian using the \"Caesar cipher\" key;",
                    "6.  Decrypt the text in English using the \"Caesar cipher\" key;",
                    "7.  Decrypt the text in Ukrainian using the \"Caesar cipher\"key;",
                    "8.  Decrypt the text in the language suggested by the user using the \"Caesar cipher\" key;",
                    "9.  Decrypt the text without using a key, only Russian, Ukrainian and English;",
                    "10. Decrypt the text in the language suggested by the user, without using a key;",
                    "11. Exit.\n");

    private static final List<Integer> POSITION_MENU_ENCRYPT = Arrays.asList(1, 2, 3, 4);

    private static final int SIZE_MENU = LIST_MENU.size();

    private static final String START_MENU = "Enter the number corresponding to the menu item:";

    private static final String ENTER_NUMBER = "Enter the number corresponding to the menu item.";

    private static final String ENTER_PATH_ENCRYPT = "Enter the path to the file whose text you want to encrypt:";

    private static final String ENTER_PATH_DECRYPT = "Enter the path to the file whose text you want to decrypt:";

    private static final String ENTER_PATH_ALPHABET = "Enter the path to the file with the alphabet";

    private static final String FALL_PATH = "Incorrect file path has been entered.";

    private static final String ENTER_KEY = "Enter the key.";

    private static final String FALL_NUMBER = "The number is entered incorrectly.";

    private static final String FILE_IS_EMPTY = "You have specified the path to an empty file";

    private static void printString(String string) {
        System.out.println(string);
    }

    private static void printMenu() {
        for (String positionMenu : LIST_MENU) {
            printString(positionMenu);
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

    private static String settFilePath(int positionMenu) {
        String nameOperation;
        if (POSITION_MENU_ENCRYPT.contains(positionMenu)) {
            nameOperation = ENTER_PATH_ENCRYPT;
        } else {
            nameOperation = ENTER_PATH_DECRYPT;
        }
        Scanner console = new Scanner(System.in);
        String filePath = "";
        boolean sett = false;
        while (!sett) {
            printString(nameOperation);
            filePath = console.nextLine();
            if (!Files.isRegularFile(Path.of(filePath))) {
                printString(FALL_PATH);
            } else if (new File(filePath).length() == 0) {
                System.out.println(FILE_IS_EMPTY);
            } else {
                sett = true;
            }
        }
        return filePath;
    }

    private static int settKey() {
        Scanner console = new Scanner(System.in);
        int key = 0;
        boolean sett = false;
        while (!sett) {
            printString(ENTER_KEY);
            String inputString = console.nextLine();
            if (inputString.equals("")
                    || !Character.isDigit(inputString.charAt(0))) {
                printString(FALL_NUMBER);
            } else {
                key = Integer.parseInt(inputString);
                sett = true;
            }
        }
        return key;
    }

    private static String settAlphabet() {
        Scanner console = new Scanner(System.in);
        String filePath = "";
        boolean sett = false;
        while (!sett) {
            printString(ENTER_PATH_ALPHABET);
            filePath = console.nextLine();
            if (!Files.isRegularFile(Path.of(filePath))) {
                printString(FALL_PATH);
            } else {
                sett = true;
            }
        }
        return filePath;
    }

    public void startMenu() {
        Handler operation = new Handler();
        Alphabet alphabet = new Alphabet();
        boolean sett = false;
        while (!sett) {
            printString(START_MENU);
            printMenu();
            int positionMenu = settPositionMenu();
            switch (positionMenu) {
                case 1:
                    operation.encryptCaesarCipher(settFilePath(positionMenu),
                            alphabet.getRuAlphabet(), settKey());
                    break;
                case 2:
                    operation.encryptCaesarCipher(settFilePath(positionMenu),
                            alphabet.getEnAlphabet(), settKey());
                    break;
                case 3:
                    operation.encryptCaesarCipher(settFilePath(positionMenu),
                            alphabet.getUaAlphabet(), settKey());
                    break;
                case 4:
                    operation.encryptCaesarCipher(settFilePath(positionMenu),
                            alphabet.createAlphabet(settAlphabet()), settKey());
                    break;
                case 5:
                    operation.decryptCaesarCipher(settFilePath(positionMenu),
                            alphabet.getRuAlphabet(), settKey());
                    break;
                case 6:
                    operation.decryptCaesarCipher(settFilePath(positionMenu),
                            alphabet.getEnAlphabet(), settKey());
                    break;
                case 7:
                    operation.decryptCaesarCipher(settFilePath(positionMenu),
                            alphabet.getUaAlphabet(), settKey());
                    break;
                case 8:
                    operation.decryptCaesarCipher(settFilePath(positionMenu),
                            alphabet.createAlphabet(settAlphabet()), settKey());
                    break;
                case 9:
                    String filePath = settFilePath(positionMenu);
                    List<Character> userAlphabet = alphabet.toIdentifyAlphabet(filePath);
                    operation.decryptBruteForce(filePath, userAlphabet);
                    break;
                case 10:
                    operation.decryptBruteForce(settFilePath(positionMenu),
                            alphabet.createAlphabet(settAlphabet()));
                    break;
                case 11:
                    sett = true;
                    break;
                default:
                    printString(START_MENU);
            }
        }
    }
}
