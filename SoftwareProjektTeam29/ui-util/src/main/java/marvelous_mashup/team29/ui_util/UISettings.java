package marvelous_mashup.team29.ui_util;

import com.badlogic.gdx.math.Vector2;
import marvelous_mashup.team29.ui_util.styling.IContainDefaults;

/**
 * Settings for the UI
 */
public class UISettings {
    public static final Vector2 tileDimensions = new Vector2(100f, 100f);
    private static IContainDefaults stylingDefaults;
    private static float mapFieldTileSize;


    private UISettings() {
    }

    public static void init(IContainDefaults defaults) {
        UISettings.stylingDefaults = defaults;
    }

    public static Vector2 getGameBoardDimension() {
        return tileDimensions;
    }

    public static void setTileDimensions(Vector2 tileDimensions) {
        UISettings.tileDimensions.set(tileDimensions);
    }

    public static IContainDefaults getStylingDefaults() {
        return stylingDefaults;
    }

    public static float getMapFieldTileSize() {
        return mapFieldTileSize;
    }

    public static void setMapFieldTileSize(float mapFieldTileSize) {
        UISettings.mapFieldTileSize = mapFieldTileSize;
    }
}
