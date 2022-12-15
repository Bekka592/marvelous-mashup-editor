package marvelous_mashup.team29.ui_util.options;

/**
 * Enum for all different TextSizes
 * the TextSize can be directly changed by changing the integer value to e.g. MEDIUM(35).
 * A new TextSize can be added in the MarvelStyle class, explicit:
 * This can easily be done by adding the font to the generateFonts() and getFont() method.
 * Watch out: to do this, you have to create (multiple) new BitmapFont(s) for each different style you would like to use.
 */
public enum TextSizeEnum {
    SMALL(25), MEDIUM(30), BIG(50);

    private final int fontSize;

    TextSizeEnum(int size) {
        fontSize = size;
    }

    public int getFontSize() {
        return fontSize;
    }
}
