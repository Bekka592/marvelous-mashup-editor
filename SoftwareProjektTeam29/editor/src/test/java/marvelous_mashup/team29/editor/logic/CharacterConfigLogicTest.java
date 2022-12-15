package marvelous_mashup.team29.editor.logic;

import marvelous_mashup.team29.editor.model.CharacterConfig;
import marvelous_mashup.team29.editor.model.CharacterConfigContainer;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CharacterConfigLogicTest {
    private static final ByteArrayOutputStream OUTPUT_STREAM = new ByteArrayOutputStream();
    private static final PrintStream ORIGINAL_OUT = System.out;
    private String[] testCharacterOptions;
    private CharacterConfigLogic logic;

    @BeforeAll
    public static void setUpTestEnvironment() {
        System.setOut(new PrintStream(OUTPUT_STREAM));
    }

    @AfterAll
    public static void restoreStandardStream() {
        System.setOut(ORIGINAL_OUT);
    }

    @BeforeEach
    public void setUpLogicObject() {
        logic = new CharacterConfigLogic(null, null) {
            @Override
            public String[] getOptions() {
                return testCharacterOptions;
            }

            @Override
            public void setOptions(String[] optionsAsString) {
                testCharacterOptions = optionsAsString;
            }
        };
        testCharacterOptions = new String[CharacterConfig.NR_OF_PARAMETERS];
    }

    @AfterEach
    public void cleanTestEnvironment() {
        OUTPUT_STREAM.reset();
    }

    @Test
    void addEmptyCharacterTest() {
        assertTrue(logic.getCharacters().isEmpty());
        logic.addCharacter();
        assertEquals(1, logic.getCharacters().size());
        CharacterConfig newCharacter = logic.getCharacters().get(0);

        assertEquals("New Character", newCharacter.getName());
        assertEquals(Integer.MIN_VALUE, newCharacter.getCharacterID());
        assertEquals(Integer.MIN_VALUE, newCharacter.getHp());
        assertEquals(Integer.MIN_VALUE, newCharacter.getMp());
        assertEquals(Integer.MIN_VALUE, newCharacter.getAp());
        assertEquals(Integer.MIN_VALUE, newCharacter.getMeleeDamage());
        assertEquals(Integer.MIN_VALUE, newCharacter.getRangeCombatReach());
        assertEquals(Integer.MIN_VALUE, newCharacter.getRangeCombatDamage());
        assertEquals("", OUTPUT_STREAM.toString());
    }

    @Test
    void addCharacterTest() {
        assertTrue(logic.getCharacters().isEmpty());
        CharacterConfig character = new CharacterConfig();
        character.setName("Loki");
        logic.addCharacter(character);
        assertEquals(1, logic.getCharacters().size());
        assertEquals(character, logic.getCharacters().get(0));
        assertEquals("Loki.png", logic.getCharacterImageName());
    }

    @Test
    void changeSelectedCharacterTest() {
        CharacterConfig characterOne = new CharacterConfig();
        characterOne.setName("me");
        CharacterConfig characterTwo = new CharacterConfig();
        characterTwo.setName("you");
        characterTwo.setRangeCombatReach(5000);
        assertEquals(Integer.MIN_VALUE, characterTwo.getHp());

        logic.addCharacter(characterOne);
        logic.addCharacter(characterTwo);
        testCharacterOptions[1] = "15";

        CharacterConfig characterThree = new CharacterConfig();
        characterThree.setAp(-1);
        characterThree.setMp(500000);
        characterThree.setName("Steve");
        characterThree.setRangeCombatDamage(1);
        logic.addCharacter(characterThree);

        assertEquals(Integer.MIN_VALUE, characterTwo.getCharacterID());
        assertEquals("you", characterTwo.getName());
        assertEquals(15, characterTwo.getHp()); // hs should have been updated by updateSelectedCharacterValuesFromUI
        assertEquals(Integer.MIN_VALUE, characterTwo.getMp());
        assertEquals(Integer.MIN_VALUE, characterTwo.getAp());
        assertEquals(Integer.MIN_VALUE, characterTwo.getRangeCombatDamage());
        assertEquals(5000, characterTwo.getRangeCombatReach());
        assertEquals(Integer.MIN_VALUE, characterTwo.getMeleeDamage());

        assertEquals("Steve", testCharacterOptions[0]);
        assertEquals("", testCharacterOptions[1]);
        assertEquals("9999", testCharacterOptions[2]);
        assertEquals("", testCharacterOptions[3]);
        assertEquals("", testCharacterOptions[4]);
        assertEquals("1", testCharacterOptions[5]);
        assertEquals("", testCharacterOptions[6]);
    }

    @Test
    void setNoSelectedCharacterTest() {
        CharacterConfig lastCharacter = new CharacterConfig();
        lastCharacter.setName("my name");
        logic.addCharacter(lastCharacter);
        assertEquals("my name", testCharacterOptions[0]);

        logic.setNoSelectedCharacter();
        assertEquals("", testCharacterOptions[0]);
        assertEquals("", testCharacterOptions[1]);
        assertEquals("", testCharacterOptions[2]);
        assertEquals("", testCharacterOptions[3]);
        assertEquals("", testCharacterOptions[4]);
        assertEquals("", testCharacterOptions[5]);
        assertEquals("", testCharacterOptions[6]);
    }

    @Test
    void selectedCharacterNameChangedTest() {
        CharacterConfig character = new CharacterConfig();
        character.setName("Jesica Jones");
        logic.addCharacter(character);

        assertEquals("Jesica Jones", logic.getCharacters().get(0).getName());
        assertEquals(Integer.MIN_VALUE, logic.getCharacters().get(0).getCharacterID());
        assertEquals("unknown.png", logic.getCharacterImageName());

        logic.selectedCharacterNameChanged("Jesicca Jones");
        assertEquals("Jesicca Jones", logic.getCharacters().get(0).getName());
        assertEquals(Integer.MIN_VALUE, logic.getCharacters().get(0).getCharacterID());
        assertEquals("unknown.png", logic.getCharacterImageName());

        logic.selectedCharacterNameChanged("Jessica Jones");
        assertEquals("Jessica Jones", logic.getCharacters().get(0).getName());
        assertEquals(23, logic.getCharacters().get(0).getCharacterID());
        assertEquals("Jessica Jones.png", logic.getCharacterImageName());

        logic.selectedCharacterNameChanged("Jesica Jones ");
        assertEquals("Jesica Jones ", logic.getCharacters().get(0).getName());
        assertEquals(Integer.MIN_VALUE, logic.getCharacters().get(0).getCharacterID());
        assertEquals("unknown.png", logic.getCharacterImageName());

    }

    @Test
    void removeCharacterTest() {
        CharacterConfig characterOne = new CharacterConfig();
        characterOne.setName("me");
        logic.addCharacter(characterOne);

        CharacterConfig characterTwo = new CharacterConfig();
        characterTwo.setName("you");
        logic.addCharacter(characterTwo);

        CharacterConfig characterThree = new CharacterConfig();
        characterThree.setName("Steve");
        logic.addCharacter(characterThree);

        logic.removeCharacter(characterTwo);
        assertEquals(2, logic.getCharacters().size());
        // selected should be 2 now
        logic.selectedCharacterNameChanged("Stevee");
        assertEquals("Stevee", characterThree.getName());

        logic.removeCharacter(characterThree);
        assertEquals(1, logic.getCharacters().size());
        // selected should be -1 now
        logic.selectedCharacterNameChanged("meee");
        assertEquals("me", characterOne.getName());
    }

    @RepeatedTest(5)
    void generateRandomConfigTest() {
        CharacterConfigContainer randomConfig = (CharacterConfigContainer) logic.generateRandomConfig();
        assertTrue(randomConfig.verifyConfig(null));
        assertEquals("", OUTPUT_STREAM.toString());
    }

    @Test
    void getConfigFromUITest() {
        CharacterConfig characterOne = new CharacterConfig();
        characterOne.setName("me");
        logic.addCharacter(characterOne);

        CharacterConfig characterTwo = new CharacterConfig();
        characterTwo.setName("you");
        logic.addCharacter(characterTwo);

        testCharacterOptions[1] = "5";

        CharacterConfigContainer config = (CharacterConfigContainer) logic.getConfigFromUI();
        List<CharacterConfig> characters = config.getCharacters();
        assertEquals(characterOne, characters.get(0));
        assertEquals(5, characters.get(1).getHp());
    }

    @Test
    void setConfigToUITest() {
        CharacterConfig oldCharacter = new CharacterConfig();
        oldCharacter.setName("santa claus");
        CharacterConfigContainer oldConfig = new CharacterConfigContainer();
        oldConfig.addCharacter(oldCharacter);
        logic.setConfigToUI(oldConfig);

        CharacterConfig characterOne = new CharacterConfig();
        characterOne.setName("me");
        CharacterConfig characterTwo = new CharacterConfig();
        characterTwo.setName("you");
        CharacterConfigContainer config = new CharacterConfigContainer();
        config.addCharacter(characterOne);
        config.addCharacter(characterTwo);
        logic.setConfigToUI(config);

        assertEquals(2, logic.getCharacters().size());
        assertEquals(characterOne, logic.getCharacters().get(0));
        assertEquals(characterTwo, logic.getCharacters().get(1));
    }
}