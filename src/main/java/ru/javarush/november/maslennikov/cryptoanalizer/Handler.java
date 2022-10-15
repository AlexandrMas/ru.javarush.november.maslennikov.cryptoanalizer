package ru.javarush.november.maslennikov.cryptoanalizer;

import java.io.*;
import java.util.List;

public class Handler {

    private static final int QUANTITY_OF_ROWS_TO_READ = 20;

    private static final int MAX_CHARS_IN_WORD = 30;

    private static final int MULTIPLIER_FOR_BRUTE_FORCE = -1;

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
        key = key % alphabet.size();
        try (BufferedReader read = new BufferedReader(new FileReader(filePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + newFileNAme))) {
            String inputString;
            while ((inputString = read.readLine()) != null) {
                String outputString = getOutputString(key, inputString, alphabet);
                writer.write(outputString + "\n");
            }
            printInformation(filePath, fileProcessed, newFileNAme);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void decryptCaesarCipher(String filePath, List<Character> alphabet, int key) {
        encryptCaesarCipher(filePath, alphabet, key * MULTIPLIER_FOR_BRUTE_FORCE);
    }

    public void decryptBruteForce(String filePath, List<Character> alphabet) {
        String inputString;
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader read = new BufferedReader(new FileReader(filePath))) {
            int reedString = QUANTITY_OF_ROWS_TO_READ;
            while (reedString > 0 && (inputString = read.readLine()) != null) {
                stringBuilder.append(inputString).append("\n");
                reedString--;
            }
            String outputString = stringBuilder.toString();
            int key = getKeyForBruteForce(outputString, alphabet);
            decryptCaesarCipher(filePath, alphabet, key);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int getKeyForBruteForce(String outputString, List<Character> alphabet) {
        int key = 0;
        for (int i = 0; i < alphabet.size(); i++) {
            int wordLength = 0;
            String checkString = getOutputString(i * MULTIPLIER_FOR_BRUTE_FORCE, outputString, alphabet);
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
        inputString = new String(symbols);
        return inputString;
    }
}
