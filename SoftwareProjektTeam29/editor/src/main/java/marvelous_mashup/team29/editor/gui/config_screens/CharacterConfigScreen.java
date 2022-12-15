package marvelous_mashup.team29.editor.gui.config_screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import marvelous_mashup.team29.editor.Editor;
import marvelous_mashup.team29.editor.logic.CharacterConfigLogic;
import marvelous_mashup.team29.editor.model.AbstractConfiguration;
import marvelous_mashup.team29.editor.model.CharacterConfig;
import marvelous_mashup.team29.ui_util.AbstractGameWithMusic;
import marvelous_mashup.team29.ui_util.UIConstants;
import marvelous_mashup.team29.ui_util.components.UIComponentGroups;
import marvelous_mashup.team29.ui_util.components.UIShapes;
import marvelous_mashup.team29.ui_util.pop_ups.PopUpDecision;
import marvelous_mashup.team29.ui_util.screens.AbstractScreen;
import marvelous_mashup.team29.ui_util.styling.ComponentCreator;

import java.util.LinkedList;
import java.util.List;

import static marvelous_mashup.team29.ui_util.UIConstants.*;
import static marvelous_mashup.team29.ui_util.styling.ComponentCreator.configureTexture;

/**
 * Class that manages the UI elements of the "Character Configuration" screen.
 */
public class CharacterConfigScreen extends AbstractConfigurationScreen {
    private static final String REMOVE_CHARACTER_TEXT = "[-]  ";
    private static final float REMOVE_CHARACTER_TEXT_WIDTH = 2 * WINDOW_BORDER;

    private final CharacterConfigLogic characterLogic;
    private final List<Label> characterLabels = new LinkedList<>();
    private final TextField[] characterProperties = new TextField[CharacterConfig.NR_OF_PARAMETERS];

    private final Vector2 selectionPaneStart = new Vector2(mainAreaStartX + 2 * WINDOW_BORDER, contentStartY + 2 * WINDOW_BORDER);
    private final Vector2 selectionPaneDimension = new Vector2(mainAreaWidth / 3, contentHeight - 4 * WINDOW_BORDER);
    private final Vector2 propertiesPaneStart = new Vector2(selectionPaneStart.x + selectionPaneDimension.x + 2 * WINDOW_BORDER, contentStartY + 2 * WINDOW_BORDER);
    private final Vector2 propertiesPaneDimension = new Vector2(mainAreaWidth - propertiesPaneStart.x + mainAreaStartX - 2 * WINDOW_BORDER, contentHeight - 4 * WINDOW_BORDER);

    private final SpriteBatch spriteBatch = new SpriteBatch();
    private Table characterSelectionTable;
    private Table characterPropertiesTable;
    private int propertyIndex = 0;
    private Button addCharacterButton;
    private ScrollPane characterScrollPane;
    private boolean characterOverviewSet = false;

    public CharacterConfigScreen(Editor editorInstance) {
        super(editorInstance, "Character Configuration");

        addCharacterSelectionPane();
        addCharacterPropertyPane();

        setLogic(new CharacterConfigLogic(this, characterProperties));
        characterLogic = (CharacterConfigLogic) super.logic;

        characterLogic.setConfigToUI(characterLogic.generateRandomConfig()); // delete this line if you want to start with an empty configuration
        if (characterLogic.getCharacters().isEmpty()) characterLogic.setNoSelectedCharacter();
    }

    /**
     * Adding the left side's content onto the screen: the "add character" button, as well as the table of all
     * current character names.
     */
    public void addCharacterSelectionPane() {
        addCharacterButton = ComponentCreator.configureButton("ADD CHARACTER");
        addCharacterButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (isFileChooserClosed()) {
                    AbstractConfigurationScreen.setChangesWereMade(true);
                    characterLogic.addCharacter();
                }
            }
        });

        characterSelectionTable = new Table();
        characterSelectionTable.defaults().pad(2);
        characterSelectionTable.pack();
        characterSelectionTable.setTransform(false);
        characterScrollPane = ComponentCreator.configureScrollPane(characterSelectionTable, getStage());
        characterScrollPane.setSize(selectionPaneDimension.x - 3 * WINDOW_BORDER, selectionPaneDimension.y / 1.5f);

        UIComponentGroups.configureVerticalGroup(getStage(),
                selectionPaneStart.y + selectionPaneDimension.y - WINDOW_BORDER,
                selectionPaneStart.x + selectionPaneDimension.x / 2, -12 * WINDOW_BORDER,
                new LinkedList<>(List.of(addCharacterButton, characterScrollPane)));
        characterOverviewSet = true;
    }

    /**
     * Adding the left side's content from the screen.
     * Needed for {@link CharacterConfigLogic#setConfigToUI(AbstractConfiguration)} to reformat the table.
     */
    public void deleteCharacterSelectionPane() {
        addCharacterButton.remove();
        characterSelectionTable.remove();
        characterScrollPane.remove();
    }

    /**
     * This function is called by {@link CharacterConfigLogic#addCharacter(CharacterConfig)} whenever a new character is added.
     * This character now gets his own label in the character selection table, that can be clicked to select or delete him.
     * The label itself is divided into an area to delete the character and into an area to open the information of the character.
     *
     * @param character that needs to be added into the character selection table
     */
    public void addCharacterSelectionEntry(CharacterConfig character) {
        var characterLabel = ComponentCreator.configureLabel(REMOVE_CHARACTER_TEXT + character.getName());
        characterLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AbstractGameWithMusic.playButtonClickSound();
                if (isFileChooserClosed()) {
                    if (x < REMOVE_CHARACTER_TEXT_WIDTH) {
                        showRemoveCharacterPopUp(character);
                    } else {
                        characterLogic.changeSelectedCharacter(character);
                    }
                }
            }
        });
        characterSelectionTable.add(characterLabel).align(Align.left).width(16 * WINDOW_BORDER);
        characterSelectionTable.row();
        characterLabels.add(characterLabel);
    }

    /**
     * Adding the left side's content onto the screen: all the characters values (those can be changed using the text boxes).
     * (the characters picture is not included here)
     */
    private void addCharacterPropertyPane() {
        characterPropertiesTable = new Table();
        characterPropertiesTable.setPosition(propertiesPaneStart.x, propertiesPaneStart.y);
        characterPropertiesTable.setSize(propertiesPaneDimension.x, propertiesPaneDimension.y);
        characterPropertiesTable.defaults().pad(5);
        addComponent(characterPropertiesTable);

        addCharacterPropertyEntry("Name", false);
        addCharacterPropertyEntry("HP", true);
        addCharacterPropertyEntry("MP", true);
        addCharacterPropertyEntry("AP", true);
        addCharacterPropertyEntry("Melee Damage", true);
        addCharacterPropertyEntry("Range Combat Damage", true);
        addCharacterPropertyEntry("Range Combat Reach", true);

        characterProperties[0].addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                characterLogic.selectedCharacterNameChanged(characterProperties[0].getText());
            }
        });
    }

    /**
     * Adding one line of the characters properties including a description label and a text box.
     * Because the text boxes only allow 3 digits between 0-9 [for the characters values] or 20 ascii-letters
     * [for the characters name] as an input, this already prevents most invalid or nonsense values from being entered.
     *
     * @param description of the property that needs to be displayed
     * @param isNumber    is this text box supposed to get a number as the input? (yes/no)
     */
    private void addCharacterPropertyEntry(String description, boolean isNumber) {
        var label = ComponentCreator.configureLabel(description);
        characterPropertiesTable.add(label).align(Align.left).width(WINDOW_BORDER * 14);

        TextField textField = (isNumber) ? ComponentCreator.configureSmallNumberTextField(false) : ComponentCreator.configureTextTextField(false);
        textField.setTextFieldListener((field, key) -> AbstractConfigurationScreen.setChangesWereMade(true));
        int width = (isNumber) ? UIConstants.TEXT_FIELD_WIDTH_SMALL : UIConstants.TEXT_FIELD_WIDTH_BIG;
        characterPropertiesTable.add(textField).align(Align.left).width(width).height(UIConstants.TEXT_FIELD_HEIGHT_MEDIUM);
        characterProperties[propertyIndex] = textField;
        propertyIndex++;
        characterPropertiesTable.row();
    }

    /**
     * This method is called whenever the [-] button of a character in the character selection table is clicked.
     * The user now needs to confirm, that he really wants to remove this character.
     * If he does so {@link CharacterConfigLogic#removeCharacter(CharacterConfig)} is called.
     *
     * @param character that will be removed if this pop-up is confirmed
     */
    private void showRemoveCharacterPopUp(CharacterConfig character) {
        new PopUpDecision("Do you really want to delete " + character.getName() + "?", getStage()) {
            @Override
            public void confirm(Dialog dialog) {
                dialog.remove();
                characterLogic.removeCharacter(character);
                AbstractConfigurationScreen.setChangesWereMade(true);
            }

            @Override
            public void deny(Dialog dialog) {
                dialog.remove();
            }
        };
    }

    /**
     * Calling the render method of {@link AbstractScreen}.
     * Additionally here the character's image and the left and right area on the screen are rendered.
     */
    @Override
    public void render(float delta) {
        super.render(0);
        UIShapes.renderAreaWithBorder(selectionPaneStart.x, selectionPaneStart.y, selectionPaneDimension.x,
                selectionPaneDimension.y, 1f);
        UIShapes.renderAreaWithBorder(propertiesPaneStart.x, propertiesPaneStart.y, propertiesPaneDimension.x,
                propertiesPaneDimension.y, 1f);
        renderCharacterImage();

        getStage().act();
        getStage().draw();
    }

    /**
     * Adding the character's image with a nice frame around it onto the right side of the screen.
     * As long as the character's name doesn't match any name of the 24 supported characters, a dummy picture is displayed.
     */
    private void renderCharacterImage() {
        final float PICTURE_BORDER_WIDTH = WINDOW_BORDER * 10;
        final float PICTURE_BORDER_HEIGHT = WINDOW_BORDER * 10;
        final float PICTURE_BORDER_START_X = WINDOW_WIDTH - PICTURE_BORDER_WIDTH - WINDOW_BORDER * 7f;
        final float PICTURE_BORDER_START_Y = WINDOW_CENTER_Y - WINDOW_BORDER * 5.5f;

        var characterImage = configureTexture("characters/complete/" + characterLogic.getCharacterImageName());

        float imageScalingFactor = (characterImage.getWidth() >= characterImage.getHeight()) ?
                (PICTURE_BORDER_WIDTH - WINDOW_BORDER) / characterImage.getWidth() :
                (PICTURE_BORDER_HEIGHT - WINDOW_BORDER) / characterImage.getHeight();
        float pictureWidth = imageScalingFactor * characterImage.getWidth();
        float pictureHeight = imageScalingFactor * characterImage.getHeight();

        UIShapes.renderBorder(PICTURE_BORDER_START_X, PICTURE_BORDER_START_Y, PICTURE_BORDER_WIDTH, PICTURE_BORDER_HEIGHT);

        spriteBatch.begin();
        spriteBatch.draw(characterImage, PICTURE_BORDER_START_X + 0.5f * PICTURE_BORDER_WIDTH - 0.5f * pictureWidth,
                PICTURE_BORDER_START_Y + 0.5f * PICTURE_BORDER_HEIGHT - 0.5f * pictureHeight, pictureWidth, pictureHeight);
        spriteBatch.end();
    }

    /**
     * Called whenever a characters name is changed.
     * Sets the new name along with the option to delete this character as a prefix.
     *
     * @param index of the character, who's name needs to be changed, in the characterLabels list
     * @param text  new name
     */
    public void setCharacterLabelText(int index, String text) {
        characterLabels.get(index).setText(REMOVE_CHARACTER_TEXT + text);
    }

    public List<Label> getCharacterLabels() {
        return characterLabels;
    }

    public Table getCharacterSelectionTable() {
        return characterSelectionTable;
    }

    public boolean isCharacterOverviewSet() {
        return characterOverviewSet;
    }

    @Override
    public void dispose() {
        super.dispose();
        spriteBatch.dispose();
    }
}