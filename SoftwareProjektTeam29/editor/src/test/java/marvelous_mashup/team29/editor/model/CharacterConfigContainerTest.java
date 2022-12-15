package marvelous_mashup.team29.editor.model;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Most of this class is tested indirectly in FileIOLogicTest.
 */

class CharacterConfigContainerTest {
    @Test
    void testListFunctionalities() {
        CharacterConfig hans = new CharacterConfig();
        hans.setName("Hans");
        CharacterConfig peter = new CharacterConfig();
        peter.setName("peter");
        List<CharacterConfig> characterConfigList = new LinkedList<>();
        characterConfigList.add(hans);
        characterConfigList.add(peter);

        CharacterConfigContainer characterContainer = new CharacterConfigContainer(characterConfigList);
        CharacterConfig lieselotte = new CharacterConfig();
        lieselotte.setName("Lieselotte");
        characterContainer.addCharacter(lieselotte);

        characterConfigList.add(lieselotte);
        assertEquals(characterConfigList, characterContainer.getCharacters());
    }
}