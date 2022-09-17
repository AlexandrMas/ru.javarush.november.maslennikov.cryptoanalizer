package ru.javarush.november.maslennikov.cryptoanalizer;

import java.io.*;
import java.util.List;

public class Decoder {

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

    public void decryptBruteForce(String file, List<Character> abc) throws IOException {
        int key;
        String inputLine;
        int containsPointsSpase;
        int containsVirguleSpase;
        int resultContains;
        int matchesToDecryptBigText = 25;
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader read = new BufferedReader(new FileReader(file))) {
            int quantityOfRowsToRead = 30;
            while (quantityOfRowsToRead > 0 && (inputLine = read.readLine()) != null) {
                stringBuilder.append(inputLine);
                quantityOfRowsToRead--;
            }
        }
        for (int i = 0; i < abc.size() * 2; i++) {
            String checkString = stringBuilder.toString();
            char[] symbols = checkString.toCharArray();
            int tempKey = i % abc.size();
            for (int j = 0; j < symbols.length; j++) {
                char thisChar = symbols[j];
                if (abc.contains(thisChar)) {
                    symbols[j] = abc.get((abc.size() + abc.indexOf(thisChar) - tempKey) % abc.size());
                } else {
                    symbols[j] = thisChar;
                }
            }
            checkString = new String(symbols);
            String[] splitStringPoints = checkString.split("\\. ");
            String[] splitStringVirgule = checkString.split(", ");
            containsPointsSpase = splitStringPoints.length;
            containsVirguleSpase = splitStringVirgule.length;
            resultContains = containsPointsSpase + containsVirguleSpase;
            if (resultContains > matchesToDecryptBigText) {
                key = i;
                decryptCaesarCipher(file, abc, key);
                break;
            } else if (i > abc.size()) {
                decryptBruteForceSmallText(file, abc);
                break;
            }
        }
    }

    private void decryptBruteForceSmallText(String file, List<Character> abc) throws IOException {
        int key;
        String inputLine;
        int containsPointsSpase;
        int containsVirguleSpase;
        int resultContains;
        int matchesToDecryptSmallTExt = 5;
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader read = new BufferedReader(new FileReader(file))) {
            int quantityOfRowsToRead = 25;
            while (quantityOfRowsToRead > 0 && (inputLine = read.readLine()) != null) {
                stringBuilder.append(inputLine);
                quantityOfRowsToRead--;
            }
        }
        for (int i = 0; i < abc.size() * 2; i++) {
            String checkString = stringBuilder.toString();
            char[] symbols = checkString.toCharArray();
            int tempKey = i % abc.size();
            for (int j = 0; j < symbols.length; j++) {
                char thisChar = symbols[j];
                if (abc.contains(thisChar)) {
                    symbols[j] = abc.get((abc.size() + abc.indexOf(thisChar) - tempKey) % abc.size());
                } else {
                    symbols[j] = thisChar;
                }
            }
            checkString = new String(symbols);
            String[] splitStringPoints = checkString.split("\\. ");
            String[] splitStringVirgule = checkString.split(", ");
            containsPointsSpase = splitStringPoints.length;
            containsVirguleSpase = splitStringVirgule.length;
            resultContains = containsPointsSpase + containsVirguleSpase;
            if (resultContains > matchesToDecryptSmallTExt) {
                key = i;
                decryptCaesarCipher(file, abc, key);
                break;
            }
        }
    }
}
