package marvelous_mashup.team29.ui_util.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import marvelous_mashup.team29.ui_util.UISettings;

import static marvelous_mashup.team29.ui_util.UIConstants.WINDOW_BORDER;

/**
 * Providing methods for rendering different shapes.
 * Those methods always have to be called within the render() function of the respective screen.
 */
public class UIShapes {
    private static final ShapeRenderer shapeRenderer = new ShapeRenderer();

    private UIShapes() {
    }

    /**
     * Creating an area and it's corresponding border by calling {@link UIShapes#renderArea(float, float, float, float, float)},
     * as well as {@link UIShapes#renderBorder(float, float, float, float)} with the given parameters.
     */
    public static void renderAreaWithBorder(float startX, float startY, float width, float height, float transparencyFilling) {
        renderArea(startX, startY, width, height, transparencyFilling);
        renderBorder(startX, startY, width, height);
    }

    /**
     * Creating a white and optionally transparent overlay over the elements that have been rendered so far.
     *
     * @param startX       position of the left edge of the area
     * @param startY       position of bottom edge of the area
     * @param width        of the area
     * @param height       of the area
     * @param transparency how transparent shall the white color be? reaches from 0 (completely transparent, no color)
     *                     to 1 (not transparent at all)
     */
    public static void renderArea(float startX, float startY, float width, float height,
                                  float transparency) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(255, 255, 255, transparency); // transparent white
        shapeRenderer.rect(startX, startY, width, height);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    /**
     * Creating a theme-colored frame at the given position.
     *
     * @param startX position of the left edge of the frame
     * @param startY position of bottom edge of the frame
     * @param width  of the frame
     * @param height of the frame
     */
    public static void renderBorder(float startX, float startY, float width, float height) {
        float lineWidth = WINDOW_BORDER / 4;
        Gdx.gl20.glLineWidth(lineWidth);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(UISettings.getStylingDefaults().getThemeColor());
        lineWidth = 2 * lineWidth / 5;
        shapeRenderer.line(startX - lineWidth, startY, startX + width + lineWidth, startY);
        shapeRenderer.line(startX, startY, startX, startY + height);
        shapeRenderer.line(startX + width, startY, startX + width, startY + height);
        shapeRenderer.line(startX - lineWidth, startY + height, startX + width + lineWidth, startY + height);
        shapeRenderer.end();
    }

    public static void disposeShapeRenderer() {
        shapeRenderer.dispose();
    }
}
