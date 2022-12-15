package marvelous_mashup.team29.ui_util.pop_ups;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.utils.Align;
import marvelous_mashup.team29.ui_util.styling.ComponentCreator;

import static marvelous_mashup.team29.ui_util.UIConstants.*;
import static marvelous_mashup.team29.util.StringUtilities.formatText;

/**
 * Abstract class to use by {@link PopUpAccept} and {@link PopUpDecision}.
 */
public abstract class AbstractPopUp {
    protected float dialogWidth;
    protected float dialogHeight;
    protected Dialog dialog;

    /**
     * Instantiating a new {@link Dialog} as a PopUp and setting its parameters.
     *
     * @param text  to be displayed on the PopUp
     * @param stage that the PopUp is displayed over
     */
    protected AbstractPopUp(String text, Stage stage) {
        if (stage == null) { // just for unit testing, should never occur when running the application
            System.out.print(text);
            return;
        }

        dialogWidth = WINDOW_WIDTH / 2f;
        dialogHeight = WINDOW_HEIGHT / 2f;
        dialog = ComponentCreator.configureDialog();
        dialog.setTransform(true);

        addLabel(text);
        addButtons();

        dialog.setSize(dialogWidth, dialogHeight);
        dialog.setPosition(dialogWidth * 0.5f, dialogHeight * 0.5f);
        dialog.show(stage);
    }

    /**
     * Called by {@link AbstractPopUp#AbstractPopUp(String, Stage)} to add the text as a label to the middle of the PopUp.
     *
     * @param text to be displayed on the PopUp
     */
    private void addLabel(String text) {
        text = formatText(text, 60);
        var label = ComponentCreator.configureLabel(text);
        label.setAlignment(Align.center, Align.center);
        label.setPosition(dialogWidth / 2f - label.getWidth() / 2, 6f * WINDOW_BORDER + COMPONENT_HEIGHT);
        dialog.addActor(label);
    }

    protected abstract void addButtons();
}
