package marvelous_mashup.team29.ui_util.map;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import marvelous_mashup.team29.ui_util.UISettings;
import marvelous_mashup.team29.util.data.FieldState;
import marvelous_mashup.team29.util.map.IAmAMapTile;
import marvelous_mashup.team29.util.map.Map;

import java.util.Arrays;
import java.util.Objects;

public class MapWithUI extends Map {

    @Override
    public void clear() {
        super.clear();
        updateAllTextures();
    }

    public void registerHandler(IHaveATileCallback handler) {
        for (MapTileWithUI[] mapTile : (MapTileWithUI[][]) mapTiles) {
            for (MapTileWithUI iAmAMapTile : mapTile) {
                iAmAMapTile.registerHandler(handler);
            }
        }
    }

    @Override
    public void setState(int x, int y, FieldState newState) {
        super.setState(x, y, newState);
        ((MapTileWithUI) mapTiles[y][x]).updateTexture(getTextureValue(x, y));
        refreshSurroundingTiles(x, y);
    }

    private void updateAllTextures() {
        for (var y = 0; y < mapTiles.length; y++) {
            for (var x = 0; x < mapTiles[y].length; x++) {
                ((MapTileWithUI) mapTiles[y][x]).updateTexture(getTextureValue(x, y));
            }
        }
    }

    @Override
    public void loadMap(FieldState[][] map) {
        if (map.length == 0) throw new IllegalStateException();
        mapTiles = new MapTileWithUI[map.length][map[0].length];

        float tileSize = map.length > map[0].length ? UISettings.getGameBoardDimension().y / mapTiles.length :
                UISettings.getGameBoardDimension().x / mapTiles[0].length;

        UISettings.setMapFieldTileSize(tileSize);

        float xOffset = (UISettings.getGameBoardDimension().x / 2f) - ((map[0].length / 2f) * tileSize);
        float yOffset = (UISettings.getGameBoardDimension().y / 2f) - ((map.length / 2f) * tileSize);
        float topY = UISettings.getGameBoardDimension().y - tileSize;

        for (var yPos = 0; yPos < mapTiles.length; yPos++) {
            for (var xPos = 0; xPos < mapTiles[yPos].length; xPos++) {
                mapTiles[yPos][xPos] = new MapTileWithUI(map[yPos][xPos]);
                ((MapTileWithUI) mapTiles[yPos][xPos]).initialize(tileSize, xPos, yPos);
                ((MapTileWithUI) mapTiles[yPos][xPos]).setSpritePosition(xPos * tileSize + xOffset, topY - yPos * tileSize - yOffset);
            }
        }

        //Sets the Textures of all the Tiles
        updateAllTextures();
    }

    public int getTextureValue(int x, int y) {
        var value = 0;
        IAmAMapTile[] array = getSurroundings(x, y);
        for (IAmAMapTile mapTileWithUI : array) {
            value <<= 1;
            if (mapTileWithUI != null && mapTileWithUI.getFieldState() == FieldState.ROCK) value += 1;
        }
        return value;
    }

    public void addToStage(Stage stage) {
        for (IAmAMapTile[] tileArray : mapTiles) {
            for (IAmAMapTile tile : tileArray) {
                stage.addActor((MapTileWithUI) tile);
            }
        }
    }

    public void removeFromStage(Stage stage) {
        Array<Actor> actors = stage.getActors();
        Arrays.stream(mapTiles).forEach(line -> Arrays.stream(line).forEach(element -> actors.removeValue((MapTileWithUI) element, true)));
    }

    public void refreshSurroundingTiles(int x, int y) {
        Arrays.stream(getSurroundings(x, y)).filter(Objects::nonNull).forEach(iAmAMapTile -> {
            MapTileWithUI tile = (MapTileWithUI) iAmAMapTile;
            tile.updateTexture(getTextureValue(tile.getXPosition(), tile.getYPosition()));
        });
    }
}
