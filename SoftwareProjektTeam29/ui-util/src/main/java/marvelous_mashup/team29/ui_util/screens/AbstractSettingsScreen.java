package marvelous_mashup.team29.ui_util.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import marvelous_mashup.team29.ui_util.AbstractGameWithMusic;
import marvelous_mashup.team29.ui_util.UIConstants;
import marvelous_mashup.team29.ui_util.components.UIComponentGroups;
import marvelous_mashup.team29.ui_util.options.ComponentsSizeEnum;
import marvelous_mashup.team29.ui_util.styling.ComponentCreator;

import java.util.LinkedList;
import java.util.List;

import static marvelous_mashup.team29.ui_util.UIConstants.WINDOW_BORDER;
import static marvelous_mashup.team29.ui_util.UIConstants.WINDOW_CENTER_X;
import static marvelous_mashup.team29.ui_util.components.UIComponentGroups.configureVerticalGroup;

/**
 * Providing a configurable template for a settings screen.
 * For information on how to use this screen, see editor/screens/SettingsScreen.
 */
public abstract class AbstractSettingsScreen extends AbstractScreen {
    private final List<Actor> actors = new LinkedList<>();

    protected AbstractSettingsScreen() {
        super(MainAreaSizeEnum.SMALL_SCREEN, "Settings");

        addContents();
        addBackToMainMenuButton();
        configureVerticalGroup(getStage(), mainAreaStartY + mainAreaHeight - 8 * WINDOW_BORDER,
                WINDOW_CENTER_X, 1.5f * WINDOW_BORDER, actors);
    }

    /**
     * Possibility for adding additional settings on the settings screen.
     */
    protected abstract void addContents();

    /**
     * Method called whenever a Setting (aka a descriptive Label and the corresponding actor) is configured successfully.
     * The Setting is now added to the list, that will in the end be displayed on the screen using
     * {@link UIComponentGroups#configureVerticalGroup(AbstractScreen, List)}.
     *
     * @param label  to be added to the main menu screen's
     * @param setter to be added to the main menu screen's
     */
    protected void addSetting(Label label, Actor setter) {
        actors.add(label);
        actors.add(setter);
        actors.add(ComponentCreator.configureLabel(""));
    }

    /**
     * Adding the description + slider for controlling the music volume to the settings screen.
     */
    protected void addMusicVolumeSetting() {
        var musicLabel = ComponentCreator.configureLabel("Music");
        musicLabel.setHeight(UIConstants.COMPONENT_HEIGHT / 3f);

        var musicSlider = ComponentCreator.configureSlider(ComponentsSizeEnum.COMPONENT_WIDTH_BIG);
        musicSlider.setValue(AbstractGameWithMusic.getMusicVolume());
        musicSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                adjustMusicVolume(musicSlider.getValue());
            }
        });
        musicSlider.setWidth(mainAreaWidth / 2f);
        musicSlider.setHeight(UIConstants.COMPONENT_HEIGHT / 2f);

        addSetting(musicLabel, musicSlider);
    }

    /**
     * Method called when the position of the music volume slider knob is changed.
     * Changing the music volume by using the {@link AbstractGameWithMusic}.
     */
    private void adjustMusicVolume(float newVolume) {
        AbstractGameWithMusic.setMusicVolume(newVolume);
    }

    /**
     * Adding the description + slider for controlling the sfx volume to the settings screen.
     */
    protected void addSoundEffectsVolumeSetting() {
        var sfxLabel = ComponentCreator.configureLabel("Sound Effects");
        sfxLabel.setHeight(UIConstants.COMPONENT_HEIGHT / 3f);

        var sfxSlider = ComponentCreator.configureSlider(ComponentsSizeEnum.COMPONENT_WIDTH_BIG);
        sfxSlider.setValue(AbstractGameWithMusic.getSfxVolume());
        sfxSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                adjustSoundEffectsVolume(sfxSlider.getValue());
            }
        });
        sfxSlider.setWidth(mainAreaWidth / 2f);
        sfxSlider.setHeight(UIConstants.COMPONENT_HEIGHT / 2f);

        addSetting(sfxLabel, sfxSlider);
    }

    /**
     * Method called when the position of the sfx volume slider knob is changed.
     * Changing the sfx volume by using the {@link AbstractGameWithMusic}.
     */
    private void adjustSoundEffectsVolume(float newVolume) {
        AbstractGameWithMusic.setSfxVolume(newVolume);
    }

    /**
     * Adding the option to return to the main menu.
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
     * Method called when the return-Button is clicked.
     * The action that's happening now should be specified in the specific settings screen.
     */
    protected abstract void returnToMainMenu();
}
