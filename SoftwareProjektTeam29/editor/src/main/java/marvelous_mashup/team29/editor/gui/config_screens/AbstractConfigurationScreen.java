package marvelous_mashup.team29.editor.gui.config_screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import marvelous_mashup.team29.editor.Editor;
import marvelous_mashup.team29.editor.gui.ScreenEnum;
import marvelous_mashup.team29.editor.gui.file_io_dialogs.FileChooserDialog;
import marvelous_mashup.team29.editor.gui.file_io_dialogs.FileChooserEnum;
import marvelous_mashup.team29.editor.logic.AbstractConfigurationLogic;
import marvelous_mashup.team29.editor.logic.parsing.FileIOLogic;
import marvelous_mashup.team29.editor.model.AbstractConfiguration;
import marvelous_mashup.team29.ui_util.UIConstants;
import marvelous_mashup.team29.ui_util.pop_ups.PopUpDecision;
import marvelous_mashup.team29.ui_util.screens.AbstractScreen;
import marvelous_mashup.team29.ui_util.screens.MainAreaSizeEnum;
import marvelous_mashup.team29.ui_util.styling.ComponentCreator;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

import static marvelous_mashup.team29.ui_util.UIConstants.WINDOW_BORDER;

/**
 * Abstract class to inherit in {@link CharacterConfigScreen}, {@link MatchConfigScreen} and {@link ScenarioConfigScreen}.
 * Setting the main layout that is universal for all the Editor's configuration screens and managing
 * the File Choosers whenever they are needed.
 */
public abstract class AbstractConfigurationScreen extends AbstractScreen {
    private static boolean changesWereMade;
    protected final float contentStartY;
    protected final float contentHeight;
    private final Editor editorInstance;
    private final FileIOLogic fileIOLogic;
    private final AtomicBoolean fileReceived = new AtomicBoolean();
    protected AbstractConfigurationLogic logic;
    private FileChooserEnum fileChooserOpen = FileChooserEnum.NONE;
    private File configurationFile;
    private AbstractConfiguration configToSave;

    protected AbstractConfigurationScreen(Editor editorInstance, String title) {
        super(MainAreaSizeEnum.BIG_SCREEN, title);
        contentStartY = mainAreaStartY + UIConstants.COMPONENT_HEIGHT + 2f * WINDOW_BORDER;
        contentHeight = mainAreaHeight - UIConstants.COMPONENT_HEIGHT - 5f * WINDOW_BORDER;
        this.editorInstance = editorInstance;
        fileIOLogic = new FileIOLogic(editorInstance.getConfigurationType(), getStage());
        changesWereMade = false;

        addMenuButtons();
    }

    public static void setChangesWereMade(boolean changesWereMade) {
        AbstractConfigurationScreen.changesWereMade = changesWereMade;
    }

    /**
     * Adding the universal buttons to the screen.
     */
    private void addMenuButtons() {
        addBackToMainMenuButton();
        addSaveToFileButton();
        addLoadFromFileButton();
    }

    /**
     * Adding the return-button to the screen.
     */
    private void addBackToMainMenuButton() {
        var back = ComponentCreator.configureButton("RETURN");
        back.setPosition(mainAreaStartX + WINDOW_BORDER, mainAreaStartY + WINDOW_BORDER);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                returnToMainMenu();
            }
        });
        addComponent(back);
    }

    /**
     * Method called when the return-button is clicked.
     * Showing a PopUp and returning to main menu if that PopUp is confirmed.
     */
    private void returnToMainMenu() {
        if (isFileChooserClosed()) {
            if (!changesWereMade) editorInstance.changeScreen(ScreenEnum.MAIN_MENU);

            new PopUpDecision("""
                    Do you really want to return to the main menu?
                    All your unsaved changes will be lost.""", getStage()) {
                @Override
                public void confirm(Dialog dialog) {
                    dialog.remove();
                    editorInstance.changeScreen(ScreenEnum.MAIN_MENU);
                }

                @Override
                public void deny(Dialog dialog) {
                    dialog.remove();
                }
            };
        }
    }

    /**
     * Adding the save-button to the screen.
     */
    private void addSaveToFileButton() {
        var save = ComponentCreator.configureButton("SAVE CONFIG");
        save.setPosition(mainAreaStartX + mainAreaWidth - save.getWidth() - WINDOW_BORDER,
                mainAreaStartY + WINDOW_BORDER);
        save.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openSaveConfigDialog();
            }
        });
        addComponent(save);
    }

    /**
     * Method called when the save-button is clicked.
     * The currently displayed configuration will now directly be validated to show the user immediate feedback on
     * whether his configuration is valid or not.
     * Along this the configuration is also stored temporally, so changes on the values, while the file chooser is open,
     * won't change the configuration that will be saved.
     * Then opening a {@link FileChooserDialog} in a new thread.
     */
    private void openSaveConfigDialog() {
        if (isFileChooserClosed()) {
            configToSave = logic.getConfigFromUI();
            if (!fileIOLogic.validateConfig(configToSave)) return;

            fileChooserOpen = FileChooserEnum.SAVE_DIALOG;
            new Thread(() -> {
                configurationFile = FileChooserDialog.showFileChooser(FileChooserEnum.SAVE_DIALOG, editorInstance.getConfigurationType());
                fileReceived.set(true);
            }).start();
        }
    }

    /**
     * Adding the load-button to the screen.
     */
    private void addLoadFromFileButton() {
        var load = ComponentCreator.configureButton("LOAD CONFIG");
        load.setPosition(mainAreaStartX + mainAreaWidth - 2 * load.getWidth() - 2 * WINDOW_BORDER,
                mainAreaStartY + WINDOW_BORDER);
        load.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openLoadConfigDialog();
            }
        });
        addComponent(load);
    }

    /**
     * Method called when the load-button is clicked.
     * Opening a {@link FileChooserDialog} as a loading dialog in a new thread.
     */
    private void openLoadConfigDialog() {
        if (isFileChooserClosed()) {
            fileChooserOpen = FileChooserEnum.LOAD_DIALOG;
            new Thread(() -> {
                configurationFile = FileChooserDialog.showFileChooser(FileChooserEnum.LOAD_DIALOG, editorInstance.getConfigurationType());
                fileReceived.set(true);
            }).start();
        }
    }

    /**
     * Calling {@link AbstractScreen#render(float)} as well as checking whether a file chooser recently returned
     * a file (in a separate thread).
     * If that's the case, {@link AbstractConfigurationScreen#fileToLoadReceived(File)} is called.
     */
    @Override
    public void render(float delta) {
        super.render(delta);
        if (fileReceived.get()) {
            fileReceived.set(false);
            fileToLoadReceived(configurationFile);
        }
    }

    /**
     * Method called after a file chooser has been closed.
     * If that file chooser was a load file dialog the content of the given file is now loaded by
     * {@link FileIOLogic#loadConfigFromFile(File)} and afterwards the new config is validated.
     * Otherwise the config saved from the moment the "save config" button got clicked will be saved into the given
     * file now.
     *
     * @param file that just got selected to load or save a configuration
     */
    public void fileToLoadReceived(File file) {
        FileChooserEnum fileChooserType = fileChooserOpen;
        fileChooserOpen = FileChooserEnum.NONE;
        if (file == null) return;
        if (fileChooserType == FileChooserEnum.LOAD_DIALOG) {
            AbstractConfiguration config = fileIOLogic.loadConfigFromFile(file);
            if (fileIOLogic.validateConfig(config)) logic.setConfigToUI(config);
        } else {
            fileIOLogic.saveConfigToFile(file, configToSave);
            configToSave = null;
        }
    }

    /**
     * Because we are using a JFileChooser an nothing that's native to LibGdx, the underlying screen is not blocked
     * in our implementation by the file chooser (because it's in a separate thread).
     * So it's best to prohibit some possible actions while a file chooser is open to prevent errors.
     *
     * @return is there an open file chooser at the moment? (yes/no)
     */
    public boolean isFileChooserClosed() {
        return fileChooserOpen == FileChooserEnum.NONE;
    }

    protected void setLogic(AbstractConfigurationLogic logic) {
        this.logic = logic;
    }
}