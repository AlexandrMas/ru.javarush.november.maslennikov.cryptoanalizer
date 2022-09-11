package ru.javarush.november.maslennikov.cryptoanalizer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Tests {
    public static void main(String[] args) throws IOException {
        System.out.println(Files.exists(Paths.get("textRu.txt")));
        System.out.println(new File("textRu.txt").length());
    }
}
