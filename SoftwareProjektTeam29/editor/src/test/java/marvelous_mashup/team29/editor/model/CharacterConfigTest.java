package marvelous_mashup.team29.editor.model;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static marvelous_mashup.team29.editor.model.ParameterSpecifications.MAX_VALUE_MELEE_DAMAGE;
import static org.junit.jupiter.api.Assertions.*;

class CharacterConfigTest {
    private static final ByteArrayOutputStream OUTPUT_STREAM = new ByteArrayOutputStream();
    private static final PrintStream ORIGINAL_OUT = System.out;
    private static final CharacterConfig characterConfig = new CharacterConfig();

    @BeforeAll
    public static void setUpTestEnvironment() {
        System.setOut(new PrintStream(OUTPUT_STREAM));
        resetCharacterConfigValues();
    }

    @AfterAll
    public static void restoreStandardStream() {
        System.setOut(ORIGINAL_OUT);
    }

    private static void resetCharacterConfigValues() {
        characterConfig.setCharacterID(5);
        characterConfig.setName("Strong Character");
        characterConfig.setHp(20);
        characterConfig.setMp(3);
        characterConfig.setAp(5);
        characterConfig.setMeleeDamage(10);
        characterConfig.setRangeCombatDamage(2);
        characterConfig.setRangeCombatReach(3);
    }

    @AfterEach
    public void cleanTestEnvironment() {
        OUTPUT_STREAM.reset();
        resetCharacterConfigValues();
    }

    @Test
    void verifyCorrectScenario() {
        assertTrue(characterConfig.verifyConfig(null));
        assertEquals("", OUTPUT_STREAM.toString());
    }

    @Test
    void verifyIncorrectCharacterWrongCharacterID() {
        characterConfig.setCharacterID(0);
        assertFalse(characterConfig.verifyConfig(null));
        assertEquals("Invalid character configuration:\nOnly the 24 characters defined in the network " +
                "standard document are supported by this Editor and can be loaded from or saved to a file.", OUTPUT_STREAM.toString());

        OUTPUT_STREAM.reset();
        characterConfig.setCharacterID(25);
        assertFalse(characterConfig.verifyConfig(null));
        assertEquals("Invalid character configuration:\nOnly the 24 characters defined in the network " +
                "standard document are supported by this Editor and can be loaded from or saved to a file.", OUTPUT_STREAM.toString());

        OUTPUT_STREAM.reset();
        characterConfig.setCharacterID(Integer.MIN_VALUE);
        assertFalse(characterConfig.verifyConfig(null));
        assertEquals("Invalid character configuration:\nOnly the 24 characters defined in the network " +
                "standard document are supported by this Editor and can be loaded from or saved to a file.", OUTPUT_STREAM.toString());

    }

    @Test
    void verifyIncorrectCharacterNoAp() {
        characterConfig.setAp(Integer.MIN_VALUE);
        assertFalse(characterConfig.verifyConfig(null));
        assertEquals("Invalid character configuration:\n" +
                "Attribute 'AP' must be defined in order to load or save the configuration.", OUTPUT_STREAM.toString());
    }

    @Test
    void verifyIncorrectCharacterToHighMeleeDamage() {
        characterConfig.setMeleeDamage(MAX_VALUE_MELEE_DAMAGE + 1);
        assertFalse(characterConfig.verifyConfig(null));
        assertEquals("Invalid character configuration:\n" +
                        "Attribute 'melee damage' must be <= " + MAX_VALUE_MELEE_DAMAGE + " in order to load or save the configuration.",
                OUTPUT_STREAM.toString());
    }

    @Test
    void verifyIncorrectTooLongCharacterName() {
        characterConfig.setName(" ".repeat(26));
        assertFalse(characterConfig.verifyConfig(null));
        assertEquals("Invalid character configuration:\n" +
                "Attribute 'name' can only be up to 25 letters long " +
                "in order to load or save the configuration.", OUTPUT_STREAM.toString());
    }

    @Test
    void testDefaultValues() {
        CharacterConfig emptyConfig = new CharacterConfig();
        assertEquals(Integer.MIN_VALUE, emptyConfig.getCharacterID());
        assertEquals("", emptyConfig.getName());
        assertEquals(Integer.MIN_VALUE, emptyConfig.getHp());
        assertEquals(Integer.MIN_VALUE, emptyConfig.getMp());
        assertEquals(Integer.MIN_VALUE, emptyConfig.getAp());
        assertEquals(Integer.MIN_VALUE, emptyConfig.getMeleeDamage());
        assertEquals(Integer.MIN_VALUE, emptyConfig.getRangeCombatDamage());
        assertEquals(Integer.MIN_VALUE, emptyConfig.getRangeCombatReach());
    }
}