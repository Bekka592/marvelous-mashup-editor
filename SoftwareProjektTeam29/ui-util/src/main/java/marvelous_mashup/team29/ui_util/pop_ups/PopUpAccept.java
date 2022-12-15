package marvelous_mashup.team29.ui_util.pop_ups;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import marvelous_mashup.team29.ui_util.UIConstants;
import marvelous_mashup.team29.ui_util.options.ComponentsSizeEnum;
import marvelous_mashup.team29.ui_util.styling.ComponentCreator;

import static marvelous_mashup.team29.ui_util.UIConstants.WINDOW_BORDER;

/**
 * Providing a PopUp with just one "OK"-Button underneath the text.
 */
public class PopUpAccept extends AbstractPopUp {
    public PopUpAccept(String text, Stage stage) {
        super(text, stage);
    }

    /**
     * Adding the "OK"-Button to the PopUp.
     */
    @Override
    protected void addButtons() {
        var ok = ComponentCreator.configureButton("OK", ComponentsSizeEnum.COMPONENT_WIDTH_SMALL);
        ok.setPosition(dialogWidth / 2f - UIConstants.COMPONENT_WIDTH_SMALL / 2f, 2f * WINDOW_BORDER);
        ok.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                closePopUp();
            }
        });
        dialog.addActor(ok);
    }

    /**
     * Method called when the "OK"-Button is clicked.
     */
    private void closePopUp() {
        dialog.remove();
    }
}
