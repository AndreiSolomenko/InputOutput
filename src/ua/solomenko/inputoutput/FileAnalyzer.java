package ua.solomenko.inputoutput;

import java.io.*;

public class FileAnalyzer {

    // Написать программу FileAnalyzer, которая в консоли принимает 2 параметра:
    // 1) путь к файлу
    // 2) слово
    // Usage:
    // java FileAnalyzer C:/test/story.txt duck
    // Выводит:
    // 1) Кол-во вхождений искомого слова в файле
    // 2) Все предложения содержащие искомое слово
    // (предложение заканчивается символами ".", "?", "!"), каждое преждложение с новой строки.

    public static void main(String[] args) throws IOException {

        String path = args[0];
        String word = args[1];

        System.out.println(countDuplicates(path, word));
        printSentences(path, word);

    }

    static int countDuplicates(String path, String word) throws IOException {

        byte[] text;
        try (FileInputStream inputStream = new FileInputStream(path)) {
            text = new byte[inputStream.available()];
            inputStream.read(text);
        }
        String[] words = new String(text).split(" ");
        int counter = 0;
        for (String wd : words) {
            if (wd.equalsIgnoreCase(word)) {
                counter++;
            }
        }
        return counter;
    }

    static private void printSentences(String path, String word) throws IOException {

        for (String sentence : findSentences(path, word)) {
            if (sentence != null) {
                System.out.println(sentence);
            }
        }
    }

    static String[] findSentences(String path, String word) throws IOException {

        byte[] text;
        try (FileInputStream inputStream = new FileInputStream(path)) {
            text = new byte[inputStream.available()];
            inputStream.read(text);
        }
        String[] sentences = new String(text).split("\\.|\\?|\\!");
        String[] temp = new String[sentences.length];
        for (int i = 0 ; i < sentences.length ; i++) {
            String[] words = sentences[i].split(" ");
            for (String wd : words) {
                if (wd.equalsIgnoreCase(word)) {
                    temp[i] = sentences[i];
                }
            }
        }
        return temp;
    }
}