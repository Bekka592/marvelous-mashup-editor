package marvelous_mashup.team29.launcher;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;

import java.io.File;

/**
 * Helper class used for different launchers in this projects.
 */
public class LauncherUtil {
    public static final String ICON_LOCATION = "icons" + File.separator;

    private LauncherUtil() {
    }

    /**
     * Calculates the application window's size according to the monitor's size.
     * Always keeping a ratio of 16:9.
     *
     * @return window size of the application as {@link Vector2}
     */
    public static Vector2 determineWindowSize() {
        float windowHeight = Lwjgl3ApplicationConfiguration.getDisplayMode().height;
        float windowWidth = windowHeight * 16 / 9;
        return new Vector2(windowWidth, windowHeight);
    }
}
