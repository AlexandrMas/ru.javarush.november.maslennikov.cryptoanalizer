package ru.javarush.november.maslennikov.cryptoanalizer;

import java.io.*;
import java.util.List;

public class Decoder {

    private static final int MATCHES_TO_DECRYPT_BIG_TEXT = 70;

    private static final int MATCHES_TO_DECRYPT_SMALL_TEXT = 40;

    private static final int QUANTITY_OF_ROWS_TO_READ = 20;

    public void decryptCaesarCipher(String file, List<Character> abc, int key) {
        key = key % abc.size();
        try (BufferedReader read = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(file + "_is_decrypted.txt"))) {
            String inputString;
            String outputString;
            while ((inputString = read.readLine()) != null) {
                char[] arrayChar = inputString.toCharArray();
                for (int i = 0; i < arrayChar.length; i++) {
                    char thisChar = arrayChar[i];
                    if (abc.contains(thisChar)) {
                        arrayChar[i] = abc.get((abc.size() + abc.indexOf(thisChar) - key) % abc.size());
                    } else {
                        arrayChar[i] = thisChar;
                    }
                }
                outputString = new String(arrayChar);
                writer.write(outputString + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("The text is decrypted, written to a file with the name " + file + "_is_decrypted.txt\n");
    }

    public void decryptBruteForce(String file, List<Character> abc) {
        String inputLine;
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader read = new BufferedReader(new FileReader(file))) {
            int reedString = QUANTITY_OF_ROWS_TO_READ;
            while (reedString > 0 && (inputLine = read.readLine()) != null) {
                stringBuilder.append(inputLine);
                reedString--;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int key = getKey(stringBuilder, abc);

        decryptCaesarCipher(file, abc, key);
    }

    private static int getKey(StringBuilder stringBuilder, List<Character> abc) {
        int key = 0;
        for (int i = 0; i < abc.size() * 2; i++) {
            String checkString = getCheckString(i, stringBuilder, abc);
            String[] splitStringSpase = checkString.split(" ");
            String[] splitStringPoints = checkString.split("\\. ");
            String[] splitStringVirgule = checkString.split(", ");
            int containsSpase = splitStringSpase.length;
            int containsPointsSpase = splitStringPoints.length;
            int containsVirguleSpase = splitStringVirgule.length;
            int resultContains = containsSpase + containsPointsSpase + containsVirguleSpase;
            if (resultContains > MATCHES_TO_DECRYPT_BIG_TEXT) {
                key = i;
                break;
            } else if (resultContains > MATCHES_TO_DECRYPT_SMALL_TEXT) {
                key = i;
            }
        }
        return key;
    }

    private static String getCheckString(int key, StringBuilder stringBuilder, List<Character> abc) {
        String checkString = stringBuilder.toString();
        char[] symbols = checkString.toCharArray();
        key = key % abc.size();
        for (int i = 0; i < symbols.length; i++) {
            char thisChar = symbols[i];
            if (abc.contains(thisChar)) {
                symbols[i] = abc.get((abc.size() + abc.indexOf(thisChar) - key) % abc.size());
            } else {
                symbols[i] = thisChar;
            }
        }
        checkString = new String(symbols);
        return checkString;
    }
}

