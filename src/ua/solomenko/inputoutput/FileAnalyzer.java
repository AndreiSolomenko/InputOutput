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

        String path = "resources/story.txt";
        String word = "duck";

        if (args.length > 0) {
            path = args[0];
            word = args[1];
        }

        System.out.println("Amount of coincidences the search word in the text: " +countDuplicates(path, word));
        printSentences(path, word);

    }

    static int countDuplicates(String path, String word) throws IOException {
        validatePath(path);
        File root = new File(path);
        char[] text;
        try (FileReader file = new FileReader(path)) {
            text = new char[(int) root.length()];
            file.read(text);
        }
        int counter = 0;
        String[] words = new String(text).split(" ");
        for (String wd : words) {
            if (wd.equalsIgnoreCase(word)) {
                counter++;
            }
        }
        return counter;
    }

    static void printSentences(String path, String word) throws IOException {
        validatePath(path);
        String[] sentences = findSentences(path, word);
        for (String sentence : sentences){
            if (sentence != null){
                System.out.println(sentence);
            }
        }
    }

    static String[] findSentences(String path, String word) throws IOException {
        validatePath(path);
        File fileLength = new File(path);
        char[] text;
        try (FileReader file = new FileReader(path)) {
            text = new char[(int) fileLength.length()];
            file.read(text);
        }
        String[] sentences = new String(text).split("\\.|\\?|\\!");
        String[] temp = new String[sentences.length];
        for (int i = 0; i<sentences.length; i++){
            String[] words = sentences[i].split(" ");
            for (String wd : words){
                if(wd.equalsIgnoreCase(word)){
                    temp[i] = sentences[i];
                }
            }
        }
        return temp;
    }

    static private void validatePath(String path) throws IOException{
        File file = new File(path);
        if(!file.exists()){
            throw new IOException("File does not exist.");
        }
    }
}