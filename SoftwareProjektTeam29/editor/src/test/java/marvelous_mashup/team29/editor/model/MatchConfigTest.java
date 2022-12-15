package marvelous_mashup.team29.editor.model;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static marvelous_mashup.team29.editor.model.ParameterSpecifications.MAX_VALUE_SPACE_STONE_CD;
import static marvelous_mashup.team29.editor.model.ParameterSpecifications.MIN_VALUE_MAX_GAME_TIME;
import static org.junit.jupiter.api.Assertions.*;

class MatchConfigTest {
    private static final ByteArrayOutputStream OUTPUT_STREAM = new ByteArrayOutputStream();
    private static final PrintStream ORIGINAL_OUT = System.out;
    private static final MatchConfig matchConfig = new MatchConfig();

    @BeforeAll
    public static void setUpTestEnvironment() {
        System.setOut(new PrintStream(OUTPUT_STREAM));
        resetMatchConfigValues();
    }

    @AfterAll
    public static void restoreStandardStream() {
        System.setOut(ORIGINAL_OUT);
    }

    private static void resetMatchConfigValues() {
        matchConfig.setMaxRounds(3);
        matchConfig.setMaxRoundTime(500);
        matchConfig.setMaxGameTime(9999);
        matchConfig.setMaxAnimationTime(23);
        matchConfig.setSpaceStoneCD(3);
        matchConfig.setMindStoneCD(13);
        matchConfig.setRealityStoneCD(2);
        matchConfig.setPowerStoneCD(0);
        matchConfig.setTimeStoneCD(1);
        matchConfig.setSoulStoneCD(21);
        matchConfig.setMindStoneDMG(3);
        matchConfig.setMaxPauseTime(200);
        matchConfig.setMaxResponseTime(355);
    }

    @AfterEach
    public void cleanTestEnvironment() {
        OUTPUT_STREAM.reset();
        resetMatchConfigValues();
    }

    @Test
    void verifyCorrectMatchConfig() {
        assertTrue(matchConfig.verifyConfig(null));
        assertEquals("", OUTPUT_STREAM.toString());
    }

    @Test
    void verifyCorrectMatchConfigNoMaxGameTime() {
        matchConfig.setMaxGameTime(Integer.MIN_VALUE);
        assertTrue(matchConfig.verifyConfig(null));
        assertEquals("", OUTPUT_STREAM.toString());
    }

    @Test
    void verifyIncorrectMatchConfigWrongMaxGameTime() {
        matchConfig.setMaxGameTime(-1);
        assertFalse(matchConfig.verifyConfig(null));
        assertEquals("Invalid match configuration:\n" +
                "Attribute 'max game time' must be >= " + MIN_VALUE_MAX_GAME_TIME + " in order to load or save the configuration.", OUTPUT_STREAM.toString());
    }

    @Test
    void verifyIncorrectMatchConfigWrongSpaceStoneCD() {
        matchConfig.setSpaceStoneCD(5000);
        assertFalse(matchConfig.verifyConfig(null));
        assertEquals("Invalid match configuration:\n" +
                "Attribute 'space stone cooldown' must be <= " + MAX_VALUE_SPACE_STONE_CD + " in order to load or save the configuration.", OUTPUT_STREAM.toString());
    }

    @Test
    void verifyIncorrectMatchConfigNoMaxRounds() {
        matchConfig.setMaxRounds(Integer.MIN_VALUE);
        assertFalse(matchConfig.verifyConfig(null));
        assertEquals("Invalid match configuration:\n" +
                "Attribute 'max rounds' must be defined in order to load or save the configuration.", OUTPUT_STREAM.toString());
    }

    @Test
    void testDefaultValues() {
        MatchConfig emptyConfig = new MatchConfig();
        assertEquals(Integer.MIN_VALUE, emptyConfig.getMaxRounds());
        assertEquals(Integer.MIN_VALUE, emptyConfig.getMaxRoundTime());
        assertEquals(Integer.MIN_VALUE, emptyConfig.getMaxGameTime());
        assertEquals(Integer.MIN_VALUE, emptyConfig.getMaxAnimationTime());
        assertEquals(Integer.MIN_VALUE, emptyConfig.getSpaceStoneCD());
        assertEquals(Integer.MIN_VALUE, emptyConfig.getMindStoneCD());
        assertEquals(Integer.MIN_VALUE, emptyConfig.getRealityStoneCD());
        assertEquals(Integer.MIN_VALUE, emptyConfig.getPowerStoneCD());
        assertEquals(Integer.MIN_VALUE, emptyConfig.getTimeStoneCD());
        assertEquals(Integer.MIN_VALUE, emptyConfig.getSoulStoneCD());
        assertEquals(Integer.MIN_VALUE, emptyConfig.getMindStoneDMG());
        assertEquals(Integer.MIN_VALUE, emptyConfig.getMaxPauseTime());
        assertEquals(Integer.MIN_VALUE, emptyConfig.getMaxResponseTime());
    }
}