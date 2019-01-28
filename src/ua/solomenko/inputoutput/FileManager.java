package ua.solomenko.inputoutput;

import java.io.*;

public class FileManager {

    // принимает путь к папке, возвращает количество файлов в папке и всех подпапках по пути
    public static int countFiles(String path) throws IOException {
        validatePath(path);
        File root = new File(path);
        int counter = 0;
        if(root.isDirectory()){
            String[] files = root.list();
            for(String file : files){
                File temp = new File(path, file);
                if(temp.isDirectory()){
                    counter +=countFiles(temp.getPath());
                }else {
                    counter++;
                }
            }
        }else {
            throw new IOException("Path is not to directory.");
        }
        return counter;
    }

    // принимает путь к папке, возвращает количество папок в папке и всех подпапках по пути
    public static int countDirs(String path) throws IOException {
        validatePath(path);
        File root = new File(path);
        int counter = 0;
        if(root.isDirectory()){
            String[] files = root.list();
            for(String file : files){
                File temp = new File(path, file);
                if(temp.isDirectory()){
                    counter++;
                    counter +=countDirs(temp.getPath());
                }
            }
        }else {
            throw new IOException("Path is not to directory.");
        }
        return counter;
    }

    // метод по копированию папок и файлов.
    // Параметр from - путь к файлу или папке, параметр to - путь к папке куда будет производиться копирование.
    public static void copy(String from, String to) throws IOException {
        validatePath(from);
        validatePath(to);
        validateIsDir(to);
        File pathFrom = new File(from);
        if (pathFrom.isDirectory()){
            File pathTo = new File(to, pathFrom.getName());
            pathTo.mkdir();
            copyContent(pathFrom.getPath(), pathTo.getPath());
        }else {
            try (FileInputStream fileFrom = new FileInputStream(from);
                 FileOutputStream fileTo = new FileOutputStream(to +"/" +pathFrom.getName())){
                int read;
                while ((read = fileFrom.read()) != - 1) {
                    fileTo.write(read);
                }
            }
        }
    }

    // метод по перемещению папок и файлов.
    // Параметр from - путь к файлу или папке, параметр to - путь к папке куда будет производиться перемещение.
    public static void move(String from, String to) throws IOException {
        validatePath(from);
        validatePath(to);
        validateIsDir(to);
        File pathFrom = new File(from);
        if (pathFrom.isDirectory()){
            File pathTo = new File(to, pathFrom.getName());
            pathTo.mkdir();
            String[] files = pathFrom.list();
            if(files.length == 0){
                pathFrom.delete();
            }else {
                copyContent(pathFrom.getPath(), pathTo.getPath());
                do{
                    delete(pathFrom);
                }while (pathFrom.exists());
            }
        }else {
            try (FileInputStream fileFrom = new FileInputStream(from);
                 FileOutputStream fileTo = new FileOutputStream(to +"/" +pathFrom.getName())){
                int read;
                while ((read = fileFrom.read()) != - 1) {
                    fileTo.write(read);
                    pathFrom.delete();
                }
            }
        }
    }

    private static void copyContent(String from, String to) throws IOException {
        File pathFrom = new File(from);
        File pathTo = new File(to);
        if(pathFrom.isDirectory()){
            String[] files = pathFrom.list();
            for (String file : files) {
                File fileFrom = new File(pathFrom, file);
                File fileTo = new File(pathTo, file);
                if (fileFrom.isDirectory()) {
                    fileTo.mkdir();
                }
                copyContent(fileFrom.getPath(), fileTo.getPath());
            }
        }else {
            try (FileInputStream fileFrom = new FileInputStream(pathFrom) ;
                 FileOutputStream fileTo = new FileOutputStream(pathTo)) {
                int read;
                while ((read = fileFrom.read()) != - 1) {
                    fileTo.write(read);
                }
            }
        }
    }

    private static void delete(File pathFrom) {

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

    private static void validatePath(String path) throws IOException{
        File file = new File(path);
        if(!file.exists()){
            throw new IOException("File does not exist.");
        }
    }

    private static void validateIsDir(String path) throws IOException{
        File file = new File(path);
        if(!file.isDirectory()){
            throw new IOException("Path is not to directory.");
        }
    }
}