package marvelous_mashup.team29.launcher;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import marvelous_mashup.team29.editor.Editor;

import static marvelous_mashup.team29.launcher.LauncherUtil.ICON_LOCATION;
import static marvelous_mashup.team29.launcher.LauncherUtil.determineWindowSize;

/**
 * Launches the desktop application of the editor.
 */
public class EditorLauncher {

    public static void main(String[] args) {
        createApplication();
    }

    /**
     * Creating a new Editor application with the given configuration.
     */
    private static void createApplication() {
        new Lwjgl3Application(new Editor(), getDefaultConfiguration());
    }

    /**
     * Configuring the applications settings.
     * Setting an icon and fitting window sizes using {@link LauncherUtil#determineWindowSize()}.
     *
     * @return lwjgl3 configuration to be used for the editor
     */
    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        var configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setWindowIcon(ICON_LOCATION + "green_128.png", ICON_LOCATION + "green_64.png",
                ICON_LOCATION + "green_32.png");

        Vector2 windowDim = determineWindowSize();
        configuration.setWindowedMode((int) (windowDim.x * 0.7f), (int) (windowDim.y * 0.7f));
        configuration.setResizable(false);

        return configuration;
    }


}