package marvelous_mashup.team29.ui_util.camera;

import com.badlogic.gdx.math.Vector3;

public interface IControlCamera {
    void zoom(float zoomFactor);

    void move(float xAxisShift, float yAxisShift);

    float getCurrentZoom();

    Vector3 getCurrentPosition();
}