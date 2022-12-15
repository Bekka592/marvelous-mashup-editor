package marvelous_mashup.team29.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Providing helper functions for dealing with Strings.
 */
public class StringUtilities {
    private StringUtilities() {
    }

    /**
     * Checks whether the list of strings contains any duplicates.
     * Is case sensitive.
     *
     * @param inputList of strings
     * @return list contains duplicates? (true/false)
     */
    public static boolean listContainsDuplicates(List<String> inputList) {
        Set<String> inputSet = new HashSet<>(inputList);
        return inputSet.size() < inputList.size();
    }

    /**
     * Splitting all lines that are longer than n characters into multiple lines.
     * Doesn't cut through words, only cuts lines at spaces.
     *
     * @param text          that needs formatting
     * @param maxLineLength any line with more than that many characters will be splittet, if it contains any spaces
     * @return formatted text
     */
    public static String formatText(String text, int maxLineLength) {
        String[] lines = text.split("(\r\n|\r|\n)");
        var result = new StringBuilder();
        for (String line : lines) {
            if (line.length() > maxLineLength && line.contains(" ")) {
                var lineCut = line.substring(0, maxLineLength + 1);
                int cutPosition;
                if (lineCut.contains(" ")) {
                    cutPosition = lineCut.lastIndexOf(" ");
                } else {
                    cutPosition = line.indexOf(" ");
                }
                String firstPart = line.substring(0, cutPosition) + "\n";
                var secondPart = line.substring(cutPosition + 1);
                secondPart = formatText(secondPart, maxLineLength);
                result.append(firstPart).append(secondPart).append("\n");
            } else {
                result.append(line).append("\n");
            }
        }
        return result.toString().trim();
    }
}
