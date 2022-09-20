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

    private static final String ENTER_ABC = "Enter the path to the file with the alphabet";

    private static final String FALL_PATH = "Incorrect file path has been entered.";

    private static final String ENTER_KEY = "Enter the key.";

    private static final String FALL_NUMBER = "The number is entered incorrectly.";

    private static final String FILE_IS_EMPTY = "You have specified the path to an empty file";

    private static final int READ_TEST_LINES = 30;

    public Menu() {
        settMenu();
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

    private void settMenu() {
        Handler operation = new Handler();
        ABC abc = new ABC();
        boolean sett = false;
        while (!sett) {
            printString(START_MENU);
            printMenu();
            int menu = settPositionMenu();
            if (menu == 1) {
                operation.encryptCaesarCipher(settFileEncrypted(),
                        abc.getRU(), settKey());
            } else if (menu == 2) {
                operation.encryptCaesarCipher(settFileEncrypted(),
                        abc.getEN(), settKey());
            } else if (menu == 3) {
                operation.encryptCaesarCipher(settFileEncrypted(),
                        abc.getUA(), settKey());
            } else if (menu == 4) {
                operation.encryptCaesarCipher(settFileEncrypted(),
                        abc.createAbc(settAbc()), settKey());
            } else if (menu == 5) {
                operation.decryptCaesarCipher(settFileDecrypted(),
                        abc.getRU(), settKey());
            } else if (menu == 6) {
                operation.decryptCaesarCipher(settFileDecrypted(),
                        abc.getEN(), settKey());
            } else if (menu == 7) {
                operation.decryptCaesarCipher(settFileDecrypted(),
                        abc.getUA(), settKey());
            } else if (menu == 8) {
                operation.decryptCaesarCipher(settFileDecrypted(),
                        abc.createAbc(settAbc()), settKey());
            } else if (menu == 9) {
                String file = settFileDecrypted();
                List<Character> necessaryAbc = toIdentifyAbc(file);
                operation.decryptBruteForce(file, necessaryAbc);
            } else if (menu == 10) {
                operation.decryptBruteForce(settFileDecrypted(),
                        abc.createAbc(settAbc()));
            } else if (menu == 11) {
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

    private static List<Character> toIdentifyAbc(String file) {
        List<Character> ruAbc = new ABC().getRU();
        List<Character> uaAbc = new ABC().getUA();
        List<Character> enAbc = new ABC().getEN();
        Map<List<Character>, Integer> counterCharsAbc = new HashMap<>();
        counterCharsAbc.put(ruAbc, 0);
        counterCharsAbc.put(uaAbc, 0);
        counterCharsAbc.put(enAbc, 0);
        List<Character> abc = null;
        String inputLine;
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader read = new BufferedReader(new FileReader(file))) {
            int reedString = READ_TEST_LINES;
            while (reedString > 0 && (inputLine = read.readLine()) != null) {
                stringBuilder.append(inputLine);
                reedString--;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String checkString = stringBuilder.toString();
        char[] symbols = checkString.toCharArray();
        for (char thisSymbol : symbols) {
            if (ruAbc.contains(thisSymbol)) {
                counterCharsAbc.put(ruAbc, counterCharsAbc.get(ruAbc) + 1);
            }
            if (uaAbc.contains(thisSymbol)) {
                counterCharsAbc.put(uaAbc, counterCharsAbc.get(uaAbc) + 1);
            }
            if (enAbc.contains(thisSymbol)) {
                counterCharsAbc.put(enAbc, counterCharsAbc.get(enAbc) + 1);
            }
        }
        int determinant = Math.max(counterCharsAbc.get(ruAbc),
                Math.max(counterCharsAbc.get(uaAbc), counterCharsAbc.get(enAbc)));

        for (Map.Entry<List<Character>, Integer> pair : counterCharsAbc.entrySet()) {
            if (determinant == pair.getValue()) {
                abc = pair.getKey();
            }
        }
        return abc;
    }
}
