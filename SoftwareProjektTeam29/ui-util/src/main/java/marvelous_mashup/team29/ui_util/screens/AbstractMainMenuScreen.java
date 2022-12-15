package marvelous_mashup.team29.ui_util.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import marvelous_mashup.team29.ui_util.AbstractGameWithMusic;
import marvelous_mashup.team29.ui_util.UISettings;
import marvelous_mashup.team29.ui_util.components.UIComponentGroups;
import marvelous_mashup.team29.ui_util.options.ComponentsSizeEnum;
import marvelous_mashup.team29.ui_util.options.TextSizeEnum;
import marvelous_mashup.team29.ui_util.pop_ups.PopUpDecision;
import marvelous_mashup.team29.ui_util.styling.ComponentCreator;

import java.util.LinkedList;
import java.util.List;

import static marvelous_mashup.team29.ui_util.UIConstants.*;
import static marvelous_mashup.team29.ui_util.components.UIComponentGroups.configureVerticalGroup;
import static marvelous_mashup.team29.ui_util.styling.ComponentCreator.configureTexture;

/**
 * Providing a configurable template for a main menu screen.
 * For information on how to use this screen, see editor/screens/MainMenuScreen.
 */
public abstract class AbstractMainMenuScreen extends AbstractScreen {

    private static final Sprite mute_icon = new Sprite((Texture) ASSET_FINDER.get("icons/mute.png"));
    private static final Sprite unmute_icon = new Sprite((Texture) ASSET_FINDER.get("icons/unmute.png"));
    private final List<Actor> buttons = new LinkedList<>();
    private final SpriteBatch spriteBatch = new SpriteBatch(); // for rendering the logo
    private final Texture logo;
    private final float logoWidth;
    private final float logoHeight;

    protected AbstractMainMenuScreen(String logoSourcePath) {
        super(MainAreaSizeEnum.SMALL_SCREEN, "");

        this.logo = configureTexture(logoSourcePath);
        float logoYToXRatio = (float) logo.getHeight() / (float) logo.getWidth();
        logoWidth = mainAreaWidth - 2 * WINDOW_BORDER;
        logoHeight = logoWidth * logoYToXRatio;

        addContents();
        addSettingsButton();
        addExitButton();
        addMuteButton();
        addTeamName();

        configureVerticalGroup(getStage(), mainAreaStartY + mainAreaHeight - logoHeight - 3.5f * WINDOW_BORDER,
                WINDOW_CENTER_X, WINDOW_BORDER * 1.5f, buttons);
    }

    /**
     * Possibility for displaying own elements (Buttons etc) on the main menu screen.
     */
    protected abstract void addContents();

    /**
     * Method called whenever a Button is configured successfully.
     * The button is now added to the list, that will in the end be displayed on the screen using
     * {@link UIComponentGroups#configureVerticalGroup(Stage, float, float, float, List)}.
     *
     * @param button to be added to the main menu screen's button list
     */
    protected void addButton(Button button) {
        buttons.add(button);
    }

    /**
     * Adding the settings-button to the main menu screen.
     */
    private void addSettingsButton() {
        var settings = ComponentCreator.configureButton("SETTINGS", ComponentsSizeEnum.COMPONENT_WIDTH_BIG);
        settings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                switchToSettings();
            }
        });
        addButton(settings);
    }

    /**
     * Method called when the settings-Button is clicked.
     * The action that's happening now should be specified in the specific main menu screen.
     */
    protected abstract void switchToSettings();

    /**
     * Adding the exit-game-button to the main menu screen.
     */
    private void addExitButton() {
        var exit = ComponentCreator.configureButton("EXIT APPLICATION", ComponentsSizeEnum.COMPONENT_WIDTH_BIG);
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                exitApplication();
            }
        });
        addButton(exit);
    }

    /**
     * Method called when the exit-game-Button is clicked.
     */
    private void exitApplication() {
        new PopUpDecision("""
                Do you really want to exit the application?
                """, getStage()) {
            @Override
            public void confirm(Dialog dialog) {
                Gdx.app.exit();
            }

            @Override
            public void deny(Dialog dialog) {
                dialog.remove();
            }
        };
    }

    /**
     * Adding a image button with a mute icon to be able to mute the annoying music directly on the main menu screen.
     */
    private void addMuteButton() {
        mute_icon.setColor(UISettings.getStylingDefaults().getLogoColor());
        unmute_icon.setColor(UISettings.getStylingDefaults().getLogoColor());
        Sprite icon = (AbstractGameWithMusic.isSoundsMuted()) ? mute_icon : unmute_icon;
        configureMuteButton(icon);
    }

    /**
     * Whenever the mute button got clicked the button click sound is played, {@link AbstractMainMenuScreen#muteApplication()}
     * is called and a new button with the opposite texture (mute vs. unmute) is displayed on its position.
     */
    private void muteButtonClicked() {
        muteApplication();
        Sprite icon = (AbstractGameWithMusic.isSoundsMuted()) ? mute_icon : unmute_icon;
        configureMuteButton(icon);
    }

    /**
     * Configures the mute/unmute button and directly adds it to the stage.
     *
     * @param icon that the button is supposed to show (mute/unmute) sign
     */
    private void configureMuteButton(Sprite icon) {
        var muteButton = new ImageButton(new SpriteDrawable(icon));
        muteButton.setPosition(mainAreaStartX + WINDOW_BORDER * 1.5f, mainAreaStartY + WINDOW_BORDER);
        muteButton.setTransform(true);
        muteButton.setScale(SCALING_FACTOR * 0.4f);
        muteButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                muteButton.remove();
                muteButtonClicked();
            }
        });
        addComponent(muteButton);
    }

    /**
     * Method called when the settings-Button is clicked.
     * The action that's happening now should be specified in the specific main menu screen.
     */
    protected abstract void muteApplication();

    /**
     * Adding a label with our team name to the bottom of the main menu screen.
     */
    private void addTeamName() {
        var label = ComponentCreator.configureLabel("BY TEAM 29", UISettings.getStylingDefaults().getFontStyle(),
                TextSizeEnum.SMALL, UISettings.getStylingDefaults().getLogoColor());
        label.setPosition(mainAreaStartX + mainAreaWidth - label.getWidth() - WINDOW_BORDER * 1.5f,
                mainAreaStartY + WINDOW_BORDER);
        addComponent(label);
    }

    /**
     * Calling the rendering-method of {@link AbstractScreen} and additionally rendering the logo.
     */
    @Override
    public void render(float delta) {
        super.render(0);
        spriteBatch.begin();
        spriteBatch.draw(logo, WINDOW_CENTER_X - logoWidth / 2f, mainAreaStartY + mainAreaHeight
                - WINDOW_BORDER - logoHeight, logoWidth, logoHeight);
        spriteBatch.end();
    }

    /**
     * Calling the dispose-method of {@link AbstractScreen} and additionally disposing the logo stuff.
     */
    @Override
    public void dispose() {
        super.dispose();
        spriteBatch.dispose();
    }
}
