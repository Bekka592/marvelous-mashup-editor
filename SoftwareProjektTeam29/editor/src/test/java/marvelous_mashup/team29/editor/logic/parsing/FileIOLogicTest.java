package marvelous_mashup.team29.editor.logic.parsing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import marvelous_mashup.team29.editor.Editor;
import marvelous_mashup.team29.editor.model.*;
import marvelous_mashup.team29.util.FileIOUtilities;
import marvelous_mashup.team29.util.data.FieldState;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.mock;

/**
 * Thank you Jakob Meyer-Hilberg for providing many of the example JSON files, that are used here to test our validation system.
 * Source: https://gitlab.informatik.uni-ulm.de/sopra/ws20-marvelous-mashup/standard
 *
*/

class FileIOLogicTest {
    private static final ByteArrayOutputStream OUTPUT_STREAM = new ByteArrayOutputStream();
    private static final PrintStream ORIGINAL_OUT = System.out;

    private static final String CHARACTER_CONFIG_JSON_PATH = "json/character-config/";
    private static final String MATCH_CONFIG_JSON_PATH = "json/match-config/";
    private static final String SCENARIO_CONFIG_JSON_PATH = "json/scenario-config/";

    public FileIOLogicTest() {
        final HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        Gdx.gl = mock(GL20.class);
        new HeadlessApplication(new Editor(), config);
    }


    @BeforeAll
    public static void setUpStream() {
        System.setOut(new PrintStream(OUTPUT_STREAM));
    }

    @AfterAll
    public static void restoreStandardStream() {
        System.setOut(ORIGINAL_OUT);
    }

    @AfterEach
    public void cleanStream() {
        OUTPUT_STREAM.reset();
    }

    @Test
    void invalidFileNameWrongExtension() {
        FileIOLogic fileIOLogic = new FileIOLogic(ConfigurationTypeEnum.MATCH, null);
        File wrongName = new File(MATCH_CONFIG_JSON_PATH + "invalid_wrongEnding.gamee.json");
        AbstractConfiguration config = fileIOLogic.loadConfigFromFile(wrongName);
        assertNull(config);
        assertEquals("Unsupported file extension.\nPlease choose a different file and try again.", OUTPUT_STREAM.toString());
    }

    @Test
    void invalidFileNameNoExtension() {
        FileIOLogic fileIOLogic = new FileIOLogic(ConfigurationTypeEnum.SCENARIO, null);
        File wrongName = new File(SCENARIO_CONFIG_JSON_PATH + "wrongName.json");
        fileIOLogic.saveConfigToFile(wrongName, new ScenarioConfig());
        assertFalse(wrongName.exists());
        assertEquals("Unsupported file extension.\nPlease choose a different file and try again.", OUTPUT_STREAM.toString());
    }

    @Test
    void invalidFileNameWrongKind() {
        FileIOLogic fileIOLogic = new FileIOLogic(ConfigurationTypeEnum.SCENARIO, null);
        File wrongName = new File(CHARACTER_CONFIG_JSON_PATH + "valid_marvelheroes.character");
        AbstractConfiguration config = fileIOLogic.loadConfigFromFile(wrongName);
        assertNull(config);
        assertEquals("Unsupported file extension.\nPlease choose a different file and try again.", OUTPUT_STREAM.toString());
    }

    @Test
    void loadNonExistingFile() {
        FileIOLogic fileIOLogic = new FileIOLogic(ConfigurationTypeEnum.CHARACTER, null);
        File notExistingFile = new File(CHARACTER_CONFIG_JSON_PATH + "i_do_not_exist.character.json");
        AbstractConfiguration config = fileIOLogic.loadConfigFromFile(notExistingFile);
        assertNull(config);
        assertEquals("An error occurred when trying to load the file.\nPlease try again.", OUTPUT_STREAM.toString());
    }

    @Test
    void saveToNonExistingDirectory() {
        FileIOLogic fileIOLogic = new FileIOLogic(ConfigurationTypeEnum.CHARACTER, null);
        File notExistingFile = new File("i_do_not_exist/file.character.json");
        fileIOLogic.saveConfigToFile(notExistingFile, new CharacterConfigContainer());
        assertFalse(notExistingFile.exists());
        assertEquals("An error occurred when trying to save the config to a file:\nPlease try again.", OUTPUT_STREAM.toString());
    }

    @Test
    void loadMalformedJson() {
        FileIOLogic fileIOLogic = new FileIOLogic(ConfigurationTypeEnum.SCENARIO, null);
        File malformedFile = new File(SCENARIO_CONFIG_JSON_PATH + "invalid_i_am_no_json.scenario.json");
        AbstractConfiguration config = fileIOLogic.loadConfigFromFile(malformedFile);
        assertNull(config);
        assertEquals("Invalid JSON file: Parsing failed.\nPlease choose a valid configuration file.", OUTPUT_STREAM.toString());
    }

    @Test
    void deserializeAndSerializeConfigNoMaxGameTime() {
        FileIOLogic fileIOLogic = new FileIOLogic(ConfigurationTypeEnum.MATCH, null);
        File testFile = new File(MATCH_CONFIG_JSON_PATH + "valid_noMax GameTime.game.json");
        AbstractConfiguration config = fileIOLogic.loadConfigFromFile(testFile);
        assertEquals("", OUTPUT_STREAM.toString());
        OUTPUT_STREAM.reset();

        assertTrue(fileIOLogic.validateConfig(config));
        assertEquals("", OUTPUT_STREAM.toString());
        OUTPUT_STREAM.reset();

        File newFile = new File(MATCH_CONFIG_JSON_PATH + "test.game.json");
        fileIOLogic.saveConfigToFile(newFile, config);
        assertEquals("", OUTPUT_STREAM.toString());
        assertEquals(FileIOUtilities.loadTextFromFile(testFile), FileIOUtilities.loadTextFromFile(newFile));
        assertTrue(newFile.delete());
    }

    @Test
    void serializeConfigNoAuthor() {
        FileIOLogic fileIOLogic = new FileIOLogic(ConfigurationTypeEnum.SCENARIO, null);
        File file = new File(SCENARIO_CONFIG_JSON_PATH + "test.scenario.json");
        ScenarioConfig config = new ScenarioConfig();
        config.setName("boring map");
        config.setScenario(new FieldState[][]{{FieldState.GRASS}});
        fileIOLogic.saveConfigToFile(file, config);

        assertEquals("", OUTPUT_STREAM.toString());
        String content = FileIOUtilities.loadTextFromFile(file);
        String expected = """
                {
                  "scenario": [
                    [
                      "GRASS"
                    ]
                  ],
                  "name": "boring map"
                }""";
        assertEquals(expected, content);
        assertTrue(file.delete());
    }

    @Test
    void tryToValidateEmptyConfigs() {
        FileIOLogic fileIOLogic = new FileIOLogic(ConfigurationTypeEnum.CHARACTER, null);
        assertFalse(fileIOLogic.validateConfig(null));
        assertEquals("", OUTPUT_STREAM.toString());
        OUTPUT_STREAM.reset();

        assertFalse(fileIOLogic.validateConfig(new CharacterConfigContainer()));
        assertEquals("The character configuration file must contain exactly 24 characters in order to be used.",
                OUTPUT_STREAM.toString());
        OUTPUT_STREAM.reset();

        fileIOLogic = new FileIOLogic(ConfigurationTypeEnum.MATCH, null);
        assertFalse(fileIOLogic.validateConfig(new MatchConfig()));
        assertEquals("Invalid match configuration:\n" +
                "Attribute 'max rounds' must be defined in order to load or save the configuration.", OUTPUT_STREAM.toString());
        OUTPUT_STREAM.reset();

        fileIOLogic = new FileIOLogic(ConfigurationTypeEnum.SCENARIO, null);
        assertFalse(fileIOLogic.validateConfig(new ScenarioConfig()));
        assertEquals("Invalid scenario configuration:\n" +
                "Attribute 'name' must be defined in order to load or save the configuration.", OUTPUT_STREAM.toString());
        OUTPUT_STREAM.reset();
    }

    @Test
    void validateValidCharacterLists() {
        FileIOLogic fileIOLogic = new FileIOLogic(ConfigurationTypeEnum.CHARACTER, null);
        File tutoren = new File(CHARACTER_CONFIG_JSON_PATH + "valid_sopratutoren.character.json");
        File heroes = new File(CHARACTER_CONFIG_JSON_PATH + "valid_marvelheroes.character.json");

        AbstractConfiguration tutorenConfig = fileIOLogic.loadConfigFromFile(tutoren);
        AbstractConfiguration heroesConfig = fileIOLogic.loadConfigFromFile(heroes);
        assertEquals("", OUTPUT_STREAM.toString());
        OUTPUT_STREAM.reset();

        assertTrue(fileIOLogic.validateConfig(tutorenConfig));
        assertTrue(fileIOLogic.validateConfig(heroesConfig));
        assertEquals("", OUTPUT_STREAM.toString());
    }

    @Test
    void validateValidMatchConfig() {
        FileIOLogic fileIOLogic = new FileIOLogic(ConfigurationTypeEnum.MATCH, null);
        File basic = new File(MATCH_CONFIG_JSON_PATH + "valid_basic.game.json");
        AbstractConfiguration basicConfig = fileIOLogic.loadConfigFromFile(basic);
        assertEquals("", OUTPUT_STREAM.toString());
        OUTPUT_STREAM.reset();

        assertTrue(fileIOLogic.validateConfig(basicConfig));
        assertEquals("", OUTPUT_STREAM.toString());
    }

    @Test
    void validateValidScenarios() {
        FileIOLogic fileIOLogic = new FileIOLogic(ConfigurationTypeEnum.SCENARIO, null);
        File asgard = new File(SCENARIO_CONFIG_JSON_PATH + "valid_asgard.scenario.json");
        File darkDimension = new File(SCENARIO_CONFIG_JSON_PATH + "valid_darkdimension.scenario.json");
        File avengersTower = new File(SCENARIO_CONFIG_JSON_PATH + "valid_avengerstower.scenario.json");

        AbstractConfiguration asgardConfig = fileIOLogic.loadConfigFromFile(asgard);
        AbstractConfiguration darkDimensionConfig = fileIOLogic.loadConfigFromFile(darkDimension);
        AbstractConfiguration avengersTowerConfig = fileIOLogic.loadConfigFromFile(avengersTower);
        assertEquals("", OUTPUT_STREAM.toString());
        OUTPUT_STREAM.reset();

        assertTrue(fileIOLogic.validateConfig(asgardConfig));
        assertTrue(fileIOLogic.validateConfig(darkDimensionConfig));
        assertTrue(fileIOLogic.validateConfig(avengersTowerConfig));
        assertEquals("", OUTPUT_STREAM.toString());
    }

    @Test
    void validateInvalidCharacterListNotEnough() {
        FileIOLogic fileIOLogic = new FileIOLogic(ConfigurationTypeEnum.CHARACTER, null);
        File tutoren = new File(CHARACTER_CONFIG_JSON_PATH + "invalid_sopratutoren.character.json");

        AbstractConfiguration tutorenConfig = fileIOLogic.loadConfigFromFile(tutoren);
        assertEquals("", OUTPUT_STREAM.toString());
        OUTPUT_STREAM.reset();

        assertFalse(fileIOLogic.validateConfig(tutorenConfig));
        assertEquals("The character configuration file must contain exactly 24 characters in order to be used.", OUTPUT_STREAM.toString());
    }

    @Test
    void validateInvalidCharacterListSameCharacterTwice() {
        FileIOLogic fileIOLogic = new FileIOLogic(ConfigurationTypeEnum.CHARACTER, null);
        File tutoren = new File(CHARACTER_CONFIG_JSON_PATH + "invalid_characterTwice.character.json");

        AbstractConfiguration tutorenConfig = fileIOLogic.loadConfigFromFile(tutoren);
        assertEquals("", OUTPUT_STREAM.toString());
        OUTPUT_STREAM.reset();

        assertFalse(fileIOLogic.validateConfig(tutorenConfig));
        assertEquals("Invalid character configuration:\n" +
                "A configuration can not contain the same hero twice.", OUTPUT_STREAM.toString());
    }

    @Test
    void parseInvalidMatchConfigContainingFloat() {
        FileIOLogic fileIOLogic = new FileIOLogic(ConfigurationTypeEnum.MATCH, null);
        File invalid = new File(MATCH_CONFIG_JSON_PATH + "invalid_floatValue.game.json");
        assertNull(fileIOLogic.loadConfigFromFile(invalid));
        assertEquals("Invalid JSON file: Parsing failed.\nPlease choose a valid configuration file.", OUTPUT_STREAM.toString());
    }

    @Test
    void validateInvalidMatchConfigContainingNegativeInt() {
        FileIOLogic fileIOLogic = new FileIOLogic(ConfigurationTypeEnum.MATCH, null);
        File invalid = new File(MATCH_CONFIG_JSON_PATH + "invalid_negativeValue.game.json");
        AbstractConfiguration invalidConfig = fileIOLogic.loadConfigFromFile(invalid);
        assertEquals("", OUTPUT_STREAM.toString());
        OUTPUT_STREAM.reset();

        assertFalse(fileIOLogic.validateConfig(invalidConfig));
        assertEquals("Invalid match configuration:\n" +
                "Attribute 'power stone cooldown' must be >= 0 in order to load or save the configuration.", OUTPUT_STREAM.toString());
    }

    @Test
    void validateInvalidMatchConfigMissingMindStoneCD() {
        FileIOLogic fileIOLogic = new FileIOLogic(ConfigurationTypeEnum.MATCH, null);
        File invalid = new File(MATCH_CONFIG_JSON_PATH + "invalid_noMindStoneCD.game.json");
        AbstractConfiguration invalidConfig = fileIOLogic.loadConfigFromFile(invalid);
        assertEquals("", OUTPUT_STREAM.toString());
        OUTPUT_STREAM.reset();

        assertFalse(fileIOLogic.validateConfig(invalidConfig));
        assertEquals("Invalid match configuration:\n" +
                "Attribute 'mind stone cooldown' must be defined in order to load or save the configuration.", OUTPUT_STREAM.toString());
    }

    static Stream<Arguments> invalidScenarioProvider() {
        return Stream.of(
                arguments("invalid_noSquare.scenario.json", "Invalid map: The map needs to be in a properly formatted rectangular shape."),
                arguments("invalid_weneed20freefields.scenario.json", "Invalid map: The map needs to contain at least 20 grass fields."),
                arguments("invalid_noPortals.scenario.json", "Invalid map: The map needs to contain at least 2 portal fields.")
        );
    }

    @ParameterizedTest
    @MethodSource("invalidScenarioProvider")
    void validateInvalidScenarioMalformed(String filePath, String errorMessage) {
        FileIOLogic fileIOLogic = new FileIOLogic(ConfigurationTypeEnum.SCENARIO, null);
        File invalid = new File(SCENARIO_CONFIG_JSON_PATH + filePath);
        AbstractConfiguration invalidConfig = fileIOLogic.loadConfigFromFile(invalid);
        assertEquals("", OUTPUT_STREAM.toString());
        OUTPUT_STREAM.reset();

        assertFalse(fileIOLogic.validateConfig(invalidConfig));
        assertEquals(errorMessage, OUTPUT_STREAM.toString());
    }
}