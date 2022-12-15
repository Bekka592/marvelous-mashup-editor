package marvelous_mashup.team29.editor.logic.parsing;

import com.google.gson.*;
import marvelous_mashup.team29.editor.model.AbstractConfiguration;
import marvelous_mashup.team29.editor.model.CharacterConfigContainer;
import marvelous_mashup.team29.editor.model.MatchConfig;
import marvelous_mashup.team29.editor.model.ScenarioConfig;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

/**
 * Class that handles the serialization and the deserialization of {@link AbstractConfiguration} objects into JSON Strings.
 */
public class ConfigurationParsing {
    private final Type classType;
    private Gson gson;

    protected ConfigurationParsing(String className) {
        resetGsonBuilder();
        if (className.equals("CharacterConfigContainer")) {
            classType = CharacterConfigContainer.class;
        } else if (className.equals("MatchConfig")) {
            classType = MatchConfig.class;
        } else {
            classType = ScenarioConfig.class;
        }
    }

    /**
     * Method called whenever a JSON string needs to be parsed into the suitable {@link AbstractConfiguration} object.
     *
     * @param jsonString to be parsed
     * @return parsed (serialized) configuration object or null if the parsing was unsuccessful
     */
    protected AbstractConfiguration deserializeConfig(String jsonString) {
        AbstractConfiguration parsedConfig;

        try {
            parsedConfig = gson.fromJson(jsonString, classType);
        } catch (JsonParseException e) {
            return null;
        }
        return parsedConfig;
    }

    /**
     * Method called whenever a configuration object needs to be parsed into a JSON string.
     * If there is an optional attribute, that has not been set in this configuration object, that attribute
     * is not included into the JSON string.
     *
     * @param config that needs to be parsed
     * @return serialized configuration as a JSON String
     */
    protected String serializeConfig(AbstractConfiguration config) {
        var unwantedAttribute = "";
        if (config instanceof MatchConfig && ((MatchConfig) config).getMaxGameTime() == Integer.MIN_VALUE) {
            unwantedAttribute = "maxGameTime";
        } else if (config instanceof ScenarioConfig && ((ScenarioConfig) config).getAuthor().equals("")) {
            unwantedAttribute = "author";
        }

        if (!unwantedAttribute.equals("")) modifyGsonBuilder(unwantedAttribute);
        var jsonString = gson.toJson(config);
        resetGsonBuilder();
        return jsonString;
    }

    /**
     * Modifying the used gson builder to now not serialize/deserialize the given attribute.
     *
     * @param nameOfUnwantedAttribute JSON-name of the attribute to ignore
     */
    protected void modifyGsonBuilder(String nameOfUnwantedAttribute) {
        gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithModifiers(Modifier.TRANSIENT).serializeNulls()
                .addSerializationExclusionStrategy(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getName().contains(nameOfUnwantedAttribute);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> aClass) {
                        return false;
                    }
                }).create();
    }

    /**
     * Modifying the used gson builder to now again serialize/deserialize all the default attributes.
     * Removing the gson exclusion strategy.
     */
    protected void resetGsonBuilder() {
        gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithModifiers(Modifier.TRANSIENT).serializeNulls().create();
    }
}