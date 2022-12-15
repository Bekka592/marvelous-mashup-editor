package marvelous_mashup.team29.editor.gui.config_screens;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import marvelous_mashup.team29.editor.Editor;
import marvelous_mashup.team29.editor.logic.MatchConfigLogic;
import marvelous_mashup.team29.editor.model.MatchConfig;
import marvelous_mashup.team29.ui_util.UIConstants;
import marvelous_mashup.team29.ui_util.options.ColorEnum;
import marvelous_mashup.team29.ui_util.styling.ComponentCreator;

import static marvelous_mashup.team29.ui_util.UIConstants.WINDOW_BORDER;

/**
 * Class that manages the UI elements of the "Match Configuration" screen.
 */
public class MatchConfigScreen extends AbstractConfigurationScreen {
    private final TextField[] matchOptions = new TextField[MatchConfig.NR_OF_PARAMETERS];
    private final Table optionsTable = new Table();
    private int propertyIndex = 0;

    public MatchConfigScreen(Editor editorInstance) {
        super(editorInstance, "Match Configuration");
        addOptionsTable();
        setLogic(new MatchConfigLogic(getStage(), matchOptions));
        logic.setConfigToUI(logic.generateRandomConfig()); // delete this line if you want to start with an empty configuration
    }

    /**
     * Adding the text and the text boxes formatted as a table onto the screen.
     */
    protected void addOptionsTable() {
        optionsTable.setPosition(mainAreaStartX + 0.1f * mainAreaWidth, contentStartY + WINDOW_BORDER);
        optionsTable.setSize(mainAreaWidth * 0.8f, contentHeight);
        optionsTable.defaults().pad(WINDOW_BORDER / 4);

        final var ROUNDS = "rounds";
        final var TIME = "seconds";

        addEntry("Round limit until Thanos appears [0 = Thanos never appears]", ROUNDS, false);
        addEntry("Maximum time played until Thanos appears [0 = no time limit]", TIME, true);
        addEntry("Time limit for each game round", TIME, false);
        addEntry("Maximum response time for a client", TIME, false);
        addEntry("Maximum time the animation can last [0 = no time limit]", TIME, false);
        addEntry("Time limit for a game pause [0 = no pause allowed]", TIME, false);
        addEntry("Space Stone: cooldown time", ROUNDS, false);
        addEntry("Reality Stone: cooldown time", ROUNDS, false);
        addEntry("Power Stone: cooldown time", ROUNDS, false);
        addEntry("Time Stone: cooldown time", ROUNDS, false);
        addEntry("Soul Stone: cooldown time", ROUNDS, false);
        addEntry("Mind Stone: cooldown time", ROUNDS, false);
        addEntry("Mind Stone: damage value", "HP", false);
        addComponent(optionsTable);
    }

    /**
     * Adding one line including a description label, a text box and a unit label.
     * Because the text boxes only allow 4 digits (0-9) as an input, this already prevents most invalid or nonsense
     * values from being entered.
     * Also this enhances the usability of our editor, as it gives the user guidance about what to input into this text box.
     * So we decided not to emphasize nonsense values (letters, negative values, decimals, huge numbers,...)
     * for the user, but just to forbid entering those values anyway.
     *
     * @param description of the line that needs to be displayed
     * @param unit        of the line that needs to be displayed
     * @param optional    is this parameter defined as optional in the network standard document? (true/false)
     */
    private void addEntry(String description, String unit, boolean optional) {
        var label = ComponentCreator.configureLabel(description);
        optionsTable.add(label).width(WINDOW_BORDER * 38);

        var textField = ComponentCreator.configureBigNumberTextField(optional);
        textField.setTextFieldListener((field, key) -> AbstractConfigurationScreen.setChangesWereMade(true));
        optionsTable.add(textField).width(UIConstants.TEXT_FIELD_WIDTH_MEDIUM).height(UIConstants.TEXT_FIELD_HEIGHT_SMALL);
        matchOptions[propertyIndex] = textField;
        propertyIndex++;

        var unitLabel = ComponentCreator.configureLabel(unit);
        optionsTable.add(unitLabel).width(WINDOW_BORDER * 4);
        if (optional) {
            var optionalLabel = ComponentCreator.configureLabel("*optional", ColorEnum.THEME_COLOR.getColor());
            optionsTable.add(optionalLabel).width(WINDOW_BORDER * 3);
        }
        optionsTable.row();
    }
}