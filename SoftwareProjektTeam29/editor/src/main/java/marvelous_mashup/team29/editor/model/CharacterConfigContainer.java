package marvelous_mashup.team29.editor.model;

import com.badlogic.gdx.scenes.scene2d.Stage;
import marvelous_mashup.team29.ui_util.pop_ups.PopUpAccept;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static marvelous_mashup.team29.util.StringUtilities.listContainsDuplicates;

/**
 * Implements a JSON standardized 'character configuration' as a java class.
 * Basically just a list of {@link CharacterConfig} objects.
 */
public class CharacterConfigContainer extends AbstractConfiguration implements Serializable {
    public static final transient int NEEDED_NR_OF_CHARACTERS = 24;
    private final List<CharacterConfig> characters;

    public CharacterConfigContainer() {
        characters = new LinkedList<>();
    }

    public CharacterConfigContainer(List<CharacterConfig> characters) {
        this.characters = characters;
    }

    public void addCharacter(CharacterConfig character) {
        characters.add(character);
    }

    public List<CharacterConfig> getCharacters() {
        return characters;
    }

    /**
     * Checks whether the character list contains exactly 24 characters, whether all of those characters are
     * valid according to {@link CharacterConfig#verifyConfig(Stage)} and whether the character
     * names and the character IDs really are unique strings.
     *
     * @param stage needed for displaying error pop-ups onto
     * @return is this a valid list of characters according to the network standard document? (true/false)
     */
    @Override
    public boolean verifyConfig(Stage stage) {
        if (characters.size() != NEEDED_NR_OF_CHARACTERS) {
            new PopUpAccept("The character configuration file must contain exactly 24 characters in order to be used.",
                    stage);
            return false;
        }

        for (CharacterConfig config : characters) {
            if (!config.verifyConfig(stage)) return false;
        }

        List<String> characterNames = characters.stream().map(CharacterConfig::getName).collect(Collectors.toList());
        List<String> characterIDs = characters.stream().map(CharacterConfig::getCharacterID).map(Object::toString).collect(Collectors.toList());
        if (listContainsDuplicates(characterNames) || listContainsDuplicates(characterIDs)) {
            new PopUpAccept("""
                    Invalid character configuration:
                    A configuration can not contain the same hero twice.""", stage);
            return false;
        }
        return true;
    }
}
