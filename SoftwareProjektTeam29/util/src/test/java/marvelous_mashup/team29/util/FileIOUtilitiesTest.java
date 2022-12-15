package marvelous_mashup.team29.util;

import org.junit.jupiter.api.Test;

import java.io.File;

import static marvelous_mashup.team29.util.FileIOUtilities.loadTextFromFile;
import static marvelous_mashup.team29.util.FileIOUtilities.writeTextToFile;
import static org.junit.jupiter.api.Assertions.*;

class FileIOUtilitiesTest {
    private static final String basicContent = "I'm just a test.\nDon't mind me.\nBye.";
    private static final File basicFile = new File("test.txt");


    @Test
    void basicFileIoTest() {

        String newContent = "I'm an even better test.\nYou first content shall be gone now.";
        String longContent = "hi.\n\n\n\n\n\n\n\n\n\n\n\n\nbye.";

        assertTrue(writeTextToFile(basicContent, basicFile));
        assertTrue(writeTextToFile(newContent, basicFile));
        assertEquals(newContent, loadTextFromFile(basicFile));

        assertTrue(basicFile.delete());
        assertNull(loadTextFromFile(basicFile));

        assertTrue(writeTextToFile(longContent, basicFile));
        assertEquals(longContent, loadTextFromFile(basicFile));
        assertTrue(basicFile.delete());
    }

    @Test
    void catchingErrorsTest() {
        assertFalse(writeTextToFile(null, basicFile));
        assertFalse(writeTextToFile(basicContent, null));
        assertNull(loadTextFromFile(null));

        File fileLostDirectory = new File ("thisDirectoryDoesNotExist/test.txt");
        assertFalse(writeTextToFile(basicContent, fileLostDirectory));
        assertNull(loadTextFromFile(fileLostDirectory));
        assertFalse(fileLostDirectory.delete());
    }

    @Test
    void differentFileFormatsTest() {
        File fileJson = new File ("test.json");
        File fileSvg = new File ("test.svg");

        assertTrue(writeTextToFile(basicContent, fileJson));
        assertEquals(basicContent, loadTextFromFile(fileJson));

        assertTrue(writeTextToFile(basicContent, fileSvg));
        assertEquals(basicContent, loadTextFromFile(fileSvg));

        assertTrue(fileJson.delete());
        assertTrue(fileSvg.delete());
    }
}