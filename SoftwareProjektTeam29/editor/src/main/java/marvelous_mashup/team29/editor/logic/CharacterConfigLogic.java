package marvelous_mashup.team29.editor.logic;

import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import marvelous_mashup.team29.editor.gui.config_screens.AbstractConfigurationScreen;
import marvelous_mashup.team29.editor.gui.config_screens.CharacterConfigScreen;
import marvelous_mashup.team29.editor.model.AbstractConfiguration;
import marvelous_mashup.team29.editor.model.CharacterConfig;
import marvelous_mashup.team29.editor.model.CharacterConfigContainer;
import marvelous_mashup.team29.editor.model.SupportedCharactersEnum;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static marvelous_mashup.team29.editor.model.CharacterConfig.IMAGE_OF_UNKNOWN_CHARACTER;
import static marvelous_mashup.team29.editor.model.ParameterSpecifications.*;
import static marvelous_mashup.team29.ui_util.UIConstants.TEXT_LENGTH_IN_TEXT_FIELD_SMALL;
import static marvelous_mashup.team29.util.MathUtilities.randomIntInRange;

/**
 * Implements the logic of the {@link CharacterConfigScreen} to give the user the option to interact with the screen.
 */
public class CharacterConfigLogic extends AbstractConfigurationLogic {
    private final List<CharacterConfig> characters = new LinkedList<>();
    private final CharacterConfigScreen characterScreen;
    private String characterImageName;
    private int selected = -1;

    public CharacterConfigLogic(CharacterConfigScreen characterScreen, TextField[] characterProperties) {
        super((characterScreen == null) ? null : characterScreen.getStage(), characterProperties);
        this.characterScreen = characterScreen;
        characterImageName = IMAGE_OF_UNKNOWN_CHARACTER;
    }

    /**
     * This Method is called whenever the "add character" button is clicked.
     * Configures a new blank characters and adds it using {@link CharacterConfigLogic#addCharacter(CharacterConfig)}.
     */
    public void addCharacter() {
        var character = new CharacterConfig();
        character.setName("New Character");
        addCharacter(character);
    }

    /**
     * Adding the character to the character selection table and to the character list.
     * The new character's information is directly displayed on the properties pane.
     *
     * @param character that needs to be added
     */
    public void addCharacter(CharacterConfig character) {
        if (characterScreen != null) characterScreen.addCharacterSelectionEntry(character);
        characters.add(character);
        changeSelectedCharacter(character);
    }

    /**
     * This method is called whenever a new character is clicked in the character selection pane.
     * Now the old character's values are saved first before the new character's values are displayed on the properties pane.
     *
     * @param character who's values now need to be displayed
     */
    public void changeSelectedCharacter(CharacterConfig character) {
        updateSelectedCharacterValuesFromUI();
        setPropertiesCleared(false);
        selected = characters.indexOf(character);
        showCharacterOnPropertiesPane();
    }

    /**
     * This method is called whenever the character properties pane needs to be cleared.
     * Now the old character's values are saved first before setting anonymous values.
     */
    public void setNoSelectedCharacter() {
        updateSelectedCharacterValuesFromUI();
        setPropertiesCleared(true);
        characterImageName = IMAGE_OF_UNKNOWN_CHARACTER;
        selected = -1;
    }

    /**
     * Called whenever the properties pane needs to be cleared and disabled, because there is no selected character
     * anymore, or needs to be displayed again, because a character got selected.
     * Disables or enables all the property pane's text boxes and clears the texts if the pane needs to be disabled.
     *
     * @param propertiesPaneDisabled does the properties pane need to be cleared? (true/false)
     */
    private void setPropertiesCleared(boolean propertiesPaneDisabled) {
        var noText = new String[CharacterConfig.NR_OF_PARAMETERS];
        for (var i = 0; i < getOptions().length; i++) {
            noText[i] = "";
            if (characterScreen != null) options[i].setDisabled(propertiesPaneDisabled);
        }
        if (propertiesPaneDisabled) setOptions(noText);
    }

    /**
     * This method is called every time a name in the properties pane is changed.
     * It directly reacts to the name change, by changing the corresponding label in the character selection pane.
     * It also updates the character ID and the characters image, in case the character's name now equals and of the
     * {@link SupportedCharactersEnum} name's.
     *
     * @param newName currently entered text in the "name" text box
     */
    public void selectedCharacterNameChanged(String newName) {
        if (selected < 0) return;
        characters.get(selected).setCharacterID(Integer.MIN_VALUE);
        characterImageName = IMAGE_OF_UNKNOWN_CHARACTER;
        for (SupportedCharactersEnum hero : SupportedCharactersEnum.values()) {
            if (newName.equals(hero.getName())) {
                characters.get(selected).setCharacterID(hero.getId());
                characterImageName = hero.getName() + ".png";
            }
        }
        characters.get(selected).setName(newName);
        if (characterScreen != null) characterScreen.setCharacterLabelText(selected, newName);
    }

    /**
     * Reading out all the values that are currently entered in the properties panel's text boxes
     * and setting them as the selected character's new values.
     */
    private void updateSelectedCharacterValuesFromUI() {
        if (selected < 0) return;
        String[] inputs = getOptions();
        var index = 0;
        characters.get(selected).setName(inputs[index++]);
        characters.get(selected).setHp(parseInputToNumber(inputs[index++]));
        characters.get(selected).setMp(parseInputToNumber(inputs[index++]));
        characters.get(selected).setAp(parseInputToNumber(inputs[index++]));
        characters.get(selected).setMeleeDamage(parseInputToNumber(inputs[index++]));
        characters.get(selected).setRangeCombatDamage(parseInputToNumber(inputs[index++]));
        characters.get(selected).setRangeCombatReach(parseInputToNumber(inputs[index]));
    }

    /**
     * Setting the values of the properties pane's text boxes according to the attributes of the new selected character.
     * Also setting his picture in case his name matches one of the 24 supported character names.
     */
    private void showCharacterOnPropertiesPane() {
        CharacterConfig character = characters.get(selected);
        var characterProperties = new String[CharacterConfig.NR_OF_PARAMETERS];
        var index = 0;
        characterProperties[index++] = character.getName();
        characterProperties[index++] = parseNumberToOutput(character.getHp(), TEXT_LENGTH_IN_TEXT_FIELD_SMALL);
        characterProperties[index++] = parseNumberToOutput(character.getMp(), TEXT_LENGTH_IN_TEXT_FIELD_SMALL);
        characterProperties[index++] = parseNumberToOutput(character.getAp(), TEXT_LENGTH_IN_TEXT_FIELD_SMALL);
        characterProperties[index++] = parseNumberToOutput(character.getMeleeDamage(), TEXT_LENGTH_IN_TEXT_FIELD_SMALL);
        characterProperties[index++] = parseNumberToOutput(character.getRangeCombatDamage(), TEXT_LENGTH_IN_TEXT_FIELD_SMALL);
        characterProperties[index] = parseNumberToOutput(character.getRangeCombatReach(), TEXT_LENGTH_IN_TEXT_FIELD_SMALL);
        setOptions(characterProperties);

        var image = "";
        for (SupportedCharactersEnum hero : SupportedCharactersEnum.values()) {
            if (character.getName().equals(hero.getName())) {
                image = hero.getName() + ".png";
            }
        }
        if (image.equals("")) image = IMAGE_OF_UNKNOWN_CHARACTER;
        characterImageName = image;
    }

    /**
     * Removing the character by deleting his label and all of his information from the saved lists.
     * In case this character's information was just shown on the properties pane, now an empty properties pane will be displayed.
     *
     * @param character that needs to be deleted
     */
    public void removeCharacter(CharacterConfig character) {
        if (selected >= 0) {
            if (characters.get(selected) == character) {
                setNoSelectedCharacter();
            } else if (selected >= characters.indexOf(character)) {
                selected--;
            }
        }
        if (characterScreen != null) {
            var characterLabel = characterScreen.getCharacterLabels().get(characters.indexOf(character));
            characterLabel.remove();
            characterScreen.getCharacterLabels().remove(characterLabel);
            characterScreen.getCharacterSelectionTable().pack();
        }
        characters.remove(character);
    }

    /**
     * Called when opening the {@link CharacterConfigScreen} to generate a new random character configuration.
     * Uses {@link CharacterConfigLogic#generateRandomCharacterStats()} to generate random but useful values.
     * Contains all the elements of {@link SupportedCharactersEnum} ordered by their character IDs.
     *
     * @return list of 24 character configurations as an {@link CharacterConfigContainer} object
     */
    @Override
    public AbstractConfiguration generateRandomConfig() {
        var characterConfigList = new CharacterConfigContainer();
        for (var i = 0; i < CharacterConfigContainer.NEEDED_NR_OF_CHARACTERS; i++) {
            List<Integer> stats = generateRandomCharacterStats();
            var character = new CharacterConfig();
            SupportedCharactersEnum supportetCharacter = SupportedCharactersEnum.values()[i];
            character.setName(supportetCharacter.getName());
            character.setCharacterID(supportetCharacter.getId());
            character.setHp(randomIntInRange(USEFUL_MIN_VALUE_HP, USEFUL_MAX_VALUE_HP));
            var index = 0;
            character.setMp(stats.get(index++));
            character.setAp(stats.get(index++));
            if (stats.get(index) >= stats.get(index + 1)) { // the melee damage should be more than the range combat damage
                character.setMeleeDamage(stats.get(index++));
                character.setRangeCombatDamage(stats.get(index++));
            } else {
                character.setRangeCombatDamage(stats.get(index++));
                character.setMeleeDamage(stats.get(index++));
            }
            character.setRangeCombatReach(stats.get(index));
            characterConfigList.addCharacter(character);
        }
        return characterConfigList;
    }

    /**
     * Generating a list of character's attribute values.
     * The method ensures that the values make sense, but still allows them to be pretty random.
     * The method's calculation also ensures, that if a character for example has an advantage by having really high HP,
     * his other values will probably give him disadvantages by being lower.
     * By doing so the random generator should actually already produce a playable group of characters most of the times.
     *
     * @return list of values to use as attributes for a character
     */
    private List<Integer> generateRandomCharacterStats() {
        List<Integer> featurePointDistribution = new LinkedList<>();
        var totalFeaturePoints = randomIntInRange(USEFUL_MIN_VALUE_TOTAL_FEATURE_POINTS, USEFUL_MAX_VALUE_TOTAL_FEATURE_POINTS);
        for (var i = 0; i < CharacterConfig.NR_OF_PARAMETERS - 3; i++) { // not for name, hp and character ID
            var points = randomIntInRange(USEFUL_MIN_VALUE_FEATURE_POINTS, totalFeaturePoints / 2);
            featurePointDistribution.add(points);
            totalFeaturePoints -= points;
        }
        featurePointDistribution.add(totalFeaturePoints);
        Collections.shuffle(featurePointDistribution);
        return featurePointDistribution;
    }

    /**
     * Sums up all the saved characters of this {@link CharacterConfigScreen} into one list.
     *
     * @return list of all (displayed) characters as an {@link CharacterConfigContainer} object
     */
    @Override
    public AbstractConfiguration getConfigFromUI() {
        updateSelectedCharacterValuesFromUI();
        return new CharacterConfigContainer(characters);
    }

    /**
     * Adds each single character of the given configuration list to the {@link CharacterConfigScreen} by using
     * {@link CharacterConfigLogic#addCharacter(CharacterConfig)}.
     * Deletes all the old characters.
     *
     * @param config list of characters (will always be 24) as an {@link CharacterConfigContainer} object,
     *               that needs to be added in order to enable their editing
     */
    @Override
    public void setConfigToUI(AbstractConfiguration config) {
        for (var i = characters.size() - 1; i > -1; i--) {
            removeCharacter(characters.get(i));
        }
        if (characterScreen != null && characterScreen.isCharacterOverviewSet()) {
            characterScreen.deleteCharacterSelectionPane();
            characterScreen.addCharacterSelectionPane();
        }
        List<CharacterConfig> characterList = ((CharacterConfigContainer) config).getCharacters();
        for (CharacterConfig characterConfig : characterList) {
            addCharacter(characterConfig);
        }
        AbstractConfigurationScreen.setChangesWereMade(false);
        changeSelectedCharacter(characters.get(0));
    }

    public String getCharacterImageName() {
        return characterImageName;
    }

    public List<CharacterConfig> getCharacters() {
        return characters;
    }
}