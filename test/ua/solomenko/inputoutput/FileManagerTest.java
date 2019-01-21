package ua.solomenko.inputoutput;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class FileManagerTest {

    @Test
    public void testCountFiles() throws IOException {

        //prepare
        String path = "resources/from";

        //when
        int count = FileManager.countFiles(path);

        //then
        assertEquals(count, 2);
    }

    @Test
    public void testCountDirs()throws IOException {

        //prepare
        String path = "resources/from";

        //when
        int count = FileManager.countDirs(path);

        //then
        assertEquals(count, 2);
    }

    @Test
    public void testCopyFilesAndDirs() throws IOException {

        //prepare
        String fileFrom = "resources/from/file.txt";
        String dirFrom = "resources/from/directory";
        String to = "resources/to";

        //when
        FileManager.copy(fileFrom, to);
        FileManager.copy(dirFrom, to);

        //then
        assertEquals(FileManager.countFiles(to), 2);
        assertEquals(FileManager.countDirs(to), 2);
    }

    @Test
    public void testMove() throws IOException {

        //prepare
        String fromFile = "resources/to/file.txt";
        String toFile = "resources/from";
        String fromDir = "resources/to/directory";
        String path = "resources/to";

        //when
        FileManager.move(fromFile, toFile);
        FileManager.move(fromDir, toFile);

        //then
        assertEquals(FileManager.countFiles(path), 0);
        assertEquals(FileManager.countDirs(path), 0);

    }
}