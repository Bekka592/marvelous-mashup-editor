package marvelous_mashup.team29.editor.logic.parsing;

import com.badlogic.gdx.scenes.scene2d.Stage;
import marvelous_mashup.team29.editor.gui.config_screens.AbstractConfigurationScreen;
import marvelous_mashup.team29.editor.model.AbstractConfiguration;
import marvelous_mashup.team29.editor.model.ConfigurationTypeEnum;
import marvelous_mashup.team29.ui_util.pop_ups.PopUpAccept;
import marvelous_mashup.team29.util.parsing.Validator;

import java.io.File;

import static marvelous_mashup.team29.util.FileIOUtilities.loadTextFromFile;
import static marvelous_mashup.team29.util.FileIOUtilities.writeTextToFile;

/**
 * Class that handles what is going to happen after a file got chosen in a file chooser.
 * Contains functionality for loading a config, saving a config to a file and validating a config, mostly
 * by using methods of {@link ConfigurationParsing}.
 */
public class FileIOLogic {
    private final Stage stage;
    private final ConfigurationTypeEnum configType;
    private final ConfigurationParsing parser;

    public FileIOLogic(ConfigurationTypeEnum configType, Stage stage) {
        this.stage = stage;
        this.configType = configType;
        parser = new ConfigurationParsing(configType.getCorrespondingClassName());
    }

    /**
     * Called after a file to load a configuration from has been chosen.
     * Reading the JSON-String from the file ans parsing it into an {@link AbstractConfiguration}.
     *
     * @param file to read and load
     * @return loaded configuration as an {@link AbstractConfiguration} or null if the loading process was unsuccessful
     */
    public AbstractConfiguration loadConfigFromFile(File file) {
        if (!checkFileNameValid(file)) return null;

        var jsonString = loadTextFromFile(file);
        if (jsonString == null) {
            new PopUpAccept("""
                    An error occurred when trying to load the file.
                    Please try again.""", stage);
            return null;
        }

        AbstractConfiguration parsedConfig = parser.deserializeConfig(jsonString);
        if (parsedConfig == null) {
            new PopUpAccept("""
                    Invalid JSON file: Parsing failed.
                    Please choose a valid configuration file.""", stage);
            return null;
        } else {
            return parsedConfig;
        }
    }

    /**
     * Called after a file, to save the current configuration to, has been chosen.
     * Parses the given current configuration using {@link ConfigurationParsing#serializeConfig(AbstractConfiguration)}
     * and saves the parsed JSON-String into the specified file.
     *
     * @param file   to save the config to
     * @param config to save
     */
    public void saveConfigToFile(File file, AbstractConfiguration config) {
        if (!checkFileNameValid(file)) return;
        var jsonString = parser.serializeConfig(config);
        if (!writeTextToFile(jsonString, file)) {
            new PopUpAccept("""
                    An error occurred when trying to save the config to a file:
                    Please try again.""", stage);
        }
        AbstractConfigurationScreen.setChangesWereMade(false);
    }

    /**
     * Checks whether the given config is really valid according to {@link AbstractConfiguration#verifyConfig(Stage)}.
     * Additionally also parses the configuration into a JSON-String and validates that against the
     * official Marvelous Mashup Schema defined in the network standard document.
     * The validation against the schema actually should actually not find any additional errors,
     * but it's saver to double check the configuration that way.
     *
     * @param config to validate
     * @return does this given config comply with the network standard? (true/false)
     */
    public boolean validateConfig(AbstractConfiguration config) {
        if (config == null) return false;
        if (!config.verifyConfig(stage)) return false;

        var jsonString = parser.serializeConfig(config);
        if (jsonString == null) return false;
        if (!Validator.isValid(jsonString)) {
            new PopUpAccept("""
                    The JSON String failed to validate against the Marvelous Mashup Schema.
                    Please try again.""", stage);
            return false;
        }
        return true;
    }

    /**
     * Checks whether the selected file has the right file-extension according to the {@link ConfigurationTypeEnum}
     * of this FileIOLogic object.
     *
     * @param file that needs to be checked
     * @return is the name of the given file valid for the desired use? (true/false)
     */
    private boolean checkFileNameValid(File file) {
        if (!file.getName().toLowerCase().endsWith(configType.getFileExtension())) {
            new PopUpAccept("""
                    Unsupported file extension.
                    Please choose a different file and try again.""", stage);
            return false;
        } else {
            return true;
        }
    }
}