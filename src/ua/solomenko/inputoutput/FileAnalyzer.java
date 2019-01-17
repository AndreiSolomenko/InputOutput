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

        System.out.println(duplicateCounter(path, word));
        printSentences(path, word);

    }

    static int duplicateCounter(String path, String word) throws IOException {

        FileInputStream inputStream = new FileInputStream(path);

        byte[] text = new byte[inputStream.available()];
        inputStream.read(text);
        inputStream.close();
        String[] lines = new String(text).split(" ");

        int counter = 0;
        for (String s : lines) {
            String[] words = s.split(" ");
            for (String w : words) {
                if (w.equalsIgnoreCase(word)) {
                    counter++;
                }
            }
        }
        return counter;
    }

    static void printSentences(String path, String word) throws IOException {

        for (String Sentence : findSentences(path, word)) {
            if (Sentence != null) {
                System.out.println(Sentence);
            }
        }
    }

    static String[] findSentences(String path, String word) throws IOException {

        FileInputStream inputStream = new FileInputStream(path);
        byte[] text = new byte[inputStream.available()];
        inputStream.read(text);
        inputStream.close();
        String[] lines = new String(text).split("\\.|\\?|\\!");
        String[] temp = new String[lines.length];
        for (int i = 0 ; i < lines.length ; i++) {
            String[] words = lines[i].split(" ");
            for (String w : words) {
                if (w.equalsIgnoreCase(word)) {
                    temp[i] = lines[i];
                }
            }
        }
        return temp;
    }
}