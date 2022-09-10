package ru.javarush.november.maslennikov.cryptoanalizer;

import java.io.*;
import java.util.List;

public class Decrypt {

    private Decrypt() {
    }

    static void decryptCaesar(String file, List abc, int key) throws IOException {

        key = key % abc.size();
        try (BufferedReader read = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter("decryptedFile.txt"))) {
            String lineE;
            String lineD = "";
            while ((lineE = read.readLine()) != null) {
                char[] arrayChar = lineE.toCharArray();
                for (int i = 0; i < arrayChar.length; i++) {
                    char c = arrayChar[i];
                    if (abc.contains(c)) {
                        arrayChar[i] = (char) abc.get((abc.size() + abc.indexOf(c) - key) % abc.size());
                    } else {
                        arrayChar[i] = c;
                    }
                    lineD = new String(arrayChar);
                }
                writer.write(lineD + "\n");
            }
        }
    }
}
