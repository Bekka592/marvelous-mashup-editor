package marvelous_mashup.team29.ui_util.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import marvelous_mashup.team29.ui_util.AbstractGameWithMusic;
import marvelous_mashup.team29.ui_util.components.UIShapes;

import static marvelous_mashup.team29.ui_util.UIConstants.*;
import static marvelous_mashup.team29.ui_util.styling.ComponentCreator.configureTexture;

/**
 * Extending the basic {@link Screen} class of LibGdx.
 * Creating a screen with a background image and a plain white main area rendered in front of it.
 */
public class AbstractScreen implements Screen {
    public final float mainAreaStartX;
    public final float mainAreaStartY;
    public final float mainAreaWidth;
    public final float mainAreaHeight;
    private final Stage stage = new Stage(new ScreenViewport());
    private final SpriteBatch spriteBatch = new SpriteBatch();
    private final Texture backgroundTexture = configureTexture("general/background.jpg");

    /**
     * Defining the sizes of this screen according to whether it will be a full size screen (background picture
     * barely visible) or a menu screen (main area only takes about half of the window size).
     *
     * @param mainAreaSize size of the screen's main area as a {@link MainAreaSizeEnum}
     */
    protected AbstractScreen(MainAreaSizeEnum mainAreaSize, String title) {
        if (!title.equals("")) title = " - " + title;
        Gdx.graphics.setTitle("Marvelous Mashup " + AbstractGameWithMusic.getApplicationType().toString() + title);
        if (mainAreaSize == MainAreaSizeEnum.BIG_SCREEN) {
            mainAreaStartX = WINDOW_BORDER;
            mainAreaStartY = WINDOW_BORDER;
            mainAreaWidth = WINDOW_WIDTH - 2f * WINDOW_BORDER;
            mainAreaHeight = WINDOW_HEIGHT - 2f * WINDOW_BORDER;
        } else {
            mainAreaWidth = SCREEN_HEIGHT * (16f / 9f) * 0.28f;
            mainAreaHeight = SCREEN_HEIGHT * 0.6f;
            mainAreaStartX = (WINDOW_WIDTH - mainAreaWidth) / 2f;
            mainAreaStartY = (WINDOW_HEIGHT - mainAreaHeight) / 2f;
        }
    }

    /**
     * Method called whenever the screen becomes visible.
     * Sets the input processor to the new screen, so clicks will be registered.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Method called every few milliseconds to adapt the graphics etc.
     * Here used to render the background picture, the main area border, as well as the main area
     * surface (slightly transparent overlay over the background picture so the ui elements are better visible).
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderBackground();
        UIShapes.renderAreaWithBorder(mainAreaStartX, mainAreaStartY, mainAreaWidth, mainAreaHeight, 0.93f);
        stage.act();
        stage.draw();
    }

    /**
     * Called by {@link AbstractScreen#render(float)}.
     * Showing the background picture.
     */
    private void renderBackground() {
        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        spriteBatch.end();
    }

    /**
     * Method called whenever a new actor is added to this screen.
     * Enables the possibility to keep the screens stage private in this class.
     *
     * @param actor to get added to the stage
     */
    public void addComponent(Actor actor) {
        stage.addActor(actor);
    }

    /**
     * This method is currently only needed for the pop-ups, as they need to know the stage, that they display over.
     */
    public Stage getStage() {
        return stage;
    }

    @Override
    public void resize(int width, int height) {
        // ignored because the editors windows are not resizable
    }

    @Override
    public void pause() {
        // no actions taken
    }

    @Override
    public void resume() {
        // no actions taken
    }

    @Override
    public void hide() {
        // no actions taken
    }

    /**
     * Called when the application is exited.
     * Releasing all graphic resources to have a clean exit.
     */
    @Override
    public void dispose() {
        stage.dispose();
        spriteBatch.dispose();
    }
}