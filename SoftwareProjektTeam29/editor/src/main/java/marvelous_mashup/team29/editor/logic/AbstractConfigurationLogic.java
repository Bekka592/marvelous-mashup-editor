package marvelous_mashup.team29.editor.logic;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import marvelous_mashup.team29.editor.model.AbstractConfiguration;

import static marvelous_mashup.team29.ui_util.UIConstants.TEXT_LENGTH_IN_TEXT_FIELD_MEDIUM;
import static marvelous_mashup.team29.ui_util.UIConstants.TEXT_LENGTH_IN_TEXT_FIELD_SMALL;

/**
 * Abstract class to inherit in {@link CharacterConfigLogic}, {@link MatchConfigLogic} and {@link ScenarioConfigLogic}.
 * Providing a common interface and some standard helper functions for those classes.
 */
public abstract class AbstractConfigurationLogic {
    protected final TextField[] options;
    private final Stage stage;

    protected AbstractConfigurationLogic(Stage stage, TextField[] options) {
        this.stage = stage;
        this.options = options;
    }

    public abstract AbstractConfiguration generateRandomConfig();

    public abstract AbstractConfiguration getConfigFromUI();

    public abstract void setConfigToUI(AbstractConfiguration config);

    /**
     * @param data          integer that needs to be displayed in a text box
     * @param maxCharacters max length of the text box this integer will be displayed on
     * @return integer parsed to string, with special cases if the integer was not defined or too big.
     */
    protected String parseNumberToOutput(int data, int maxCharacters) {
        if (data < 0) {
            return "";
        } else if (maxCharacters == TEXT_LENGTH_IN_TEXT_FIELD_SMALL && data > 9999) {
            return "9999";
        } else if (maxCharacters == TEXT_LENGTH_IN_TEXT_FIELD_MEDIUM && data > 99999) {
            return "99999";
        } else {
            return Integer.toString(data);
        }

    }

    /**
     * Parses the input text from a specific text box into an Integer.
     *
     * @param input text that needs to be parsed
     * @return text from the text box as an Integer or Integer.MIN_VALUE if the text box was empty
     */
    protected int parseInputToNumber(String input) {
        if (input.equals("")) return Integer.MIN_VALUE;
        return Integer.parseInt(input);
    }

    /**
     * Getting the texts that are currently written inside the options text fields as strings.
     *
     * @return array of input texts as strings
     */
    public String[] getOptions() {
        var optionsAsString = new String[options.length];
        for (var i = 0; i < options.length; i++) {
            optionsAsString[i] = options[i].getText();
        }
        return optionsAsString;
    }

    /**
     * Setting the options text fields by filling them with strings.
     *
     * @param optionsAsString array of strings to fill into the text fields
     */
    public void setOptions(String[] optionsAsString) {
        for (var i = 0; i < options.length; i++) {
            options[i].setText(optionsAsString[i]);
        }
    }

    public Stage getStage() {
        return stage;
    }
}