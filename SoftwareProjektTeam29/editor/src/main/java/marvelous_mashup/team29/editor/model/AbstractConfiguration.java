package marvelous_mashup.team29.editor.model;

import com.badlogic.gdx.scenes.scene2d.Stage;

import java.io.Serializable;

import static marvelous_mashup.team29.ui_util.UIConstants.TEXT_LENGTH_IN_TEXT_FIELD_BIG;

/**
 * Abstract class to inherit in {@link CharacterConfig}, {@link CharacterConfigContainer} {@link MatchConfig} and {@link ScenarioConfig}.
 * Providing a common interface and some standard helper functions for those classes.
 */
public abstract class AbstractConfiguration implements Serializable {
    private static final transient String PREFIX = "Attribute '";

    public abstract boolean verifyConfig(Stage stage);

    /**
     * Method to check whether the given integer is defined and in the specified range.
     * Only the optional integers (here: max game time) don't have to be defined, yet if the are, they still have
     * to be inside their specified range.
     *
     * @param attribute     value to check
     * @param attributeName name of this value to return in error messages
     * @param minNumber     minimum value the attribute can have
     * @param maxNumber     maximum value the attribute can have
     * @throws IllegalArgumentException thrown if the number is invalid
     */
    protected void verifyNumber(int attribute, String attributeName, int minNumber, int maxNumber) throws IllegalArgumentException {
        if (attributeName.equals("max game time") && attribute == Integer.MIN_VALUE)
            return; // max game time is optional
        if (attribute == Integer.MIN_VALUE) {
            throw new IllegalArgumentException(PREFIX + attributeName + "' must be defined");
        } else if (attribute < minNumber) {
            throw new IllegalArgumentException(PREFIX + attributeName + "' must be >= " + minNumber);
        } else if (attribute > maxNumber) {
            throw new IllegalArgumentException(PREFIX + attributeName + "' must be <= " + maxNumber);
        }
    }

    /**
     * Method to check whether the given string is defined and only contains up to n characters.
     * Only the optional strings (here: author) don't have to be defined, yet if the are, they still have
     * to fulfill the other conditions.
     *
     * @param attribute     string to check
     * @param attributeName name of this attribute to return in error messages
     * @throws IllegalArgumentException thrown if the string is invalid
     */
    protected void verifyString(String attribute, String attributeName) throws IllegalArgumentException {
        if (attributeName.equals("author") && (attribute == null || attribute.equals(""))) return; // author is optional
        if (attribute == null || attribute.equals("")) {
            throw new IllegalArgumentException(PREFIX + attributeName + "' must be defined");
        } else if (attribute.length() > TEXT_LENGTH_IN_TEXT_FIELD_BIG) {
            throw new IllegalArgumentException(PREFIX + attributeName + "' can only be up to " + TEXT_LENGTH_IN_TEXT_FIELD_BIG + " letters long");
        }
    }
}