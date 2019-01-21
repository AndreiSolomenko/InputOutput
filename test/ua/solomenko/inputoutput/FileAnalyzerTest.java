package ua.solomenko.inputoutput;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class FileAnalyzerTest {

    @Test
    public void testDuplicateCounter() throws IOException {

        //when
        int count = FileAnalyzer.countDuplicates("resources/story.txt", "duck");

        //then
        assertEquals(count, 17);
    }

    @Test
    public void testPrintSentences() throws IOException {

        //when
        String[] stringCounter = FileAnalyzer.findSentences("resources/story.txt", "duck");

        //then
        assertEquals(stringCounter.length, 16);
    }
}