package marvelous_mashup.team29.ui_util.styling;

import com.badlogic.gdx.graphics.Color;
import marvelous_mashup.team29.ui_util.options.ColorEnum;
import marvelous_mashup.team29.ui_util.options.TextSizeEnum;
import marvelous_mashup.team29.ui_util.options.TextStyleEnum;

/**
 * All default values for the editor.
 * If you want to change a default color (or textstyle/ textsize), please change it here!
 */
public class EditorDefaults implements IContainDefaults {
    @Override
    public Color getBaseColor() {
        return ColorEnum.DARK_THEME_COLOR.getColor();
    }

    @Override
    public Color getHighlightColor() {
        return Color.WHITE;
    }

    @Override
    public Color getLogoColor() {
        return ColorEnum.DARK_GREEN.getColor();
    }

    @Override
    public Color getOverColor() {
        return ColorEnum.THEME_COLOR.getColor();
    }

    @Override
    public Color getClickedColor() {
        return ColorEnum.BRIGHT_THEME_COLOR.getColor();
    }

    @Override
    public Color getPrimaryFontColor() {
        return Color.WHITE;
    }

    @Override
    public Color getSecondaryFontColor() {
        return ColorEnum.DARK_THEME_COLOR.getColor();
    }

    @Override
    public Color getThemeColor(){return ColorEnum.THEME_COLOR.getColor();}

    @Override
    public Color[] getColorScheme(){
        return new Color[]{getBaseColor(), getHighlightColor(), getOverColor(), getClickedColor()};
    }

    @Override
    public TextStyleEnum getFontStyle() {
        return TextStyleEnum.COMIC;
    }

    @Override
    public TextSizeEnum getTextSize() {
        return TextSizeEnum.MEDIUM;
    }
}
