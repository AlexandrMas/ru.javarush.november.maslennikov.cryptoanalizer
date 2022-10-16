package ru.javarush.november.maslennikov.cryptoanalizer;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.List;

public class Handler {

    private static final int REED_TEST_LINES = 30;

    private static final int MAX_CHARS_IN_WORD = 30;

    private static final int MULTIPLIER_FOR_DECRYPT = -1;

    private static void printInformation(String filePath, String fileProcessed, String newFileNAme) {
        System.out.println("The text is "
                + fileProcessed + ", written to a file with the name "
                + filePath + newFileNAme + "\n");
    }

    private static String setNameProcessed(int key) {
        String fileProcessed;
        if (key >= 0) {
            fileProcessed = "encrypted";
        } else {
            fileProcessed = "decrypted";
        }
        return fileProcessed;
    }

    private static String setFileName(int key) {
        String newFileNAme;
        if (key >= 0) {
            newFileNAme = "_is_encrypted.txt";
        } else {
            newFileNAme = "_is_decrypted.txt";
        }
        return newFileNAme;
    }

    public void encryptCaesarCipher(String filePath, List<Character> alphabet, int key) {
        String fileProcessed = setNameProcessed(key);
        String newFileNAme = setFileName(key);
        String inputString;
        key = key % alphabet.size();
        try (BufferedReader read = new BufferedReader(new FileReader(filePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + newFileNAme))) {
            while ((inputString = read.readLine()) != null) {
                writer.write(getOutputString(key, inputString, alphabet) + "\n");
            }
            printInformation(filePath, fileProcessed, newFileNAme);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void decryptCaesarCipher(String filePath, List<Character> alphabet, int key) {
        encryptCaesarCipher(filePath, alphabet, key * MULTIPLIER_FOR_DECRYPT);
    }

    public void decryptBruteForce(String filePath, List<Character> alphabet) {
        String testString = getTestString(filePath);
        int key = getKeyForBruteForce(testString, alphabet);
        decryptCaesarCipher(filePath, alphabet, key);
    }

    @NotNull
    static String getTestString(String filePath) {
        int reedString = REED_TEST_LINES;
        String inputString;
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader read = new BufferedReader(new FileReader(filePath))) {
            while (reedString > 0 && (inputString = read.readLine()) != null) {
                stringBuilder.append(inputString).append(" ");
                reedString--;
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int getKeyForBruteForce(String outputString, List<Character> alphabet) {
        int key = 0;
        for (int i = 0; i < alphabet.size(); i++) {
            int wordLength = 0;
            String  checkString = getOutputString(i * MULTIPLIER_FOR_DECRYPT, outputString, alphabet);
            String[] splitStringSpase = checkString.split(" ");
            for (String checkWord : splitStringSpase) {
                wordLength = Math.max(checkWord.length(), wordLength);
            }
            if (wordLength > MAX_CHARS_IN_WORD) {
                continue;
            }
            key = i;
        }
        return key;
    }

    private static String getOutputString(int key, String inputString, List<Character> alphabet) {
        char[] symbols = inputString.toCharArray();
        key = key % alphabet.size();
        for (int i = 0; i < symbols.length; i++) {
            char thisChar = symbols[i];
            if (alphabet.contains(thisChar)) {
                symbols[i] = alphabet.get((alphabet.size() + alphabet.indexOf(thisChar) + key) % alphabet.size());
            } else {
                symbols[i] = thisChar;
            }
        }
        return new String(symbols);
    }
}
