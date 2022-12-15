package marvelous_mashup.team29.ui_util.styling;

import com.badlogic.gdx.graphics.Color;
import marvelous_mashup.team29.ui_util.options.TextSizeEnum;
import marvelous_mashup.team29.ui_util.options.TextStyleEnum;

/**
 * All default values for e.g. editor and client.
 * You need to create a new class which implements IContainDefaults.
 * There you can set all default values.
 */
public interface IContainDefaults {
    Color getBaseColor();

    Color getHighlightColor();

    Color getLogoColor();

    Color getOverColor();

    Color getClickedColor();

    Color getPrimaryFontColor();

    Color getSecondaryFontColor();

    Color getThemeColor();

    Color[] getColorScheme();

    TextStyleEnum getFontStyle();

    TextSizeEnum getTextSize();
}
