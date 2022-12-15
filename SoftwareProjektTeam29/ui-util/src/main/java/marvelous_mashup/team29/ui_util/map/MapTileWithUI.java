package marvelous_mashup.team29.ui_util.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import marvelous_mashup.team29.ui_util.UIConstants;
import marvelous_mashup.team29.util.data.FieldState;
import marvelous_mashup.team29.util.infinity_stones.IAmAnInfinityStone;
import marvelous_mashup.team29.util.map.IAmAMapTile;

public class MapTileWithUI extends Actor implements IAmAMapTile {
    private FieldState currentState;
    private IAmAnInfinityStone infinityStone;
    private int healthPoints;

    private Sprite sprite;
    private int xPosition;
    private int yPosition;
    private long timeLastPressed;

    public MapTileWithUI(FieldState state) {
        this.currentState = state;
        timeLastPressed = 0;
    }

    @Override
    public FieldState getFieldState() {
        return this.currentState;
    }

    @Override
    public void setFieldState(FieldState state) {
        if (state != FieldState.ROCK) this.healthPoints = 0;
        if (state != FieldState.INFINITY_STONE) this.infinityStone = null;
        this.currentState = state;
    }

    @Override
    public IAmAnInfinityStone getInfinityStone() {
        return this.infinityStone;
    }

    @Override
    public void setInfinityStone(IAmAnInfinityStone stone) {
        if (currentState != FieldState.INFINITY_STONE) throw new IllegalStateException();
        this.infinityStone = stone;
    }

    @Override
    public int getHealthPoints() {
        return this.healthPoints;
    }

    @Override
    public void setHealthPoints(int healthPoints) {
        if (currentState != FieldState.ROCK) throw new IllegalStateException();
        this.healthPoints = healthPoints;
    }

    public void initialize(float size, Integer x, Integer y) {
        //Initialize the sprite
        sprite = new Sprite((Texture) UIConstants.ASSET_FINDER.get("textures/map/grass/standard.png"));
        sprite.setSize(size, size);
        setSpritePosition(sprite.getX(), sprite.getY());

        this.xPosition = x;
        this.yPosition = y;
    }

    public void updateTexture(int textureValue) {
        sprite.setTexture(MapTextureMapper.getTexture(getFieldState(), textureValue));
    }

    public void setSpritePosition(float x, float y) {
        sprite.setPosition(x, y);
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    public void registerHandler(IHaveATileCallback handler) {
        setTouchable(Touchable.enabled);
        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                long timeNow = System.currentTimeMillis();
                if (timeNow - timeLastPressed > 300) {
                    handler.tileClicked(xPosition, yPosition, currentState);
                } else {
                    handler.tileDoubleClicked(xPosition, yPosition, currentState);
                }
                timeLastPressed = timeNow;
                return true;
            }
        });
    }
}
