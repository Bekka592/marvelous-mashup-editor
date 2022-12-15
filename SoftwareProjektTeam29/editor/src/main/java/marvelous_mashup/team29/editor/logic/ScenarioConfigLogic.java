package marvelous_mashup.team29.editor.logic;

import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import marvelous_mashup.team29.editor.gui.config_screens.AbstractConfigurationScreen;
import marvelous_mashup.team29.editor.gui.config_screens.GenerateMapOptionsEnum;
import marvelous_mashup.team29.editor.gui.config_screens.ScenarioConfigScreen;
import marvelous_mashup.team29.editor.model.AbstractConfiguration;
import marvelous_mashup.team29.editor.model.ScenarioConfig;
import marvelous_mashup.team29.ui_util.pop_ups.PopUpAccept;
import marvelous_mashup.team29.util.data.FieldState;
import marvelous_mashup.team29.util.map.IAmAMapTile;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static marvelous_mashup.team29.editor.model.ParameterSpecifications.*;
import static marvelous_mashup.team29.ui_util.UIConstants.TEXT_LENGTH_IN_TEXT_FIELD_SMALL;
import static marvelous_mashup.team29.util.MathUtilities.randomIntInRange;

/**
 * Implements the logic of the {@link ScenarioConfigScreen} to give the user the option to interact with the screen.
 */
public class ScenarioConfigLogic extends AbstractConfigurationLogic {
    private final ScenarioConfigScreen scenarioScreen;

    public ScenarioConfigLogic(ScenarioConfigScreen scenarioScreen, TextField[] scenarioOptions) {
        super((scenarioScreen == null) ? null : scenarioScreen.getStage(), scenarioOptions);
        this.scenarioScreen = scenarioScreen;
    }

    /**
     * This method is called whenever one of the apply buttons (random/ only grass/ only rock) is clicked.
     * The sizes of the new map are now loaded from the text boxes and a new generated map with those
     * sizes will be returned, if the entered sizes are valid.
     *
     * @param option the user chose for his new map (can be: "random", "grass" or "rock")
     * @return generated map with the new sizes
     */
    public FieldState[][] changeMapSize(GenerateMapOptionsEnum option) {
        String[] inputs = getOptions();
        int height = parseInputToNumber(inputs[2]);
        int width = parseInputToNumber(inputs[3]);

        if (height > MAX_MAP_HEIGHT || width > MAX_MAP_WIDTH) {
            new PopUpAccept("This Editor only supports maps up to a size of 100 x 100 fields.", getStage());
            return null;
        } else if (height * width < MIN_NR_OF_GRASS_FIELDS + MIN_NR_OF_PORTAL_FIELDS) {
            new PopUpAccept("The map needs to contain at least " + (MIN_NR_OF_GRASS_FIELDS + MIN_NR_OF_PORTAL_FIELDS)
                    + " tiles.\nPlease enter bigger map sizes.", getStage());
            return null;
        }

        AbstractConfigurationScreen.setChangesWereMade(true);
        if (option == GenerateMapOptionsEnum.RANDOM) {
            return generateRandomMap(height, width);
        } else {
            return generateMapFilledWith(height, width, (option == GenerateMapOptionsEnum.ONLY_GRASS) ? FieldState.GRASS : FieldState.ROCK);
        }
    }

    /**
     * Generates a map with the given width and height that is filled completely by enum-objects of the given fieldState.
     *
     * @return generated map full of grass/stones
     */
    private FieldState[][] generateMapFilledWith(int mapHeight, int mapWidth, FieldState fieldState) {
        var map = new FieldState[mapHeight][mapWidth];
        for (var i = 0; i < mapHeight; i++) {
            for (var j = 0; j < mapWidth; j++) {
                map[i][j] = fieldState;
            }
        }
        return map;
    }

    /**
     * Converting 2D-Array of {@link IAmAMapTile} objects into a 2D-Array of {@link FieldState} objects,
     * because the editor doesn't require all the information of {@link IAmAMapTile}.
     *
     * @param mapTileMap 2D-Array of {@link IAmAMapTile} objects
     * @return 2D-Array of {@link FieldState} objects
     */
    public FieldState[][] convertToFieldStateMap(IAmAMapTile[][] mapTileMap) {
        var fieldStateMap = new FieldState[mapTileMap.length][mapTileMap[0].length];
        for (var i = 0; i < mapTileMap.length; i++) {
            for (var j = 0; j < mapTileMap[i].length; j++) {
                fieldStateMap[i][j] = mapTileMap[i][j].getFieldState();
            }
        }
        return fieldStateMap;
    }

    /**
     * Generating a new random map with the given width and height sizes.
     * The method also ensures that the new map contains enough empty grass and portal field tiles.
     *
     * @param mapHeight height of the new map
     * @param mapWidth  width of the new map
     * @return map "randomly" filled with stones and grass
     */
    public FieldState[][] generateRandomMap(int mapHeight, int mapWidth) {
        var map = new FieldState[mapHeight][mapWidth];
        int mapSize = mapHeight * mapWidth;

        var grassRatio = randomIntInRange(MIN_GRASS_RATIO, MAX_GRASS_RATIO);
        var portalRatio = randomIntInRange(MIN_PORTAL_RATIO, MAX_PORTAL_RATIO);
        var nrOfGrassFields = grassRatio*mapSize/100;
        var nrOfPortalFields = portalRatio*mapSize/100;

        List<FieldState> tiles = new LinkedList<>();
        for (var i=0; i<nrOfGrassFields; i++) tiles.add(FieldState.GRASS);
        for (var i=0; i<nrOfPortalFields; i++) tiles.add(FieldState.PORTAL);
        for (var i=0; i<mapSize-nrOfGrassFields-nrOfPortalFields; i++) tiles.add(FieldState.ROCK);
        Collections.shuffle(tiles);

        // adding more grass/portals (and therefore removing random tiles) if there are too less of them
        while (nrOfGrassFields < MIN_NR_OF_GRASS_FIELDS || nrOfPortalFields < MIN_NR_OF_PORTAL_FIELDS) {
            FieldState removedTile = tiles.remove(tiles.size()-1);
            if (removedTile == FieldState.GRASS) {
                nrOfGrassFields--;
            } else if (removedTile == FieldState.PORTAL) {
                nrOfPortalFields--;
            }

            if (nrOfPortalFields < MIN_NR_OF_PORTAL_FIELDS) {
                tiles.add(FieldState.PORTAL);
                nrOfPortalFields++;
            } else {
                tiles.add(FieldState.GRASS);
                nrOfGrassFields++;
            }
            Collections.shuffle(tiles);
        }

        for (var i = 0; i < mapHeight; i++) {
            for (var j = 0; j < mapWidth; j++) {
                map[i][j] = tiles.remove(0);
            }
        }

        return map;
    }

    /**
     * Called when opening the {@link ScenarioConfigScreen} to generate a new random scenario configuration.
     * Also setting the optional attributes (author name).
     *
     * @return a new scenario config with random but useful values
     */
    @Override
    public AbstractConfiguration generateRandomConfig() {
        var mapHeight = randomIntInRange(USEFUL_MIN_MAP_HEIGHT, USEFUL_MAX_MAP_HEIGHT);
        var mapWidth = randomIntInRange(USEFUL_MIN_MAP_WIDTH, USEFUL_MAX_MAP_WIDTH);
        var config = new ScenarioConfig();
        config.setScenario(generateRandomMap(mapHeight, mapWidth));
        config.setName("My Amazing Map");
        config.setAuthor("Mr. Random Generator");
        return config;
    }

    /**
     * Because the inputs on the screen are completely detached from any configuration object, this method needs to be called
     * whenever you need to save the values from the screen (aka when saving the config to a file).
     * The entered information in the text boxes is now read and summed up together with the map, parsed into a
     * {@link FieldState} 2D-Array, in a {@link ScenarioConfig} object.
     *
     * @return a scenario config with all the information from this {@link ScenarioConfigScreen}
     */
    @Override
    public AbstractConfiguration getConfigFromUI() {
        var scenarioConfig = new ScenarioConfig();
        String[] inputs = getOptions();
        if (scenarioScreen != null) scenarioConfig.setScenario(convertToFieldStateMap(scenarioScreen.getMap()));
        scenarioConfig.setName(inputs[0]);
        scenarioConfig.setAuthor(inputs[1]);
        return scenarioConfig;
    }

    /**
     * Whenever a new config needs to be set onto the screen (a random one, or a config loaded from a file), this
     * method needs to be called.
     * It takes all the values of the given config and sets the text inside the screen's text boxes plus the map texture according to them.
     *
     * @param abstractConfiguration the scenario config that needs to be displayed
     */
    @Override
    public void setConfigToUI(AbstractConfiguration abstractConfiguration) {
        var scenarioConfig = (ScenarioConfig) abstractConfiguration;
        var scenarioOptions = new String[ScenarioConfig.NR_OF_PARAMETERS];
        var index = 0;
        scenarioOptions[index++] = scenarioConfig.getName();
        scenarioOptions[index++] = scenarioConfig.getAuthor();
        scenarioOptions[index++] = parseNumberToOutput(scenarioConfig.getScenario().length, TEXT_LENGTH_IN_TEXT_FIELD_SMALL);
        scenarioOptions[index] = parseNumberToOutput(scenarioConfig.getScenario()[0].length, TEXT_LENGTH_IN_TEXT_FIELD_SMALL);
        setOptions(scenarioOptions);
        if (scenarioScreen != null) scenarioScreen.setMap(scenarioConfig.getScenario());
        AbstractConfigurationScreen.setChangesWereMade(false);
    }
}