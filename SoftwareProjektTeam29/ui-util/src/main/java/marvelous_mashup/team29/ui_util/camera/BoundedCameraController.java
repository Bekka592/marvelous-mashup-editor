package marvelous_mashup.team29.ui_util.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Camera controller that limits the movement of the Camera
 */
public class BoundedCameraController implements IControlCamera {
    private final Vector2 limitBottomLeft;
    private final Vector2 limitTopRight;
    private final float zoomMinLimit;
    private final float zoomMaxLimit;

    private final OrthographicCamera camera;

    public BoundedCameraController(OrthographicCamera cam, Vector2 limBL, Vector2 limTR, float zoomMinLim, float zoomMaxLim) {
        limBL.add(2 * cam.position.x, 2 * cam.position.y);
        limitBottomLeft = limBL;
        limitTopRight = limTR;
        zoomMinLimit = zoomMinLim;
        zoomMaxLimit = zoomMaxLim;
        camera = cam;
    }

    @Override
    public void zoom(float zoomFactor) {
        camera.zoom = MathUtils.clamp(camera.zoom + zoomFactor, zoomMinLimit, zoomMaxLimit);
    }

    @Override
    public void move(float xAxisShift, float yAxisShift) {
        camera.position.x = MathUtils.clamp(camera.position.x + xAxisShift, limitBottomLeft.x, limitTopRight.x);
        camera.position.y = MathUtils.clamp(camera.position.y + yAxisShift, limitBottomLeft.y, limitTopRight.y);
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    @Override
    public float getCurrentZoom() {
        return camera.zoom;
    }

    @Override
    public Vector3 getCurrentPosition() {
        return camera.position;
    }
}