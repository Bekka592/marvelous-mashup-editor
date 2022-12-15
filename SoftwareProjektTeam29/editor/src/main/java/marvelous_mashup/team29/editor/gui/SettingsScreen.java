package marvelous_mashup.team29.editor.gui;

import marvelous_mashup.team29.editor.Editor;
import marvelous_mashup.team29.ui_util.screens.AbstractSettingsScreen;

/**
 * Class that manages the Editor's settings screen.
 * Mostly just uses the functionality of {@link AbstractSettingsScreen}.
 */
public class SettingsScreen extends AbstractSettingsScreen {

    private final Editor editorInstance;

    public SettingsScreen(Editor editorInstance) {
        super();
        this.editorInstance = editorInstance;
    }

    @Override
    protected void addContents() {
        addMusicVolumeSetting();
        addSoundEffectsVolumeSetting();
    }

    /**
     * Method called when the return-button is clicked.
     * The view will now change back to the {@link MainMenuScreen}.
     */
    @Override
    protected void returnToMainMenu() {
        editorInstance.changeScreen(ScreenEnum.MAIN_MENU);
    }
}
