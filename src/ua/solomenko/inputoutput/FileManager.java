package ua.solomenko.inputoutput;

import java.io.*;

public class FileManager {

    private int countDir = 0;
    private int countFile = 0;

    // принимает путь к папке, возвращает количество файлов в папке и всех подпапках по пути
    public int countFiles(String path) {

        File root = new File(path);
        return fileCounter(root);
    }

    // принимает путь к папке, возвращает количество папок в папке и всех подпапках по пути
    public int countDirs(String path) {

        File root = new File(path);
        return directoryCounter(root);
    }

    // метод по копированию папок и файлов.
    // Параметр from - путь к файлу или папке, параметр to - путь к папке куда будет производиться копирование.
    public void copy(String from, String to) throws IOException {

        File pathFrom = new File(from);
        File pathTo = new File(to);
        copyAll(pathFrom, pathTo);
    }

    // метод по перемещению папок и файлов.
    // Параметр from - путь к файлу или папке, параметр to - путь к папке куда будет производиться перемещение.
    public void move(String from, String to) throws IOException {

        copy(from, to);
        File wayFrom = new File(from);
        do {
            delete(wayFrom);
        } while (wayFrom.exists());
    }

    private int fileCounter(File file) {

        String[] files = file.list();
        for (int i = 0 ; i < files.length ; i++) {
            File temp = new File(file, files[i]);
            if (temp.isDirectory()) {
                fileCounter(temp);
            } else {
                countFile++;
            }
        }
        return countFile;
    }

    private int directoryCounter(File file) {

        String[] files = file.list();
        for (int i = 0 ; i < files.length ; i++) {
            File temp = new File(file, files[i]);
            if (temp.isDirectory()) {
                countDir++;
                directoryCounter(temp);
            }
        }
        return countDir;
    }

    private void copyAll(File pathFrom, File pathTo) throws IOException {

        if (pathFrom.isDirectory()) {
            if (! pathTo.exists()) {
                pathTo.mkdirs();
            }

            String[] files = pathFrom.list();
            for (String file : files) {
                File fromTmp = new File(pathFrom, file);
                File toTemp = new File(pathTo, file);
                copyAll(fromTmp, toTemp);
            }

        } else {
            FileInputStream fileFrom = new FileInputStream(pathFrom);
            FileOutputStream fileTo = new FileOutputStream(pathTo);
            int read = fileFrom.read();
            while (read != - 1) {
                read = fileFrom.read();
                fileTo.write(read);
            }
            fileFrom.close();
            fileTo.close();
        }
    }

    void delete(File wayFrom) {

        if (wayFrom.isFile()) {
            wayFrom.delete();
        } else if (wayFrom.isDirectory()) {
            String[] files = wayFrom.list();
            if (files.length == 0) {
                wayFrom.delete();
            } else {
                for (String file : files) {
                    File temp = new File(wayFrom, file);
                    delete(temp);

                }
            }
        }
    }
}