package marvelous_mashup.team29.util.map;

import marvelous_mashup.team29.util.Vector2int;
import marvelous_mashup.team29.util.data.FieldState;

/**
 * This class contains an array of all the map tiles
 * It also contains some basic functionality for the map
 */
public class Map implements IAmAMap {
    protected IAmAMapTile[][] mapTiles;

    /**
     * Resets the state of all the map tiles to grass.
     * Can be called bevor a new gameState is read to prevent the creation of a new map.
     */
    @Override
    public void clear() {
        for (IAmAMapTile[] mapTile : mapTiles) {
            for (IAmAMapTile iAmAMapTile : mapTile) {
                iAmAMapTile.setFieldState(FieldState.GRASS);
            }
        }
    }

    /**
     * Creates the map array and sets the state of the map tiles to the state of the FieldState array
     *
     * @param map array of FieldState
     */
    @Override
    public void loadMap(FieldState[][] map) {
        if (map.length == 0) throw new IllegalArgumentException();
        mapTiles = new MapTile[map.length][map[0].length];
        initialize();
        for (var y = 0; y < mapTiles.length; y++) {
            for (var x = 0; x < mapTiles[y].length; x++) {
                mapTiles[y][x].setFieldState(map[y][x]);
            }
        }
    }

    @Override
    public void initialize() {
        for (var y = 0; y < mapTiles.length; y++) {
            for (var x = 0; x < mapTiles[y].length; x++) {
                mapTiles[y][x] = new MapTile(FieldState.GRASS);
            }
        }
    }

    @Override
    public void setState(int x, int y, FieldState newState) {
        mapTiles[y][x].setFieldState(newState);
    }

    /**
     * Used to skip direct access to the map
     *
     * @param x Position
     * @param y Position
     * @return The map tile at the x and y position of the map
     */
    @Override
    public IAmAMapTile getAt(int x, int y) {
        if (x < 0 || y < 0 || y >= mapTiles.length || x >= mapTiles[0].length) return null;
        return mapTiles[y][x];
    }

    /**
     * Creates an array of the surrounding tiles
     * The array is always of the length 8 and is created from the top left to right bottom.
     * Tiles outside of the map are null tiles
     * 1 2 3
     * 4 X 5
     * 6 7 8
     *
     * @param x Position
     * @param y Position
     * @return An array with length 8 of the surrounding tiles
     */
    @Override
    public IAmAMapTile[] getSurroundings(int x, int y) {
        var tileArray = new IAmAMapTile[8];

        //Top
        tileArray[0] = getAt(x - 1, y - 1);
        tileArray[1] = getAt(x, y - 1);
        tileArray[2] = getAt(x + 1, y - 1);

        //Left and right
        tileArray[3] = getAt(x - 1, y);
        tileArray[4] = getAt(x + 1, y);

        //Bottom
        tileArray[5] = getAt(x - 1, y + 1);
        tileArray[6] = getAt(x, y + 1);
        tileArray[7] = getAt(x + 1, y + 1);
        return tileArray;
    }

    @Override
    public IAmAMapTile[][] getMap() {
        return mapTiles;
    }

    @Override
    public Vector2int getSize() {
        if (mapTiles.length == 0) throw new IllegalStateException();
        return new Vector2int(mapTiles.length, mapTiles[0].length);
    }
}
