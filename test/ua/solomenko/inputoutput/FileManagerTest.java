package ua.solomenko.inputoutput;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class FileManagerTest {

    private FileManager fileManager = new FileManager();

    @Test
    public void testCountFiles() throws IOException {

        //prepare
        String path = "resources/from";

        //when
        int count = fileManager.countFiles(path);

        //then
        assertEquals(count, 2);
    }

    @Test
    public void testCountDirs() {

        //prepare
        String path = "resources/from";

        //when
        int count = fileManager.countDirs(path);

        //then
        assertEquals(count, 2);
    }

    @Test
    public void testCopyFilesAndDirs() throws IOException {

        //prepare
        String from = "resources/from";
        String to = "resources/to";

        //when
        fileManager.copy(from, to);

        //then
        assertEquals(fileManager.countFiles(to), 2);
        assertEquals(fileManager.countDirs(to), 2);
    }

    @Test
    public void testMove() throws IOException {

        //prepare
        String fromFile = "resources/from/file.txt";
        String toFile = "resources/to/file.txt";
        String fromDir = "resources/from/directory";
        String toDir = "resources/to/directory";
        String path = "resources/from";

        //when
        fileManager.move(fromFile, toFile);
        fileManager.move(fromDir, toDir);

        //then
        assertEquals(fileManager.countFiles(path), 0);
        assertEquals(fileManager.countDirs(path), 0);

        //return
        fileManager.copy(toFile, fromFile);
        fileManager.copy(toDir, fromDir);
    }
}