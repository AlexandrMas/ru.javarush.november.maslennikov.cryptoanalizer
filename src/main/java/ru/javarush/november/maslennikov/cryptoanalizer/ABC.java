package ru.javarush.november.maslennikov.cryptoanalizer;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ABC {

    public List<Character> getRU() {
        return RU;
    }

    public List<Character> getUA() {
        return UA;
    }

    public List<Character> getEN() {
        return EN;
    }

    private final List<Character> RU = Arrays.asList

            ('а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й',
                    'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф',
                    'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я',
                    'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й',
                    'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф',
                    'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я',
                    '(', '.', ',', '”', ':', '-', '!', '?', ' ', ')');
    private final List<Character> EN = Arrays.asList

            ('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                    'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                    'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                    '(', '.', ',', '”', ':', '-', '!', '?', ' ', ')');
    private final List<Character> UA = Arrays.asList

            ('а', 'б', 'в', 'г', 'ґ', 'д', 'е', 'є', 'ж', 'з', 'и',
                    'і', 'ї', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с',
                    'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ь', 'ю', 'я',
                    'А', 'Б', 'В', 'Г', 'Ґ', 'Д', 'Е', 'Є', 'Ж', 'З', 'И',
                    'І', 'Ї', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С',
                    'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ь', 'Ю', 'Я',
                    '(', '.', ',', '”', ':', '-', '!', '?', ' ', ')');

    public List<Character> createAbc(String file) throws IOException {

        List<Character> abc = new ArrayList<>();

        try (BufferedReader read = new BufferedReader(new FileReader(file))) {
            String inputString;
            while ((inputString = read.readLine()) != null) {
                char[] symbols = inputString.toCharArray();
                for (char c : symbols) {
                    abc.add(c);
                }
            }
        }
        return abc;
    }
}
