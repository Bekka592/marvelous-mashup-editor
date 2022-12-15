package marvelous_mashup.team29.editor.model;

import marvelous_mashup.team29.util.data.FieldState;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static marvelous_mashup.team29.editor.model.ParameterSpecifications.*;
import static org.junit.jupiter.api.Assertions.*;

class ScenarioConfigTest {
    private static final ByteArrayOutputStream OUTPUT_STREAM = new ByteArrayOutputStream();
    private static final PrintStream ORIGINAL_OUT = System.out;
    private static final ScenarioConfig scenarioConfig = new ScenarioConfig();

    @BeforeAll
    public static void setUpTestEnvironment() {
        System.setOut(new PrintStream(OUTPUT_STREAM));
        resetScenarioConfigValues();
    }

    @AfterAll
    public static void restoreStandardStream() {
        System.setOut(ORIGINAL_OUT);
    }

    private static void resetScenarioConfigValues() {
        scenarioConfig.setAuthor("Emil Example");
        scenarioConfig.setName("really hard map");
        FieldState[][] map = new FieldState[6][5];
        for (var i = 0; i < map.length-1; i++) {
            map[i] = new FieldState[]{FieldState.ROCK, FieldState.GRASS, FieldState.GRASS, FieldState.GRASS, FieldState.GRASS};
        }
        map[5] = new FieldState[]{FieldState.PORTAL, FieldState.PORTAL, FieldState.PORTAL, FieldState.PORTAL, FieldState.PORTAL};
        scenarioConfig.setScenario(map);
    }

    @AfterEach
    public void cleanTestEnvironment() {
        OUTPUT_STREAM.reset();
        resetScenarioConfigValues();
    }

    @Test
    void verifyCorrectScenario() {
        assertTrue(scenarioConfig.verifyConfig(null));
        assertEquals("", OUTPUT_STREAM.toString());
    }

    @Test
    void verifyCorrectScenarioMissingAuthor() {
        scenarioConfig.setAuthor("");
        assertTrue(scenarioConfig.verifyConfig(null));
        assertEquals("", OUTPUT_STREAM.toString());
    }

    @Test
    void verifyCorrectScenarioLongAuthorName() {
        scenarioConfig.setAuthor("h".repeat(25));
        assertTrue(scenarioConfig.verifyConfig(null));
        assertEquals("", OUTPUT_STREAM.toString());
    }

    @Test
    void verifyIncorrectScenarioTooLongAuthorName() {
        scenarioConfig.setAuthor("h".repeat(26));
        assertFalse(scenarioConfig.verifyConfig(null));
        assertEquals("Invalid scenario configuration:\n" +
                "Attribute 'author' can only be up to 25 letters long " +
                "in order to load or save the configuration.", OUTPUT_STREAM.toString());
    }

    @Test
    void verifyIncorrectScenarioMissingName() {
        scenarioConfig.setName("");
        assertFalse(scenarioConfig.verifyConfig(null));
        assertEquals("Invalid scenario configuration:\n" +
                "Attribute 'name' must be defined in order to load or save the configuration.", OUTPUT_STREAM.toString());
    }

    @Test
    void verifyCorrectBigScenario() {
        FieldState[][] map = new FieldState[MAX_MAP_HEIGHT][5];
        for (var i = 0; i < map.length; i++) {
            map[i] = new FieldState[]{FieldState.ROCK, FieldState.GRASS, FieldState.GRASS, FieldState.GRASS, FieldState.PORTAL};
        }
        scenarioConfig.setScenario(map);
        assertTrue(scenarioConfig.verifyConfig(null));
        assertEquals("", OUTPUT_STREAM.toString());
    }

    @Test
    void verifyIncorrectTooLongScenario() {
        FieldState[][] map = new FieldState[MAX_MAP_HEIGHT + 1][5];
        for (var i = 0; i < map.length; i++) {
            map[i] = new FieldState[]{FieldState.ROCK, FieldState.GRASS, FieldState.GRASS, FieldState.GRASS, FieldState.GRASS};
        }
        scenarioConfig.setScenario(map);
        assertFalse(scenarioConfig.verifyConfig(null));
        assertEquals("Invalid map: The map's height needs to be between " + MIN_MAP_HEIGHT + " and " +
                +MAX_MAP_HEIGHT + ".", OUTPUT_STREAM.toString());
    }

    @Test
    void verifyIncorrectTooShortScenario() {
        FieldState[][] map = new FieldState[0][5];
        scenarioConfig.setScenario(map);
        assertFalse(scenarioConfig.verifyConfig(null));
        assertEquals("Invalid map: The map's height needs to be between " + MIN_MAP_HEIGHT + " and " +
                +MAX_MAP_HEIGHT + ".", OUTPUT_STREAM.toString());
    }

    @Test
    void verifyIncorrectTooWideScenario() {
        FieldState[][] map = new FieldState[5][MAX_MAP_WIDTH + 1];
        for (FieldState[] fieldStates : map) {
            Arrays.fill(fieldStates, FieldState.GRASS);
        }
        scenarioConfig.setScenario(map);
        assertFalse(scenarioConfig.verifyConfig(null));
        assertEquals("Invalid map: The map's width needs to be between " + MIN_MAP_WIDTH + " and " +
                +MAX_MAP_WIDTH + ".", OUTPUT_STREAM.toString());
    }

    @Test
    void verifyIncorrectTooSlimScenario() {
        FieldState[][] map = new FieldState[5][0];
        scenarioConfig.setScenario(map);
        assertFalse(scenarioConfig.verifyConfig(null));
        assertEquals("Invalid map: The map's width needs to be between " + MIN_MAP_WIDTH + " and " +
                +MAX_MAP_WIDTH + ".", OUTPUT_STREAM.toString());
    }

    @Test
    void verifyIncorrectNoScenario() {
        scenarioConfig.setScenario(null);
        assertFalse(scenarioConfig.verifyConfig(null));
        assertEquals("Invalid scenario configuration:\nAttribute 'scenario' must be defined in order to load or " +
                "save the configuration.", OUTPUT_STREAM.toString());
    }

    @Test
    void verifyIncorrectMalformedScenario() {
        FieldState[][] map = new FieldState[][]{
                {FieldState.ROCK, FieldState.GRASS, FieldState.ROCK},
                {FieldState.GRASS, FieldState.GRASS},
                {FieldState.ROCK, FieldState.GRASS, FieldState.ROCK}
        };
        scenarioConfig.setScenario(map);
        assertFalse(scenarioConfig.verifyConfig(null));
        assertEquals("Invalid map: The map needs to be in a properly formatted rectangular shape.",
                OUTPUT_STREAM.toString());
    }

    @Test
    void verifyIncorrectWrongObjectsOnMap() {
        FieldState[][] map = new FieldState[][]{
                {FieldState.ROCK, FieldState.GRASS, FieldState.ROCK},
                {FieldState.GRASS, FieldState.GRASS, FieldState.INFINITY_STONE},
                {FieldState.ROCK, FieldState.GRASS, FieldState.ROCK}
        };
        scenarioConfig.setScenario(map);
        assertFalse(scenarioConfig.verifyConfig(null));
        assertEquals("Invalid map: All of the map fields need to be declared either as grass, portals or as rocks.",
                OUTPUT_STREAM.toString());
    }

    @Test
    void verifyIncorrectTooLessGrassFields() {
        scenarioConfig.getScenario()[4][4] = FieldState.ROCK;
        assertFalse(scenarioConfig.verifyConfig(null));
        assertEquals("Invalid map: The map needs to contain at least " + MIN_NR_OF_GRASS_FIELDS + " grass fields.",
                OUTPUT_STREAM.toString());
    }

    @Test
    void verifyIncorrectTooLessPortalFields() {
        scenarioConfig.getScenario()[5] = new FieldState[]{FieldState.ROCK, FieldState.GRASS, FieldState.GRASS, FieldState.GRASS, FieldState.GRASS};
        assertFalse(scenarioConfig.verifyConfig(null));
        assertEquals("Invalid map: The map needs to contain at least " + MIN_NR_OF_PORTAL_FIELDS + " portal fields.",
                OUTPUT_STREAM.toString());
    }

    @Test
    void testDefaultValues() {
        ScenarioConfig emptyConfig = new ScenarioConfig();
        assertNull(emptyConfig.getScenario());
        assertEquals("", emptyConfig.getName());
        assertEquals("", emptyConfig.getAuthor());
    }
}