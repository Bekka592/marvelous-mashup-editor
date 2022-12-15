package marvelous_mashup.team29.util;

import java.io.*;

/**
 * Providing helper functions for dealing with local files.
 */
public class FileIOUtilities {
    private FileIOUtilities() {
    }

    /**
     * Using basic java file-io techniques to write the given text to a file.
     *
     * @param text to write
     * @param file to save the text into
     * @return writing was successful? (true/false)
     */
    public static boolean writeTextToFile(String text, File file) {
        if (text == null || file == null) return false;
        try (var writer = new FileWriter(file.getAbsolutePath(), false);
             var bw = new BufferedWriter(writer)) {
            bw.write(text);
            bw.flush();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Using basic java file-io techniques to read the contents of the given file.
     *
     * @param file to read
     * @return contents of the file as a String or null if the parsing was unsuccessful
     */
    public static String loadTextFromFile(File file) {
        if (file == null) return null;
        var jsonString = new StringBuilder();
        try (var reader = new FileReader(file.getAbsolutePath());
             var br = new BufferedReader(reader)) {
            String line;
            while ((line = br.readLine()) != null) {
                jsonString.append(line).append("\n");
            }
        } catch (IOException e) {
            return null;
        }
        jsonString.deleteCharAt(jsonString.length() - 1); // deleting the last \n
        return jsonString.toString();
    }
}
