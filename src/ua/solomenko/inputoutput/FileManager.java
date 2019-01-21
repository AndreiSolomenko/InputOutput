package ua.solomenko.inputoutput;

import java.io.*;

public class FileManager {

    // принимает путь к папке, возвращает количество файлов в папке и всех подпапках по пути
    public static int countFiles(String path) throws IOException {

        int counter = 0;
        File root = new File(path);
        validatePath(root);

        String[] files = root.list();
        for (String file : files) {
            File temp = new File(root, file);
            if (temp.isDirectory()) {
                counter += countFiles(temp.getPath());
            } else {
                counter++;
            }
        }
        return counter;
    }

    // принимает путь к папке, возвращает количество папок в папке и всех подпапках по пути
    public static int countDirs(String path) throws IOException {

        int counter = 0;
        File root = new File(path);
        validatePath(root);

        String[] files = root.list();
        for (int i = 0 ; i < files.length ; i++) {
            File temp = new File(root, files[i]);
            if (temp.isDirectory()) {
                counter++;
                counter += countDirs(temp.getPath());
            }
        }
        return counter;
    }

    // метод по копированию папок и файлов.
    // Параметр from - путь к файлу или папке, параметр to - путь к папке куда будет производиться копирование.
    public static void copy(String from, String to) throws IOException {

        File pathFrom = new File(from);
        File pathTo = new File(to);

        if (pathFrom.isDirectory()){
            String[] files = pathFrom.list();
            for (String file : files){
                File fileFrom = new File(pathFrom, file);
                File fileTo = new File(pathTo, file);
                if (fileFrom.isDirectory()){
                    fileTo.mkdir();
                }
                copy(fileFrom.getPath(), fileTo.getPath());
            }
        } else{
            if(pathFrom.isFile() && pathTo.isDirectory()){
                try (FileInputStream fileFrom = new FileInputStream(pathFrom);
                     FileOutputStream fileTo = new FileOutputStream(pathTo +"/" +pathFrom.getName())){
                    int read = fileFrom.read();
                    while (read != - 1) {
                        read = fileFrom.read();
                        fileTo.write(read);
                    }
                }
            }else {
               try (FileInputStream fileFrom = new FileInputStream(pathFrom);
                    FileOutputStream fileTo = new FileOutputStream(pathTo)){
                   int read = fileFrom.read();
                   while (read != - 1) {
                       read = fileFrom.read();
                       fileTo.write(read);
                    }
                }
            }
        }
    }

    // метод по перемещению папок и файлов.
    // Параметр from - путь к файлу или папке, параметр to - путь к папке куда будет производиться перемещение.
    public static void move(String from, String to) throws IOException {

        copy(from, to);
        File pathFrom = new File(from);
        validateExists(pathFrom);
        do {
            delete(pathFrom);
        } while (pathFrom.exists());
    }

    static void delete(File pathFrom) {

        if (pathFrom.isFile()) {
            pathFrom.delete();
        } else if (pathFrom.isDirectory()) {
            String[] files = pathFrom.list();
            if (files.length == 0) {
                pathFrom.delete();
            } else {
                for (String file : files) {
                    File temp = new File(pathFrom, file);
                    delete(temp);

                }
            }
        }
    }

    private static void validatePath(File root) throws IOException {
        if (! root.exists()) {
            throw new IOException("Directory does not exist.");
        } else if (! root.isDirectory()) {
            throw new IOException("Path not to directory.");
        }
    }

    private static void validateExists(File root) throws IOException {
        if (! root.exists()) {
            throw new IOException("Directory does not exist.");
        }
    }
}