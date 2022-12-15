package marvelous_mashup.team29.editor.model;

import com.badlogic.gdx.scenes.scene2d.Stage;
import marvelous_mashup.team29.ui_util.pop_ups.PopUpAccept;
import marvelous_mashup.team29.util.data.FieldState;

import java.io.Serializable;

import static marvelous_mashup.team29.editor.model.ParameterSpecifications.*;

/**
 * Implements a JSON standardized 'scenario configuration' as a java class.
 */
public class ScenarioConfig extends AbstractConfiguration implements Serializable {
    public static final transient int NR_OF_PARAMETERS = 4;

    private FieldState[][] scenario;
    private String name = "";
    private String author = ""; // optional

    /**
     * This method checks whether the object is a valid configuration and can be loaded from or saved into a file.
     * Checks whether the attributes name and author are defined and within a useful range
     * using {@link AbstractConfiguration#verifyString(String, String)}.
     * Then calls {@link ScenarioConfig#verifyMap(Stage)} to verify the map.
     *
     * @param stage needed for displaying error pop-ups onto
     * @return is this configuration object valid? (true/false)
     */
    @Override
    public boolean verifyConfig(Stage stage) {
        try {
            verifyString(name, "name");
            verifyString(author, "author");
        } catch (IllegalArgumentException exception) {
            new PopUpAccept("Invalid scenario configuration:\n" + exception.getMessage() +
                    " in order to load or save the configuration.", stage);
            return false;
        }
        return verifyMap(stage);
    }

    /**
     * Checking whether the map (aka the 'scenario' attribute) is a valid map for Marvelous Mashup.
     * Checks whether the scenario is defined, has a valid length and height, is properly formatted with all rows of
     * the same size.
     * Then calls {@link ScenarioConfig#verifyMapFieldStates(Stage)} to carry out even more checks on the map.
     *
     * @param stage needed for displaying error pop-ups onto
     * @return is this object's map a valid one? (true/false)
     */
    private boolean verifyMap(Stage stage) {
        if (scenario == null) {
            new PopUpAccept("""
                    Invalid scenario configuration:
                    Attribute 'scenario' must be defined in order to load or save the configuration.""", stage);
            return false;
        }
        if (scenario.length < MIN_MAP_HEIGHT || scenario.length > MAX_MAP_HEIGHT) {
            new PopUpAccept("Invalid map: The map's height needs to be between " + MIN_MAP_HEIGHT + " and "
                    + MAX_MAP_HEIGHT + ".", stage);
            return false;
        }
        if (scenario[0].length < MIN_MAP_WIDTH || scenario[0].length > MAX_MAP_WIDTH) {
            new PopUpAccept("Invalid map: The map's width needs to be between " + MIN_MAP_WIDTH + " and "
                    + MAX_MAP_WIDTH + ".", stage);
            return false;
        }

        for (FieldState[] fieldStates : scenario) {
            if (fieldStates.length != scenario[0].length) {
                new PopUpAccept("Invalid map: The map needs to be in a properly formatted rectangular shape.", stage);
                return false;
            }
        }

        return verifyMapFieldStates(stage);
    }

    /**
     * Checks whether the map contains nothing but grass, portal and rock fields.
     * Then only verifies the map, if it contains at least {@link ParameterSpecifications#MIN_NR_OF_GRASS_FIELDS} grass fields
     * and {@link ParameterSpecifications#MIN_NR_OF_PORTAL_FIELDS} portal fields.
     *
     * @param stage needed for displaying error pop-ups onto
     * @return is the allocation of fields in this map valid? (true/false)
     */
    private boolean verifyMapFieldStates(Stage stage) {
        var grassFieldCount = 0;
        var portalFieldCount = 0;
        for (FieldState[] fieldRow : scenario) {
            for (FieldState field : fieldRow) {
                if (field == FieldState.GRASS) {
                    grassFieldCount++;
                } else if (field == FieldState.PORTAL) {
                    portalFieldCount++;
                } else if (field != FieldState.ROCK) {
                    new PopUpAccept("Invalid map: All of the map fields need to be declared either as grass, portals or as rocks.",
                            stage);
                    return false;
                }
            }
        }
        if (grassFieldCount < MIN_NR_OF_GRASS_FIELDS) {
            new PopUpAccept("Invalid map: The map needs to contain at least " + MIN_NR_OF_GRASS_FIELDS + " grass fields.", stage);
            return false;
        } else if (portalFieldCount < MIN_NR_OF_PORTAL_FIELDS) {
            new PopUpAccept("Invalid map: The map needs to contain at least " + MIN_NR_OF_PORTAL_FIELDS + " portal fields.", stage);
            return false;
        }
        return true;
    }

    public FieldState[][] getScenario() {
        return scenario;
    }

    public void setScenario(FieldState[][] scenario) {
        this.scenario = scenario;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
