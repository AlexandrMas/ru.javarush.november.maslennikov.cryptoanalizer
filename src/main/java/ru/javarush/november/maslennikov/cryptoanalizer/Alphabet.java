package ru.javarush.november.maslennikov.cryptoanalizer;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.*;

public class Alphabet {

    public List<Character> getRuAlphabet() {
        return ruAlphabet;
    }

    public List<Character> getUaAlphabet() {
        return uaAlphabet;
    }

    public List<Character> getEnAlphabet() {
        return enAlphabet;
    }

    private final List<Character> ruAlphabet = Arrays.asList
            ('а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й',
                    'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф',
                    'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я',
                    'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й',
                    'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф',
                    'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я',
                    '(', '.', ',', '”', ':', '-', '!', '?', ' ', ')');

    private final List<Character> enAlphabet = Arrays.asList
            ('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                    'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                    'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                    '(', '.', ',', '”', ':', '-', '!', '?', ' ', ')');

    private final List<Character> uaAlphabet = Arrays.asList
            ('а', 'б', 'в', 'г', 'ґ', 'д', 'е', 'є', 'ж', 'з', 'и',
                    'і', 'ї', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с',
                    'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ь', 'ю', 'я',
                    'А', 'Б', 'В', 'Г', 'Ґ', 'Д', 'Е', 'Є', 'Ж', 'З', 'И',
                    'І', 'Ї', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С',
                    'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ь', 'Ю', 'Я',
                    '(', '.', ',', '”', ':', '-', '!', '?', ' ', ')');

    List<Character> createAlphabet(String filePath) {
        List<Character> alphabet = new ArrayList<>();
        String inputString;
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader read = new BufferedReader(new FileReader(filePath))) {
            while ((inputString = read.readLine()) != null) {
                stringBuilder.append(inputString);
            }
            char[] symbols = stringBuilder.toString().toCharArray();
            for (char symbol : symbols) {
                alphabet.add(symbol);
            }
            return alphabet;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    List<Character> toIdentifyAlphabet(String filePath) {
        Map<List<Character>, Integer> statistic = getStatisticOfAlphabet(filePath);
        int determinant = Math.max(statistic.get(getRuAlphabet()),
                Math.max(statistic.get(getUaAlphabet()), statistic.get(getEnAlphabet())));
        List<Character> alphabet = null;
        for (Map.Entry<List<Character>, Integer> pair : statistic.entrySet()) {
            if (determinant == pair.getValue()) {
                alphabet = pair.getKey();
            }
        }
        return alphabet;
    }

    @NotNull
    private Map<List<Character>, Integer> getStatisticOfAlphabet(String filePath) {
        Map<List<Character>, Integer> statistic = new HashMap<>();
        statistic.put(getRuAlphabet(), 0);
        statistic.put(getUaAlphabet(), 0);
        statistic.put(getEnAlphabet(), 0);
        char[] symbols = Handler.getTestString(filePath).toCharArray();
        for (char thisSymbol : symbols) {
            if (getRuAlphabet().contains(thisSymbol)) {
                statistic.put(getRuAlphabet(), statistic.get(getRuAlphabet()) + 1);
            }
            if (getUaAlphabet().contains(thisSymbol)) {
                statistic.put(getUaAlphabet(), statistic.get(getUaAlphabet()) + 1);
            }
            if (getEnAlphabet().contains(thisSymbol)) {
                statistic.put(getEnAlphabet(), statistic.get(getEnAlphabet()) + 1);
            }
        }
        return statistic;
    }
}
