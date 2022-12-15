package marvelous_mashup.team29.ui_util.options;

import com.badlogic.gdx.graphics.Color;

/**
 * ColorEnum with all used colors.
 * Our Theme has three different colors, a dark, a bright and a normal color.
 * These are used in the MarvelStyle. If you like to change these, you can directly change it here.
 * If you like to add a new color, use the constructor and add it here to this class.
 * Watch out: The Editor Set of TEAM 29 includes the possibility to have different (theme) colors.
 * So instead of using two different colors with the name THEME_COLOR_EDITOR and THEME_COLOR_CLIENT,
 * please use the IContainDefaults and create a new class. The different colors can be called with their real color,
 * like blue and royal.
 */
public enum ColorEnum {
    // INFINITY STONE COLORS
    RED(new Color(0xec1c23ff)),
    BLUE(new Color(0x1c8becff)),
    GREEN(new Color(0x91ca2fff)),
    PURPLE(new Color(0x7c1cecff)),
    YELLOW(new Color(0xffd300ff)),
    ORANGE(new Color((0xff8b00ff))),

    // THEME COLORS
    BRIGHT_THEME_COLOR(new Color(0xd5d5d5ff)),
    THEME_COLOR(new Color(0x666666ff)),
    DARK_THEME_COLOR(new Color(0x333345ff)),

    // OTHER COLORS
    DARK_GREEN(new Color(0x2b8987ff)); // alternative: 0x2c6354ff

    private final Color color;

    ColorEnum(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
