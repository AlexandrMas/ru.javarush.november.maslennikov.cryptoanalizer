package ru.javarush.november.maslennikov.cryptoanalizer;

import java.io.*;
import java.util.List;

public class Encrypt {

    public void encryptCaesarCipher(String file, List<Character> abc, int key) throws IOException {
        key = key % abc.size();
        try (BufferedReader read = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter("encryptedFile.txt"))) {
            String lineD;
            String lineE = "";
            while ((lineD = read.readLine()) != null) {
                char[] arrayChar = lineD.toCharArray();
                for (int i = 0; i < arrayChar.length; i++) {
                    char c = arrayChar[i];
                    if (abc.contains(c)) {
                        int index = (abc.indexOf(c) + key) % abc.size();
                        arrayChar[i] = abc.get(index);
                    } else {
                        arrayChar[i] = c;
                    }
                    lineE = new String(arrayChar);
                }
                writer.write(lineE + "\n");
            }
        }
        System.out.println("The text is encrypted, written to a file with the name encryptedFile.txt\n");
    }
}
