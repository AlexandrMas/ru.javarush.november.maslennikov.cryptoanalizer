package ru.javarush.november.maslennikov.cryptoanalizer;

import java.io.*;
import java.util.List;

public class Cryptographer {

    public void encryptCaesarCipher(String file, List<Character> abc, int key) {
        key = key % abc.size();
        try (BufferedReader read = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(file + "_is_encrypted.txt"))) {
            String inputString;
            String outputString;
            while ((inputString = read.readLine()) != null) {
                char[] symbols = inputString.toCharArray();
                for (int i = 0; i < symbols.length; i++) {
                    char thisChar = symbols[i];
                    if (abc.contains(thisChar)) {
                        int index = (abc.indexOf(thisChar) + key) % abc.size();
                        symbols[i] = abc.get(index);
                    } else {
                        symbols[i] = thisChar;
                    }
                }
                outputString = new String(symbols);
                writer.write(outputString + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("The text is encrypted, written to a file with the name" + file + "_is_encrypted.txt\n");
    }
}
