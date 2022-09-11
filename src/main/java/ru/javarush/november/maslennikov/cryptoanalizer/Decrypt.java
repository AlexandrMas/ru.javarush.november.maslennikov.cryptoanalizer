package ru.javarush.november.maslennikov.cryptoanalizer;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class Decrypt {

    public void decryptCaesarCipher(String file, List<Character> abc, int key)
            throws IOException {
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
                        arrayChar[i] = abc.get((abc.size() + abc.indexOf(c) - key) % abc.size());
                    } else {
                        arrayChar[i] = c;
                    }
                    lineD = new String(arrayChar);
                }
                writer.write(lineD + "\n");
            }
        }
    }

    public void decryptBruteForce(String file, List<Character> abc)
            throws IOException {
        int length1;
        int length2;
        int result;
        int key;

        for (int i = 0; ; i++) {
            new Decrypt().decryptCaesarCipher(file, abc, i);
            try (BufferedReader read = new BufferedReader(new FileReader("decryptedFile.txt"));
                 BufferedWriter writer = new BufferedWriter(new FileWriter("temple.txt"))) {
                String lineE = "";
                int countString = 30;
                while (countString > 0 || (lineE = read.readLine()) != null) {
                    writer.write(lineE);
                    countString--;
                }
            }
            try (BufferedReader read = new BufferedReader(new FileReader("temple.txt"))) {
                String readLine = read.readLine();
                String[] arrayString = readLine.split("\\. ");
                String line = Arrays.toString(arrayString);
                String[] arrayString1 = line.split(", ");
                length1 = arrayString.length;
                length2 = arrayString1.length;
            }
            result = length1 + length2;

            if (result > 15) {
                key = i;
              new  Decrypt().decryptCaesarCipher(file, abc, key);
                break;
            } else if (i > 500) {
                new Decrypt().decryptBruteForceSmallText(file, abc);
                break;
            }
        }
    }

    private void decryptBruteForceSmallText(String file, List<Character> abc)
            throws IOException {
        int length1;
        int length2;
        int result;
        int key;

        for (int i = 0; ; i++) {
            new Decrypt().decryptCaesarCipher(file, abc, i);
            try (BufferedReader read = new BufferedReader(new FileReader("decryptedFile.txt"));
                 BufferedWriter writer = new BufferedWriter(new FileWriter("temple.txt"))) {
                String lineE = "";
                int countString = 30;
                while (countString > 0 || (lineE = read.readLine()) != null) {
                    writer.write(lineE);
                    countString--;
                }
            }
            try (BufferedReader read = new BufferedReader(new FileReader("temple.txt"))) {
                String readLine = read.readLine();
                String[] arrayString = readLine.split("\\. ");
                String line = Arrays.toString(arrayString);
                String[] arrayString1 = line.split(", ");
                length1 = arrayString.length;
                length2 = arrayString1.length;
            }
            result = length1 + length2;

            if (result > 5) {
                key = i;
                new Decrypt().decryptCaesarCipher(file, abc, key);
                break;
            }
        }
    }
}




