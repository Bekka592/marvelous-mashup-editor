package marvelous_mashup.team29.editor.logic;

import marvelous_mashup.team29.editor.gui.config_screens.GenerateMapOptionsEnum;
import marvelous_mashup.team29.editor.model.ScenarioConfig;
import marvelous_mashup.team29.util.data.FieldState;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ScenarioConfigLogicTest {
    private static final ByteArrayOutputStream OUTPUT_STREAM = new ByteArrayOutputStream();
    private static final PrintStream ORIGINAL_OUT = System.out;
    private String[] testScenarioOptions = new String[ScenarioConfig.NR_OF_PARAMETERS];
    private final ScenarioConfigLogic logic = new ScenarioConfigLogic(null, null) {
        @Override
        public String[] getOptions() {
            return testScenarioOptions;
        }

        @Override
        public void setOptions(String[] optionsAsString) {
            testScenarioOptions = optionsAsString;
        }
    };

    @BeforeAll
    public static void setUpTestEnvironment() {
        System.setOut(new PrintStream(OUTPUT_STREAM));
    }

    @AfterAll
    public static void restoreStandardStream() {
        System.setOut(ORIGINAL_OUT);
    }

    @BeforeEach
    public void setUpOptionFields() {
        testScenarioOptions[0] = "cool map name";
        testScenarioOptions[1] = "nice author name";
        testScenarioOptions[2] = "21";
        testScenarioOptions[3] = "3";
    }

    @AfterEach
    public void cleanTestEnvironment() {
        OUTPUT_STREAM.reset();
    }

    @Test
    void changeMapSizeTestTooLongMap() {
        testScenarioOptions[2] = "101";
        testScenarioOptions[3] = "10";
        assertNull(logic.changeMapSize(GenerateMapOptionsEnum.RANDOM));
        assertEquals("This Editor only supports maps up to a size of 100 x 100 fields.", OUTPUT_STREAM.toString());
    }

    @Test
    void changeMapSizeTestTooWideMap() {
        testScenarioOptions[2] = "3";
        testScenarioOptions[3] = "123456";
        assertNull(logic.changeMapSize(GenerateMapOptionsEnum.ONLY_GRASS));
        assertEquals("This Editor only supports maps up to a size of 100 x 100 fields.", OUTPUT_STREAM.toString());
    }

    @Test
    void changeMapSizeTestTooSmallMap() {
        testScenarioOptions[2] = "4";
        testScenarioOptions[3] = "5";
        assertNull(logic.changeMapSize(GenerateMapOptionsEnum.ONLY_ROCKS));
        assertEquals("The map needs to contain at least 22 tiles.\nPlease enter bigger map sizes.",
                OUTPUT_STREAM.toString());
    }

    @Test
    void changeMapSizeTestOnlyGrassMap() {
        FieldState[][] grassMap = logic.changeMapSize(GenerateMapOptionsEnum.ONLY_GRASS);
        for (int i = 0; i < 21; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(FieldState.GRASS, grassMap[i][j]);
            }
        }
        assertEquals("", OUTPUT_STREAM.toString());
    }

    @Test
    void changeMapSizeTestOnlyRocksMap() {
        FieldState[][] rockMap = logic.changeMapSize(GenerateMapOptionsEnum.ONLY_ROCKS);
        for (int i = 0; i < 21; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(FieldState.ROCK, rockMap[i][j]);
            }
        }
        assertEquals("", OUTPUT_STREAM.toString());
    }

    @RepeatedTest(20)
    void changeMapSizeTestRandomMap() {
        testScenarioOptions[2] = "5";
        testScenarioOptions[3] = "5";
        FieldState[][] randomMap = logic.changeMapSize(GenerateMapOptionsEnum.RANDOM);
        assertEquals(5, randomMap.length);
        for (FieldState[] fieldStates : randomMap) {
            assertEquals(5, fieldStates.length);
        }

        ScenarioConfig config = new ScenarioConfig();
        config.setName(testScenarioOptions[0]);
        config.setAuthor(testScenarioOptions[1]);
        config.setScenario(randomMap);
        assertTrue(config.verifyConfig(null));
        assertEquals("", OUTPUT_STREAM.toString());
    }

    @RepeatedTest(5)
    void generateRandomConfig() {
        ScenarioConfig randomConfig = (ScenarioConfig) logic.generateRandomConfig();
        assertTrue(randomConfig.verifyConfig(null));
        assertEquals("", OUTPUT_STREAM.toString());
    }

    @RepeatedTest(5)
    void generateRandomSmallMap() {
        var config = new ScenarioConfig();
        config.setName("name");
        config.setAuthor("author");
        config.setScenario(logic.generateRandomMap(5,5));
        assertTrue(config.verifyConfig(null));
        assertEquals("", OUTPUT_STREAM.toString());
    }

    @Test
    void setConfigToUIAndGetConfigFromUITest() {
        var config = new ScenarioConfig();
        config.setName("this is my name");
        config.setAuthor("that is my author");
        config.setScenario(logic.changeMapSize(GenerateMapOptionsEnum.RANDOM));

        logic.setConfigToUI(config);
        assertEquals("this is my name", testScenarioOptions[0]);
        assertEquals("that is my author", testScenarioOptions[1]);
        assertEquals("21", testScenarioOptions[2]);
        assertEquals("3", testScenarioOptions[3]);

        ScenarioConfig resultConfig = (ScenarioConfig) logic.getConfigFromUI();
        assertEquals(config.getName(), resultConfig.getName());
        assertEquals(config.getAuthor(), resultConfig.getAuthor());
        assertEquals("", OUTPUT_STREAM.toString());
    }
}