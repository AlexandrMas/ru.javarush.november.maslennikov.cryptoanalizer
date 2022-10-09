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

    private static final int READ_TEST_LINES = 30;

    private static void printString(String string) {
        System.out.println(string);
    }

    private static void printMenu() {
        for (String positionMenu : LIST_MENU) {
            printString(positionMenu);
        }
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

    private static String settFileEncrypted() {
        Scanner console = new Scanner(System.in);
        String filePath = "";
        boolean sett = false;
        while (!sett) {
            printString(ENTER_PATH_ENCRYPT);
            filePath = console.nextLine();
            if (!Files.isRegularFile(Path.of(filePath))) {
                printString(FALL_PATH);
            } else if (new File(filePath).length() == 0) {
                printString(FILE_IS_EMPTY);
            } else {
                sett = true;
            }
        }
        return filePath;
    }

    private static String settFileDecrypted() {
        Scanner console = new Scanner(System.in);
        String filePath = "";
        boolean sett = false;
        while (!sett) {
            printString(ENTER_PATH_DECRYPT);
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

    public void settMenu() {
        Handler operation = new Handler();
        Alphabet alphabet = new Alphabet();
        boolean sett = false;
        while (!sett) {
            printString(START_MENU);
            printMenu();
            int positionMenu = settPositionMenu();
            if (positionMenu == 1) {
                operation.encryptCaesarCipher(settFileEncrypted(),
                        alphabet.getRuAlphabet(), settKey());
            } else if (positionMenu == 2) {
                operation.encryptCaesarCipher(settFileEncrypted(),
                        alphabet.getEnAlphabet(), settKey());
            } else if (positionMenu == 3) {
                operation.encryptCaesarCipher(settFileEncrypted(),
                        alphabet.getUaAlphabet(), settKey());
            } else if (positionMenu == 4) {
                operation.encryptCaesarCipher(settFileEncrypted(),
                        alphabet.createAlphabet(settAlphabet()), settKey());
            } else if (positionMenu == 5) {
                operation.decryptCaesarCipher(settFileDecrypted(),
                        alphabet.getRuAlphabet(), settKey());
            } else if (positionMenu == 6) {
                operation.decryptCaesarCipher(settFileDecrypted(),
                        alphabet.getEnAlphabet(), settKey());
            } else if (positionMenu == 7) {
                operation.decryptCaesarCipher(settFileDecrypted(),
                        alphabet.getUaAlphabet(), settKey());
            } else if (positionMenu == 8) {
                operation.decryptCaesarCipher(settFileDecrypted(),
                        alphabet.createAlphabet(settAlphabet()), settKey());
            } else if (positionMenu == 9) {
                String filePath = settFileDecrypted();
                List<Character> userAlphabet = toIdentifyAlphabet(filePath);
                operation.decryptBruteForce(filePath, userAlphabet);
            } else if (positionMenu == 10) {
                operation.decryptBruteForce(settFileDecrypted(),
                        alphabet.createAlphabet(settAlphabet()));
            } else if (positionMenu == 11) {
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

    private static List<Character> toIdentifyAlphabet(String filePath) {
        List<Character> ruAlphabet = new Alphabet().getRuAlphabet();
        List<Character> uaAlphabet = new Alphabet().getUaAlphabet();
        List<Character> enAlphabet = new Alphabet().getEnAlphabet();
        Map<List<Character>, Integer> counterCharsAlphabet = new HashMap<>();
        counterCharsAlphabet.put(ruAlphabet, 0);
        counterCharsAlphabet.put(uaAlphabet, 0);
        counterCharsAlphabet.put(enAlphabet, 0);
        List<Character> alphabet = null;
        String inputLine;
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader read = new BufferedReader(new FileReader(filePath))) {
            int reedString = READ_TEST_LINES;
            while (reedString > 0 && (inputLine = read.readLine()) != null) {
                stringBuilder.append(inputLine);
                reedString--;
            }
            String checkString = stringBuilder.toString();
            char[] symbols = checkString.toCharArray();
            for (char thisSymbol : symbols) {
                if (ruAlphabet.contains(thisSymbol)) {
                    counterCharsAlphabet.put(ruAlphabet, counterCharsAlphabet.get(ruAlphabet) + 1);
                }
                if (uaAlphabet.contains(thisSymbol)) {
                    counterCharsAlphabet.put(uaAlphabet, counterCharsAlphabet.get(uaAlphabet) + 1);
                }
                if (enAlphabet.contains(thisSymbol)) {
                    counterCharsAlphabet.put(enAlphabet, counterCharsAlphabet.get(enAlphabet) + 1);
                }
            }
            int determinant = Math.max(counterCharsAlphabet.get(ruAlphabet),
                    Math.max(counterCharsAlphabet.get(uaAlphabet), counterCharsAlphabet.get(enAlphabet)));

            for (Map.Entry<List<Character>, Integer> pair : counterCharsAlphabet.entrySet()) {
                if (determinant == pair.getValue()) {
                    alphabet = pair.getKey();
                }
            }
            return alphabet;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
