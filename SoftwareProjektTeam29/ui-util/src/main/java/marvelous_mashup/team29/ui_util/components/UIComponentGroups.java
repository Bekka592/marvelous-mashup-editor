package marvelous_mashup.team29.ui_util.components;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import marvelous_mashup.team29.ui_util.UIConstants;
import marvelous_mashup.team29.ui_util.screens.AbstractScreen;

import java.util.List;

/**
 * Providing methods for configuring different layout groups of LibGdx Actors, because the libGdx grouping functions
 * sadly do not always work in our project as intended.
 */
public class UIComponentGroups {
    private UIComponentGroups() {
    }

    /**
     * Creating a vertical group of actors horizontally centered in the middle of the screen.
     *
     * @param stage           that the vertical group is displayed on
     * @param startingHeightY upmost point of the vertical group
     * @param margin          between two actors
     * @param actors          to add to the group
     */
    public static void configureVerticalGroup(Stage stage, float startingHeightY, float xCenter, float margin, List<Actor> actors) {
        var actorCount = 0;
        for (Actor actor : actors) {
            actor.setPosition(xCenter - actor.getWidth() / 2f,
                    startingHeightY - actor.getHeight() * (actorCount + 1) - margin * (actorCount));
            actorCount++;
            stage.addActor(actor);
        }
    }

    /**
     * Creating a vertical group of actors horizontally centered in the middle of the screen, with a default
     * margin of 2 * WINDOW_BORDER and a default starting height somewhere in the upper half of the screen.
     *
     * @param screen abstract screen that the vertical group is displayed on
     * @param actors to add to the group
     */
    public static void configureVerticalGroup(AbstractScreen screen, List<Actor> actors) {
        configureVerticalGroup(screen.getStage(), screen.mainAreaStartY + screen.mainAreaHeight - 8 * UIConstants.WINDOW_BORDER,
                UIConstants.WINDOW_CENTER_X, 2 * UIConstants.WINDOW_BORDER, actors);
    }

    /**
     * Creating an horizontal group of actors, that will be positioned at the given yPos and centered horizontally.
     *
     * @param stage   that the horizontal group is displayed on
     * @param yPos    height where the group will be displayed at
     * @param margins to add between the actors as a list
     * @param actors  to add to the group
     */
    public static void configureHorizontalGroup(Stage stage, float yPos, List<Float> margins, List<Actor> actors) {
        float totalWidth = 0;
        for (Actor actor : actors) { // calculation to center the elements horizontally
            totalWidth += actor.getWidth();
        }
        for (Float margin : margins) {
            totalWidth += margin;
        }

        float xPos = UIConstants.WINDOW_CENTER_X - totalWidth / 2f;
        for (var i = 0; i < actors.size() - 1; i++) {
            actors.get(i).setPosition(xPos, yPos);
            xPos += margins.get(i) + actors.get(i).getWidth();
            stage.addActor(actors.get(i));
        }
        actors.get(actors.size() - 1).setPosition(xPos, yPos);
        stage.addActor(actors.get(actors.size() - 1));
    }
}
