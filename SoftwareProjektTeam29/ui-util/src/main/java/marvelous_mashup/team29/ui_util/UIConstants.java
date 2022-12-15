package marvelous_mashup.team29.ui_util;

import com.badlogic.gdx.Gdx;
import marvelous_mashup.team29.ui_util.options.ComponentsSizeEnum;
import marvelous_mashup.team29.util.asset_loader.AssetFinder;

import java.awt.*;

/**
 * all different UIConstants like Window width, height, size of components...
 */
public class UIConstants {
    // WINDOW
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    public static final float SCREEN_HEIGHT = (float) SCREEN_SIZE.getHeight();
    public static final float HEIGHT_FULL_HD_MONITOR = 1080;
    public static final float SCALING_FACTOR = SCREEN_HEIGHT / HEIGHT_FULL_HD_MONITOR; // all measurements are relative to the screen size for good scaling

    public static final int WINDOW_WIDTH = Gdx.graphics.getWidth();
    public static final int WINDOW_HEIGHT = Gdx.graphics.getHeight();
    public static final int WINDOW_CENTER_X = WINDOW_WIDTH / 2;
    public static final int WINDOW_CENTER_Y = WINDOW_HEIGHT / 2;

    // SIZES FOR DESIGN
    public static final float WINDOW_BORDER = getScaling(ComponentsSizeEnum.BORDER_BIG);
    public static final int BORDER_WIDTH = getScaling(ComponentsSizeEnum.BORDER);

    public static final int COMPONENT_HEIGHT = getScaling(ComponentsSizeEnum.COMPONENT_HEIGHT);
    public static final int COMPONENT_WIDTH_SMALL = getScaling(ComponentsSizeEnum.COMPONENT_WIDTH_SMALL);

    public static final int TEXT_FIELD_HEIGHT_SMALL = getScaling(ComponentsSizeEnum.TEXT_FIELD_HEIGHT_SMALL);
    public static final int TEXT_FIELD_HEIGHT_MEDIUM = getScaling(ComponentsSizeEnum.TEXT_FIELD_HEIGHT_MEDIUM);
    public static final int TEXT_FIELD_WIDTH_SMALL = getScaling(ComponentsSizeEnum.TEXT_FIELD_WIDTH_SMALL);
    public static final int TEXT_FIELD_WIDTH_MEDIUM = getScaling(ComponentsSizeEnum.TEXT_FIELD_WIDTH_MEDIUM);
    public static final int TEXT_FIELD_WIDTH_BIG = getScaling(ComponentsSizeEnum.TEXT_FIELD_WIDTH_BIG);

    // allowed text lengths for {@link ComponentCreator#configureSmallNumberTextField(boolean)} etc.
    public static final int TEXT_LENGTH_IN_TEXT_FIELD_SMALL = 4;
    public static final int TEXT_LENGTH_IN_TEXT_FIELD_MEDIUM = 5;
    public static final int TEXT_LENGTH_IN_TEXT_FIELD_BIG = 25;

    // GENRALLY USEFUL CONSTANTS
    public static final AssetFinder ASSET_FINDER = AssetFinder.getAssetFinder();

    private UIConstants() {
    }

    private static int getScaling(ComponentsSizeEnum size) {
        return (int) (size.getValue() * SCALING_FACTOR);
    }
}
