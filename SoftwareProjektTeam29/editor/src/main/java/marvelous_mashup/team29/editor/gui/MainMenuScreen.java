package marvelous_mashup.team29.editor.gui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import marvelous_mashup.team29.editor.Editor;
import marvelous_mashup.team29.ui_util.AbstractGameWithMusic;
import marvelous_mashup.team29.ui_util.options.ComponentsSizeEnum;
import marvelous_mashup.team29.ui_util.screens.AbstractMainMenuScreen;
import marvelous_mashup.team29.ui_util.styling.ComponentCreator;

/**
 * Class that manages the Editor's main menu screen.
 * Mostly just uses the functionality of {@link AbstractMainMenuScreen} and adds a few own buttons for switching
 * to the configuration screens.
 */
public class MainMenuScreen extends AbstractMainMenuScreen {
    private final Editor editorInstance;

    public MainMenuScreen(Editor editorInstance) {
        super("general/logo_editor.png");
        this.editorInstance = editorInstance;
    }

    @Override
    protected void addContents() {
        addCharacterConfigButton();
        addMatchConfigButton();
        addScenarioConfigButton();
    }

    /**
     * Adding the character-config-button to the main menu screen.
     */
    private void addCharacterConfigButton() {
        var characterConfig = ComponentCreator.configureButton("CHARACTER CONFIGURATION", ComponentsSizeEnum.COMPONENT_WIDTH_BIG);
        characterConfig.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                editorInstance.changeScreen(ScreenEnum.CHARACTER_CONFIG);
            }
        });
        addButton(characterConfig);
    }

    /**
     * Adding the match-config-button to the main menu screen.
     */
    private void addMatchConfigButton() {
        var matchConfig = ComponentCreator.configureButton("MATCH CONFIGURATION", ComponentsSizeEnum.COMPONENT_WIDTH_BIG);
        matchConfig.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                editorInstance.changeScreen(ScreenEnum.MATCH_CONFIG);
            }
        });
        addButton(matchConfig);
    }

    /**
     * Adding the scenario-config-button to the main menu screen.
     */
    private void addScenarioConfigButton() {
        var scenarioConfig = ComponentCreator.configureButton("SCENARIO CONFIGURATION", ComponentsSizeEnum.COMPONENT_WIDTH_BIG);
        scenarioConfig.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                editorInstance.changeScreen(ScreenEnum.SCENARIO_CONFIG);
            }
        });
        addButton(scenarioConfig);
    }

    /**
     * Method called when the settings-button is clicked.
     * The view will now change to the {@link SettingsScreen}.
     */
    @Override
    protected void switchToSettings() {
        editorInstance.changeScreen(ScreenEnum.SETTINGS);
    }

    /**
     * Method called when the settings-Button is clicked.
     * Muting or un-muting the background music and sfx.
     */
    @Override
    protected void muteApplication() {
        AbstractGameWithMusic.setSoundsMuted();
    }
}
