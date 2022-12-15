package marvelous_mashup.team29.editor.logic;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import marvelous_mashup.team29.editor.gui.config_screens.AbstractConfigurationScreen;
import marvelous_mashup.team29.editor.gui.config_screens.MatchConfigScreen;
import marvelous_mashup.team29.editor.model.AbstractConfiguration;
import marvelous_mashup.team29.editor.model.MatchConfig;

import static marvelous_mashup.team29.editor.model.ParameterSpecifications.*;
import static marvelous_mashup.team29.ui_util.UIConstants.TEXT_LENGTH_IN_TEXT_FIELD_MEDIUM;
import static marvelous_mashup.team29.util.MathUtilities.randomIntInRange;

/**
 * Implements the logic of the {@link MatchConfigScreen} to give the user the option to interact with the screen.
 */
public class MatchConfigLogic extends AbstractConfigurationLogic {

    public MatchConfigLogic(Stage stage, TextField[] matchOptions) {
        super(stage, matchOptions);
    }

    /**
     * Called when opening the {@link MatchConfigScreen} to generate a new random match configuration.
     * Also setting the optional attributes (max game time).
     *
     * @return a new match config with random but useful values
     */
    @Override
    public AbstractConfiguration generateRandomConfig() {
        var config = new MatchConfig();
        config.setMaxRounds(randomIntInRange(USEFUL_MIN_VALUE_MAX_ROUNDS, USEFUL_MAX_VALUE_MAX_ROUNDS));
        config.setMaxGameTime(randomIntInRange(USEFUL_MIN_VALUE_MAX_GAME_TIME, USEFUL_MAX_VALUE_MAX_GAME_TIME));
        config.setMaxRoundTime(randomIntInRange(USEFUL_MIN_VALUE_MAX_ROUND_TIME, USEFUL_MAX_VALUE_MAX_ROUND_TIME));
        config.setMaxResponseTime(randomIntInRange(USEFUL_MIN_VALUE_MAX_RESPONSE_TIME, USEFUL_MAX_VALUE_MAX_RESPONSE_TIME));
        config.setMaxAnimationTime(randomIntInRange(USEFUL_MIN_VALUE_MAX_ANIMATION_TIME, USEFUL_MAX_VALUE_MAX_ANIMATION_TIME));
        config.setMaxPauseTime(randomIntInRange(USEFUL_MIN_VALUE_MAX_PAUSE_TIME, USEFUL_MAX_VALUE_MAX_PAUSE_TIME));
        config.setSpaceStoneCD(randomIntInRange(USEFUL_MIN_VALUE_SPACE_STONE_CD, USEFUL_MAX_VALUE_SPACE_STONE_CD));
        config.setRealityStoneCD(randomIntInRange(USEFUL_MIN_VALUE_REALITY_STONE_CD, USEFUL_MAX_VALUE_REALITY_STONE_CD));
        config.setPowerStoneCD(randomIntInRange(USEFUL_MIN_VALUE_POWER_STONE_CD, USEFUL_MAX_VALUE_POWER_STONE_CD));
        config.setTimeStoneCD(randomIntInRange(USEFUL_MIN_VALUE_TIME_STONE_CD, USEFUL_MAX_VALUE_TIME_STONE_CD));
        config.setSoulStoneCD(randomIntInRange(USEFUL_MIN_VALUE_SOUL_STONE_CD, USEFUL_MAX_VALUE_SOUL_STONE_CD));
        config.setMindStoneCD(randomIntInRange(USEFUL_MIN_VALUE_MIND_STONE_CD, USEFUL_MAX_VALUE_MIND_STONE_CD));
        config.setMindStoneDMG(randomIntInRange(USEFUL_MIN_VALUE_MIND_STONE_DMG, USEFUL_MAX_VALUE_MIND_STONE_DMG));
        return config;
    }

    /**
     * Because the inputs on the screen are completely detached from any configuration object, this method needs to be called
     * whenever you need to save the values from the screen (aka when saving the config to a file).
     * The entered information in the text boxes is now read and summed up into a {@link MatchConfig} object.
     *
     * @return a match config with all the information from this {@link MatchConfigScreen}
     */
    @Override
    public AbstractConfiguration getConfigFromUI() {
        var config = new MatchConfig();
        String[] matchOptions = getOptions();
        var index = 0;
        config.setMaxRounds(parseInputToNumber(matchOptions[index++]));
        config.setMaxGameTime(parseInputToNumber(matchOptions[index++]));
        config.setMaxRoundTime(parseInputToNumber(matchOptions[index++]));
        config.setMaxResponseTime(parseInputToNumber(matchOptions[index++]));
        config.setMaxAnimationTime(parseInputToNumber(matchOptions[index++]));
        config.setMaxPauseTime(parseInputToNumber(matchOptions[index++]));
        config.setSpaceStoneCD(parseInputToNumber(matchOptions[index++]));
        config.setRealityStoneCD(parseInputToNumber(matchOptions[index++]));
        config.setPowerStoneCD(parseInputToNumber(matchOptions[index++]));
        config.setTimeStoneCD(parseInputToNumber(matchOptions[index++]));
        config.setSoulStoneCD(parseInputToNumber(matchOptions[index++]));
        config.setMindStoneCD(parseInputToNumber(matchOptions[index++]));
        config.setMindStoneDMG(parseInputToNumber(matchOptions[index]));
        return config;
    }

    /**
     * Whenever a new config needs to be set onto the screen (a random one, or a config loaded from a file), this
     * method needs to be called.
     * It takes all the values of the given config and sets the text inside the screen's text boxes according to them.
     *
     * @param abstractConfiguration the match config that needs to be displayed
     */
    @Override
    public void setConfigToUI(AbstractConfiguration abstractConfiguration) {
        MatchConfig config = (MatchConfig) abstractConfiguration;
        var matchOptions = new String[MatchConfig.NR_OF_PARAMETERS];
        var index = 0;
        matchOptions[index++] = parseNumberToOutput(config.getMaxRounds(), TEXT_LENGTH_IN_TEXT_FIELD_MEDIUM);
        matchOptions[index++] = parseNumberToOutput(config.getMaxGameTime(), TEXT_LENGTH_IN_TEXT_FIELD_MEDIUM);
        matchOptions[index++] = parseNumberToOutput(config.getMaxRoundTime(), TEXT_LENGTH_IN_TEXT_FIELD_MEDIUM);
        matchOptions[index++] = parseNumberToOutput(config.getMaxResponseTime(), TEXT_LENGTH_IN_TEXT_FIELD_MEDIUM);
        matchOptions[index++] = parseNumberToOutput(config.getMaxAnimationTime(), TEXT_LENGTH_IN_TEXT_FIELD_MEDIUM);
        matchOptions[index++] = parseNumberToOutput(config.getMaxPauseTime(), TEXT_LENGTH_IN_TEXT_FIELD_MEDIUM);
        matchOptions[index++] = parseNumberToOutput(config.getSpaceStoneCD(), TEXT_LENGTH_IN_TEXT_FIELD_MEDIUM);
        matchOptions[index++] = parseNumberToOutput(config.getRealityStoneCD(), TEXT_LENGTH_IN_TEXT_FIELD_MEDIUM);
        matchOptions[index++] = parseNumberToOutput(config.getPowerStoneCD(), TEXT_LENGTH_IN_TEXT_FIELD_MEDIUM);
        matchOptions[index++] = parseNumberToOutput(config.getTimeStoneCD(), TEXT_LENGTH_IN_TEXT_FIELD_MEDIUM);
        matchOptions[index++] = parseNumberToOutput(config.getSoulStoneCD(), TEXT_LENGTH_IN_TEXT_FIELD_MEDIUM);
        matchOptions[index++] = parseNumberToOutput(config.getMindStoneCD(), TEXT_LENGTH_IN_TEXT_FIELD_MEDIUM);
        matchOptions[index] = parseNumberToOutput(config.getMindStoneDMG(), TEXT_LENGTH_IN_TEXT_FIELD_MEDIUM);
        setOptions(matchOptions);
        AbstractConfigurationScreen.setChangesWereMade(false);
    }
}