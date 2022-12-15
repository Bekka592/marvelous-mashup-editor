package marvelous_mashup.team29.editor;

import marvelous_mashup.team29.editor.gui.MainMenuScreen;
import marvelous_mashup.team29.editor.gui.ScreenEnum;
import marvelous_mashup.team29.editor.gui.SettingsScreen;
import marvelous_mashup.team29.editor.gui.config_screens.CharacterConfigScreen;
import marvelous_mashup.team29.editor.gui.config_screens.MatchConfigScreen;
import marvelous_mashup.team29.editor.gui.config_screens.ScenarioConfigScreen;
import marvelous_mashup.team29.editor.model.ConfigurationTypeEnum;
import marvelous_mashup.team29.ui_util.AbstractGameWithMusic;
import marvelous_mashup.team29.ui_util.ApplicationTypeEnum;
import marvelous_mashup.team29.ui_util.UISettings;
import marvelous_mashup.team29.ui_util.styling.EditorDefaults;

import javax.swing.*;

import static marvelous_mashup.team29.ui_util.UIConstants.ASSET_FINDER;

/**
 * Handling the Editors global data, managing screen changes and using the functionalities of
 * {@link AbstractGameWithMusic} to handle sounds.
 */
public class Editor extends AbstractGameWithMusic {
    private ConfigurationTypeEnum configurationType;

    @Override
    public void create() {
        UISettings.init(new EditorDefaults());
        setApplicationType(ApplicationTypeEnum.EDITOR);
        initSfx();
        setBackgroundMusic(ASSET_FINDER.get("music/Editor_Music.ogg"));
        this.setScreen(new MainMenuScreen(this));
        ASSET_FINDER.loadMapTextures();
        ASSET_FINDER.loadCharacterDisplayTextures();

        try { // giving the JFileChoosers a prettier UI
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
            // just take the standard look and feel then
        }
    }

    /**
     * Method called whenever the current screen needs to be changed.
     * Changes the screen and sets the parameter {@link Editor#configurationType}
     * accordingly, if the new screen is one of the three configuration screens.
     *
     * @param screen as a {@link SettingsScreen} that needs to be set to the GUI now
     */
    public void changeScreen(ScreenEnum screen) {
        switch (screen) {
            case MAIN_MENU:
                setScreen(new MainMenuScreen(this));
                break;
            case SETTINGS:
                setScreen(new SettingsScreen(this));
                break;
            case CHARACTER_CONFIG:
                ASSET_FINDER.finishLoading();
                configurationType = ConfigurationTypeEnum.CHARACTER;
                setScreen(new CharacterConfigScreen(this));
                break;
            case MATCH_CONFIG:
                configurationType = ConfigurationTypeEnum.MATCH;
                setScreen(new MatchConfigScreen(this));
                break;
            case SCENARIO_CONFIG:
                ASSET_FINDER.finishLoading();
                configurationType = ConfigurationTypeEnum.SCENARIO;
                setScreen(new ScenarioConfigScreen(this));
        }
    }

    public ConfigurationTypeEnum getConfigurationType() {
        return configurationType;
    }
}
