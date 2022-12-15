package marvelous_mashup.team29.editor.logic;

import marvelous_mashup.team29.editor.model.MatchConfig;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MatchConfigLogicTest {
    private static final ByteArrayOutputStream OUTPUT_STREAM = new ByteArrayOutputStream();
    private static final PrintStream ORIGINAL_OUT = System.out;
    private String[] testMatchOptions;
    private final MatchConfigLogic matchConfigLogic = new MatchConfigLogic(null, null) {
        @Override
        public String[] getOptions() {
            return testMatchOptions;
        }

        @Override
        public void setOptions(String[] optionsAsString) {
            testMatchOptions = optionsAsString;
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

    @AfterEach
    public void cleanTestEnvironment() {
        OUTPUT_STREAM.reset();
    }

    @Test
    void setConfigToUITest() {
        var matchConfig = new MatchConfig();
        matchConfig.setMaxRounds(15);
        matchConfig.setMaxGameTime(100000);
        matchConfig.setMaxRoundTime(100);
        matchConfig.setMaxResponseTime(Integer.MIN_VALUE);
        matchConfig.setMaxAnimationTime(100);
        matchConfig.setMaxPauseTime(2000);
        matchConfig.setSpaceStoneCD(0);
        matchConfig.setRealityStoneCD(1);
        matchConfig.setPowerStoneCD(11);
        matchConfig.setTimeStoneCD(1000);
        matchConfig.setSoulStoneCD(-1);
        matchConfig.setMindStoneCD(Integer.MAX_VALUE);
        matchConfig.setMindStoneDMG(5);

        matchConfigLogic.setConfigToUI(matchConfig);
        assertEquals("15", testMatchOptions[0]);
        assertEquals("99999", testMatchOptions[1]);
        assertEquals("100", testMatchOptions[2]);
        assertEquals("", testMatchOptions[3]);
        assertEquals("100", testMatchOptions[4]);
        assertEquals("2000", testMatchOptions[5]);
        assertEquals("0", testMatchOptions[6]);
        assertEquals("1", testMatchOptions[7]);
        assertEquals("11", testMatchOptions[8]);
        assertEquals("1000", testMatchOptions[9]);
        assertEquals("", testMatchOptions[10]);
        assertEquals("99999", testMatchOptions[11]);
        assertEquals("5", testMatchOptions[12]);
    }

    @Test
    void getConfigFromUITest() {
        var matchConfig = new MatchConfig();
        matchConfig.setMaxRounds(15);
        matchConfig.setMaxGameTime(100000);
        matchConfig.setMaxRoundTime(100);
        matchConfig.setMaxResponseTime(Integer.MIN_VALUE);
        matchConfig.setMaxAnimationTime(100);
        matchConfig.setMaxPauseTime(2000);
        matchConfig.setSpaceStoneCD(0);
        matchConfig.setRealityStoneCD(1);
        matchConfig.setPowerStoneCD(11);
        matchConfig.setTimeStoneCD(1000);
        matchConfig.setSoulStoneCD(-1);
        matchConfig.setMindStoneCD(Integer.MAX_VALUE);
        matchConfig.setMindStoneDMG(5);

        matchConfigLogic.setConfigToUI(matchConfig);
        MatchConfig resultConfig = (MatchConfig) matchConfigLogic.getConfigFromUI();

        matchConfig.setMaxGameTime(99999);
        matchConfig.setMindStoneCD(99999);
        matchConfig.setSoulStoneCD(Integer.MIN_VALUE);
        assertEquals(matchConfig.getMaxRounds(), resultConfig.getMaxRounds());
        assertEquals(matchConfig.getMaxRoundTime(), resultConfig.getMaxRoundTime());
        assertEquals(matchConfig.getMaxGameTime(), resultConfig.getMaxGameTime());
        assertEquals(matchConfig.getMaxResponseTime(), resultConfig.getMaxResponseTime());
        assertEquals(matchConfig.getMaxAnimationTime(), resultConfig.getMaxAnimationTime());
        assertEquals(matchConfig.getMaxPauseTime(), resultConfig.getMaxPauseTime());
        assertEquals(matchConfig.getSoulStoneCD(), resultConfig.getSoulStoneCD());
        assertEquals(matchConfig.getSpaceStoneCD(), resultConfig.getSpaceStoneCD());
        assertEquals(matchConfig.getRealityStoneCD(), resultConfig.getRealityStoneCD());
        assertEquals(matchConfig.getMindStoneCD(), resultConfig.getMindStoneCD());
        assertEquals(matchConfig.getPowerStoneCD(), resultConfig.getPowerStoneCD());
        assertEquals(matchConfig.getTimeStoneCD(), resultConfig.getTimeStoneCD());
        assertEquals(matchConfig.getMindStoneDMG(), resultConfig.getMindStoneDMG());
        assertEquals("", OUTPUT_STREAM.toString());
    }

    @RepeatedTest(5)
    void generateRandomConfigTest() {
        MatchConfig randomMatchConfig = (MatchConfig) matchConfigLogic.generateRandomConfig();
        assertTrue(randomMatchConfig.verifyConfig(null));
        assertEquals("", OUTPUT_STREAM.toString());
    }
}