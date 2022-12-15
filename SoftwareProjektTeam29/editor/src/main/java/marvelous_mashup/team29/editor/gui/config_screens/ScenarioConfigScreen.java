package marvelous_mashup.team29.editor.gui.config_screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import marvelous_mashup.team29.editor.Editor;
import marvelous_mashup.team29.editor.logic.ScenarioConfigLogic;
import marvelous_mashup.team29.editor.model.ScenarioConfig;
import marvelous_mashup.team29.ui_util.AbstractGameWithMusic;
import marvelous_mashup.team29.ui_util.UIConstants;
import marvelous_mashup.team29.ui_util.camera.InputCameraController;
import marvelous_mashup.team29.ui_util.map.IHaveATileCallback;
import marvelous_mashup.team29.ui_util.map.MapWithUI;
import marvelous_mashup.team29.ui_util.options.ColorEnum;
import marvelous_mashup.team29.ui_util.screens.AbstractScreen;
import marvelous_mashup.team29.ui_util.styling.ComponentCreator;
import marvelous_mashup.team29.util.data.FieldState;
import marvelous_mashup.team29.util.map.IAmAMapTile;

import java.util.LinkedList;
import java.util.List;

import static marvelous_mashup.team29.ui_util.UIConstants.*;
import static marvelous_mashup.team29.ui_util.UISettings.setTileDimensions;
import static marvelous_mashup.team29.ui_util.components.UIComponentGroups.configureHorizontalGroup;

/**
 * Class that manages the UI elements of the "Scenario Configuration" screen.
 */
public class ScenarioConfigScreen extends AbstractConfigurationScreen {
    private static final float MAP_WIDTH_ON_SCREEN = WINDOW_WIDTH * 0.9f;
    private static final float MAP_HEIGHT_ON_SCREEN = WINDOW_HEIGHT * 0.6f;

    private final ScenarioConfigLogic scenarioLogic;
    private final TextField[] scenarioOptions = new TextField[ScenarioConfig.NR_OF_PARAMETERS];
    private int optionIndex = 0;

    private Stage mapStage;
    private InputMultiplexer inputMultiplexer;
    private MapWithUI map;

    public ScenarioConfigScreen(Editor editorInstance) {
        super(editorInstance, "Scenario Configuration");

        addNameOptions();
        addMapSizeOptions();
        addToolTipButton();
        addMapArea();

        setLogic(new ScenarioConfigLogic(this, scenarioOptions));
        scenarioLogic = (ScenarioConfigLogic) super.logic;
        scenarioLogic.setConfigToUI(scenarioLogic.generateRandomConfig()); // delete this line if you want to start with an empty configuration
    }

    /**
     * Adding the first line of text to the scenario config screen including the map name field and the author name field.
     */
    private void addNameOptions() {
        var mapNameLabel = ComponentCreator.configureLabel("Map Name");
        var mapNameTextField = ComponentCreator.configureTextTextField(false);
        mapNameTextField.setTextFieldListener((field, key) -> AbstractConfigurationScreen.setChangesWereMade(true));
        mapNameTextField.setSize(UIConstants.TEXT_FIELD_WIDTH_BIG, UIConstants.TEXT_FIELD_HEIGHT_MEDIUM);
        scenarioOptions[optionIndex++] = mapNameTextField;

        var authorNameLabel = ComponentCreator.configureLabel("Author Name");
        var authorNameTextField = ComponentCreator.configureTextTextField(true);
        authorNameTextField.setTextFieldListener((field, key) -> AbstractConfigurationScreen.setChangesWereMade(true));
        authorNameTextField.setSize(UIConstants.TEXT_FIELD_WIDTH_BIG, UIConstants.TEXT_FIELD_HEIGHT_MEDIUM);
        scenarioOptions[optionIndex++] = authorNameTextField;
        var optionalLabel = ComponentCreator.configureLabel("*optional", ColorEnum.THEME_COLOR.getColor());

        configureHorizontalGroup(getStage(), mainAreaStartY + mainAreaHeight - 3.5f * WINDOW_BORDER,
                new LinkedList<>(List.of(WINDOW_BORDER, 3 * WINDOW_BORDER, WINDOW_BORDER, WINDOW_BORDER / 2f)),
                new LinkedList<>(List.of(mapNameLabel, mapNameTextField, authorNameLabel, authorNameTextField, optionalLabel)));
    }

    /**
     * Adding the first line of text to the scenario config screen including the map size options and buttons to
     * apply these options.
     * The user can choose if he wants the map in those sizes to be a valid random one, full or rocks or full of stones.
     * Really cool special feature: pressing "enter" after the input of a number in one of the text areas will
     * directly result in a new random map generated with the given sizes.
     */
    private void addMapSizeOptions() {
        var mapSizeLabel = ComponentCreator.configureLabel("Map Size");

        var widthTextField = ComponentCreator.configureSmallNumberTextField(false);
        widthTextField.setSize(UIConstants.TEXT_FIELD_WIDTH_SMALL, UIConstants.TEXT_FIELD_HEIGHT_MEDIUM);
        widthTextField.setTextFieldListener((textField, key) -> {
            AbstractConfigurationScreen.setChangesWereMade(true);
            if (key == '\n' && isFileChooserClosed())
                setMap(scenarioLogic.changeMapSize(GenerateMapOptionsEnum.RANDOM));
        });
        scenarioOptions[optionIndex++] = widthTextField;

        var timesLabel = ComponentCreator.configureLabel("x");

        var heightTextField = ComponentCreator.configureSmallNumberTextField(false);
        heightTextField.setSize(UIConstants.TEXT_FIELD_WIDTH_SMALL, UIConstants.TEXT_FIELD_HEIGHT_MEDIUM);
        heightTextField.setTextFieldListener((textField, key) -> {
            AbstractConfigurationScreen.setChangesWereMade(true);
            if (key == '\n' && isFileChooserClosed())
                setMap(scenarioLogic.changeMapSize(GenerateMapOptionsEnum.RANDOM));
        });
        scenarioOptions[optionIndex] = heightTextField;

        var randomButton = ComponentCreator.configureButton("NEW RANDOM");
        randomButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (isFileChooserClosed()) setMap(scenarioLogic.changeMapSize(GenerateMapOptionsEnum.RANDOM));
            }
        });

        var onlyGrassButton = ComponentCreator.configureButton("ONLY GRASS");
        onlyGrassButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (isFileChooserClosed()) setMap(scenarioLogic.changeMapSize(GenerateMapOptionsEnum.ONLY_GRASS));
            }
        });

        var onlyRocksButton = ComponentCreator.configureButton("ONLY ROCKS");
        onlyRocksButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (isFileChooserClosed()) setMap(scenarioLogic.changeMapSize(GenerateMapOptionsEnum.ONLY_ROCKS));
            }
        });

        configureHorizontalGroup(getStage(), mainAreaStartY + mainAreaHeight - 7.5f * WINDOW_BORDER,
                new LinkedList<>(List.of(WINDOW_BORDER, 0.5f * WINDOW_BORDER, 0.5f * WINDOW_BORDER, 3 * WINDOW_BORDER, WINDOW_BORDER, WINDOW_BORDER)),
                new LinkedList<>(List.of(mapSizeLabel, widthTextField, timesLabel, heightTextField, randomButton, onlyGrassButton, onlyRocksButton)));
    }

    /**
     * Adding the (?) Button that will show a tooltip whenever the mouse is hovering over it.
     */
    private void addToolTipButton() {
        var helpIcon = new Sprite((Texture) ASSET_FINDER.get("icons/help.png"));
        helpIcon.setColor(ColorEnum.DARK_THEME_COLOR.getColor());
        var helpButton = new ImageButton(new SpriteDrawable(helpIcon));
        helpButton.setPosition(mainAreaStartX + mainAreaWidth - 4 * WINDOW_BORDER,
                mainAreaStartY + mainAreaHeight - 8.5f * WINDOW_BORDER);
        helpButton.setTransform(true);
        helpButton.setScale(SCALING_FACTOR * 0.4f);
        TextTooltip tooltip = ComponentCreator.configureToolTip("""
                Left click to place and delete stones.
                Double left click to place a portal.
                Right click and hold to move the map.
                Scroll the mouse wheel to zoom.""");
        tooltip.setInstant(true);
        helpButton.addListener(tooltip);
        addComponent(helpButton);
    }

    /**
     * Creating a second stage to render the map in and adding this stage to the screen as well.
     * This new stage contains a {@link FitViewport} to enable moving around the map.
     * Also the clicks inside this map area are now redirected to the map stage.
     */
    private void addMapArea() {
        var mapViewport = new FitViewport(MAP_WIDTH_ON_SCREEN, MAP_HEIGHT_ON_SCREEN);
        mapStage = new Stage(mapViewport);
        mapViewport.setScreenSize((int) MAP_WIDTH_ON_SCREEN, (int) MAP_HEIGHT_ON_SCREEN);
        mapViewport.setScreenPosition((int) (mainAreaStartX + mainAreaWidth / 2 - MAP_WIDTH_ON_SCREEN / 2), (int) contentStartY);
        setTileDimensions(new Vector2(MAP_WIDTH_ON_SCREEN, MAP_HEIGHT_ON_SCREEN));

        var camera = new InputCameraController((OrthographicCamera) mapStage.getCamera(), mainAreaWidth,
                mainAreaHeight, -mainAreaWidth, -mainAreaHeight);
        inputMultiplexer = new InputMultiplexer(getStage(), camera, mapStage);
    }

    /**
     * Calling the render method of {@link AbstractScreen}.
     * Additionally here the map stage gets rendered, so the map is visible and always displaying the current version.
     */
    @Override
    public void render(float delta) {
        super.render(0);
        mapStage.getViewport().apply();
        mapStage.act(delta);
        mapStage.draw();
        getStage().getViewport().apply();
        getStage().act();
        getStage().draw();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void dispose() {
        super.dispose();
        mapStage.dispose();
    }

    /**
     * @return the currently displayed map as an 2D-Array of {@link IAmAMapTile}
     */
    public IAmAMapTile[][] getMap() {
        return map.getMap();
    }

    /**
     * Method called whenever a new map gets loaded (for example after loading from a file or changing the map sizes).
     * The stage is cleared and afterwards filled by the new map.
     * The camera is automatically centered over the new map.
     *
     * @param newMap map to display now
     */
    public void setMap(FieldState[][] newMap) {
        if (newMap == null) return;
        mapStage.clear();
        var cam = ((OrthographicCamera) mapStage.getCamera());
        cam.position.x = MAP_WIDTH_ON_SCREEN / 2;
        cam.position.y = MAP_HEIGHT_ON_SCREEN / 2;
        cam.zoom = 1;
        map = new MapWithUI();
        map.loadMap(newMap);

        map.registerHandler(new IHaveATileCallback() {
            @Override
            public void tileClicked(int xPosition, int yPosition, FieldState fieldState) {
                map.setState(xPosition, yPosition, (fieldState == FieldState.GRASS) ? FieldState.ROCK : FieldState.GRASS);
                AbstractGameWithMusic.playButtonClickSound();
                AbstractConfigurationScreen.setChangesWereMade(true);
            }
            @Override
            public void tileDoubleClicked(int xPosition, int yPosition, FieldState fieldState) {
                map.setState(xPosition, yPosition, FieldState.PORTAL);
                AbstractGameWithMusic.playButtonClickSound();
                AbstractConfigurationScreen.setChangesWereMade(true);
            }
        });
        map.addToStage(mapStage);
    }
}