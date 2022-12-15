package marvelous_mashup.team29.util.parsing;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ValidatorTest {

    static Stream<Arguments> jsonFileValueProviderValid() {
        return Stream.of(
                arguments("json/character-config/valid_marvelheroes.character.json"),
                arguments("json/match-config/valid_basic.game.json"),
                arguments("json/scenario-config/valid_asgard.scenario.json")
        );
    }

    @ParameterizedTest
    @MethodSource("jsonFileValueProviderValid")
    void testValidJson(String filePath) throws FileNotFoundException {
        Validator validator = Validator.getTestInstance();
        Reader in = new FileReader(filePath);
        assertTrue(validator.validate(in));
    }

    static Stream<Arguments> jsonFileValueProviderInvalid() {
        return Stream.of(
                arguments("json/scenario-config/invalid_i_am_no_json.scenario.json"),
                arguments("json/match-config/invalid_floatValue.game.json")
        );
    }

    @ParameterizedTest
    @MethodSource("jsonFileValueProviderInvalid")
    void testInvalidJson(String filePath) throws FileNotFoundException {
        Validator validator = Validator.getTestInstance();
        Reader in = new FileReader(filePath);
        assertFalse(validator.validate(in));
    }
}