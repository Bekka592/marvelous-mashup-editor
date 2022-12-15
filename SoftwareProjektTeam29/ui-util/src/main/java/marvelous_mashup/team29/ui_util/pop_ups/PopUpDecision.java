package marvelous_mashup.team29.ui_util.pop_ups;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import marvelous_mashup.team29.ui_util.UIConstants;
import marvelous_mashup.team29.ui_util.options.ComponentsSizeEnum;
import marvelous_mashup.team29.ui_util.styling.ComponentCreator;

import static marvelous_mashup.team29.ui_util.UIConstants.WINDOW_BORDER;

/**
 * Providing a PopUp with a "Yes" and a "No" Button underneath the text.
 * When using this PopUp the Methods {@link PopUpDecision#confirm(Dialog)} and {@link PopUpDecision#deny(Dialog)}
 * shall be overwritten with the specific action to be performed when one of the Buttons is clicked.
 */
public class PopUpDecision extends AbstractPopUp {
    public PopUpDecision(String text, Stage stage) {
        super(text, stage);
    }

    /**
     * Adding two Buttons to the PopUp: "YES" and "NO"
     */
    @Override
    protected void addButtons() {
        var yes = ComponentCreator.configureButton("YES", ComponentsSizeEnum.COMPONENT_WIDTH_SMALL);
        yes.setPosition(dialogWidth / 2f - 1.5f * UIConstants.COMPONENT_WIDTH_SMALL, 2f * WINDOW_BORDER);
        yes.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                confirm(dialog);
            }
        });
        dialog.addActor(yes);

        var no = ComponentCreator.configureButton("NO", ComponentsSizeEnum.COMPONENT_WIDTH_SMALL);
        no.setPosition(dialogWidth / 2f + UIConstants.COMPONENT_WIDTH_SMALL / 2f, 2f * WINDOW_BORDER);
        no.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                deny(dialog);
            }
        });
        dialog.addActor(no);
    }

    /**
     * Method called when the "Yes"-Button is clicked.
     *
     * @param dialog the PopUp itself
     */
    public void confirm(Dialog dialog) {
        // method to be specified (overridden) in the specific use-case scenario
    }

    /**
     * Method called when the "No"-Button is clicked.
     *
     * @param dialog the PopUp itself
     */
    public void deny(Dialog dialog) {
        // method to be specified (overridden) in the specific use-case scenario
    }
}
