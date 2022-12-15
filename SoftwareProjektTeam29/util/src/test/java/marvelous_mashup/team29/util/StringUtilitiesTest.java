package marvelous_mashup.team29.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class StringUtilitiesTest {
    static Stream<Arguments> duplicatesValueProvider() {
        return Stream.of(
                arguments(new LinkedList<>(List.of("Apple", "pear", "ApPle")), false),
                arguments(new LinkedList<>(List.of("Apple", "pear", "Banana")), false),
                arguments(new LinkedList<>(List.of("banana", "banana", "banana")), true),
                arguments(new LinkedList<>(List.of("noDuplicate", "banana", "noDuplicate")), true)
        );
    }

    @ParameterizedTest
    @MethodSource("duplicatesValueProvider")
    void listContainsDuplicatesTest(List<String> list, boolean result) {
        boolean containsDuplicates = StringUtilities.listContainsDuplicates(list);
        assertEquals(result, containsDuplicates);
    }

    static Stream<Arguments> formatTextValueProvider() {
        return Stream.of(
                arguments("jduweidijsauxxioksksxa", 4, "jduweidijsauxxioksksxa"),
                arguments("Hello, I am a really nice example text. Just here to exist.", 1, "Hello,\nI\nam\na\nreally\nnice\nexample\ntext.\nJust\nhere\nto\nexist."),
                arguments("Hello, I am a really nice example text. Just here to exist.", 8, "Hello, I\nam a\nreally\nnice\nexample\ntext.\nJust\nhere to\nexist."),
                arguments("Hello, I am a really nice example text. Just here to exist.", 15, "Hello, I am a\nreally nice\nexample text.\nJust here to\nexist."),
                arguments("extreeeeemly weird text", 10, "extreeeeemly\nweird text")
        );
    }

    @ParameterizedTest
    @MethodSource("formatTextValueProvider")
    void formatTextTest(String text, int maxLineLength, String result) {
        String formattedText = StringUtilities.formatText(text, maxLineLength);
        assertEquals(result, formattedText);
    }
}