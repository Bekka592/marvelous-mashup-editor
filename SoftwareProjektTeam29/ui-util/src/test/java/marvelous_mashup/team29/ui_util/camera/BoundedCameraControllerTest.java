package marvelous_mashup.team29.ui_util.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class BoundedCameraControllerTest {

    static Stream<Arguments> zoomValueProvider() {
        return Stream.of(
                arguments(0.5f, 5, new float[]{2.5f}, 3.5f),
                arguments(0.5f, 5, new float[]{1.25f}, 2.25f),
                arguments(0.5f, 5, new float[]{0.8f, 1, 0.4f}, 3.2f),
                arguments(0.5f, 5, new float[]{0.2f, -10, 0.4f, 25.5f, -0.5f}, 4.5f),
                arguments(0.5f, 5, new float[]{6f}, 5f),
                arguments(0.5f, 5, new float[]{-0.5f}, 0.5f),
                arguments(0.5f, 7, new float[]{9.8f}, 7f),
                arguments(0.2f, 5, new float[]{-1.5f}, 0.2f)
        );
    }

    static Stream<Arguments> moveValueProvider() {
        return Stream.of(
                arguments(2, 2, 8, 8, new Vector2[]{Vector2.Zero}, new Vector2(2, 2)),
                arguments(2, 2, 8, 8, new Vector2[]{new Vector2(1, 3)}, new Vector2(2, 3)),
                arguments(2, 2, 8, 8, new Vector2[]{new Vector2(4, 9)}, new Vector2(4, 8)),
                arguments(2, 2, 8, 8, new Vector2[]{new Vector2(2, 3), new Vector2(3.5f, 1)}, new Vector2(5.5f, 4)),
                arguments(2, 2, 8, 8, new Vector2[]{new Vector2(4, 2), new Vector2(-5, 5.75f)}, new Vector2(2, 7.75f)),
                arguments(2, 2, 8, 8, new Vector2[]{new Vector2(-1, 4), new Vector2(0.25f, -0.3f)}, new Vector2(2.25f, 3.7f)),
                arguments(2, 2, 8, 8, new Vector2[]{new Vector2(1, 1), new Vector2(4.5f, 1.5f),
                        new Vector2(0.5f, 1.5f), new Vector2(1, 1.1f)}, new Vector2(8, 6.1f)),
                arguments(-0.5f, -2, 5, 7, new Vector2[]{new Vector2(-1, 4), new Vector2(2, 3.5f)}, new Vector2(1.5f, 7)),
                arguments(-8, -8, -2, -2, new Vector2[]{new Vector2(-6, 4), new Vector2(2, 0)}, new Vector2(-4, -2))
        );
    }

    BoundedCameraController setupBoundedCameraController(float leftLim, float bottomLim, float rightLim, float topLim,
                                                         float zoomMin, float zoomMax) {
        var orthographicCamera = new OrthographicCamera();
        return new BoundedCameraController(orthographicCamera,
                new Vector2(leftLim, bottomLim), new Vector2(rightLim, topLim), zoomMin, zoomMax);
    }

    @ParameterizedTest
    @MethodSource("zoomValueProvider")
    void zoom(float zoomMin, float zoomMax, float[] zoomValues, float expected) {
        BoundedCameraController cameraController = setupBoundedCameraController(2, 5, 4, 3, zoomMin, zoomMax);
        for (float zoomValue : zoomValues) {
            cameraController.zoom(zoomValue);
        }
        assertEquals(expected, cameraController.getCurrentZoom());
    }

    @ParameterizedTest
    @MethodSource("moveValueProvider")
    void zoom(float leftLim, float bottomLim, float rightLim, float topLim, Vector2[] moveValues, Vector2 expected) {
        BoundedCameraController cameraController = setupBoundedCameraController(leftLim, bottomLim, rightLim, topLim, 1.5f, 4);
        for (Vector2 moveValue : moveValues) {
            cameraController.move(moveValue.x, moveValue.y);
        }
        assertEquals(new Vector3(expected.x, expected.y, 0), cameraController.getCurrentPosition());
    }
}