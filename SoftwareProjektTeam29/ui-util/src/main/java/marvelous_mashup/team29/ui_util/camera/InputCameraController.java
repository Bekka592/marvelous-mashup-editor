package marvelous_mashup.team29.ui_util.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

/**
 * This class contains drag and zoom functionality for a camera.
 * The movement can be limited to left, right, top and bottom coordinates.
 * <p>
 * Drag is only enabled with the right mouse button.
 * Zoom uses the scroll wheel.
 */
public class InputCameraController extends BoundedCameraController implements DefaultInputProcessor {
    private boolean moveEnabled;

    //Used to store the last updated position.
    //The movement of the Camera is the difference of this value and the new mouse Position.
    private Vector2 lastPosition;

    public InputCameraController(OrthographicCamera camera, float limitRightX, float limitTopY, float limitLeftX, float limitBottomY) {
        super(camera, new Vector2(limitLeftX, limitBottomY), new Vector2(limitRightX, limitTopY), 0.1f, 2);
    }

    /**
     * Sets the lastPosition to the current mouse position.
     * This function is needed because the touchDragged function does not supply what button is pressed.
     * The moveEnabled value is true if the clicked mouse button is the right mouse button.
     *
     * @return if the touchDown event is handled. true -> no other touchDown event is executed
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        lastPosition = new Vector2(screenX, screenY);
        moveEnabled = button == 1;
        return moveEnabled;
    }

    /**
     * Moves the camera with the mouse cursor.
     * The camera movement is normalized with the zoom value.
     * That way the mouse will stay over an element on the screen during a drag option.
     * The maximum and minimum camera movement is limited.
     *
     * @return if the touchDragged event is handled. true -> no other touchDragged event is executed
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (moveEnabled) {
            float moveX = getCamera().zoom * (lastPosition.x - screenX);
            float moveY = getCamera().zoom * (-(lastPosition.y - screenY));

            move(moveX, moveY);
            lastPosition = new Vector2(screenX, screenY);
        }
        return moveEnabled;
    }

    /**
     * The Scroll value is inverted to simulate the same camera behavior like in other games.
     * The maximum and minimum scroll value is limited.
     *
     * @return if the scrolled event is handled. true -> no other scrolled event is executed
     */
    @Override
    public boolean scrolled(float amountX, float amountY) {
        var zoomStepSize = .1f;
        zoom(amountY * zoomStepSize);
        return true;
    }
}