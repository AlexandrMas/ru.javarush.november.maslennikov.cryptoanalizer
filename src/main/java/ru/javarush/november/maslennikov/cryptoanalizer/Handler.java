package ru.javarush.november.maslennikov.cryptoanalizer;

import java.io.*;
import java.util.List;

public class Handler {

    private static final int QUANTITY_OF_ROWS_TO_READ = 20;

    private static final int MAX_CHARS_IN_WORD = 30;

    private static final int MULTIPLIER_FOR_BRUTE_FORCE = -1;

    private static final String DECRYPT = "decrypted";

    private static final String ENCRYPT = "encrypted";

    private static final String NEW_NAME_DECRYPTED = "_is_decrypted.txt";

    private static final String NEW_NAME_ENCRYPTED = "_is_encrypted.txt";

    public void encryptCaesarCipher(String file, List<Character> abc, int key) {
        String fileProcessed;
        String newFileNAme;
        if (key > 0) {
            fileProcessed = ENCRYPT;
            newFileNAme = NEW_NAME_ENCRYPTED;
        } else {
            fileProcessed = DECRYPT;
            newFileNAme = NEW_NAME_DECRYPTED;
        }
        key = key % abc.size();
        try (BufferedReader read = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(file + newFileNAme))) {
            String inputString;
            while ((inputString = read.readLine()) != null) {
                String outputString = getOutputString(key, inputString, abc);
                writer.write(outputString + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("The text is " + fileProcessed + ", written to a file with the name " + file + newFileNAme + "\n");
    }

    public void decryptCaesarCipher(String file, List<Character> abc, int key) {
        encryptCaesarCipher(file, abc, key * MULTIPLIER_FOR_BRUTE_FORCE);
    }

    public void decryptBruteForce(String file, List<Character> abc) {
        String inputString;
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader read = new BufferedReader(new FileReader(file))) {
            int reedString = QUANTITY_OF_ROWS_TO_READ;
            while (reedString > 0 && (inputString = read.readLine()) != null) {
                stringBuilder.append(inputString).append("\n");
                reedString--;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String outputString = stringBuilder.toString();
        int key = getKeyForBruteForce(outputString, abc);
        decryptCaesarCipher(file, abc, key);
    }

    private static int getKeyForBruteForce(String outputString, List<Character> abc) {
        int key = 0;
        for (int i = 0; i < abc.size(); i++) {
            int wordLength = 0;
            String checkString = getOutputString(i * MULTIPLIER_FOR_BRUTE_FORCE, outputString, abc);
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

    private static String getOutputString(int key, String inputString, List<Character> abc) {
        char[] symbols = inputString.toCharArray();
        key = key % abc.size();
        for (int i = 0; i < symbols.length; i++) {
            char thisChar = symbols[i];
            if (abc.contains(thisChar)) {
                symbols[i] = abc.get((abc.size() + abc.indexOf(thisChar) + key) % abc.size());
            } else {
                symbols[i] = thisChar;
            }
        }
        inputString = new String(symbols);
        return inputString;
    }
}
