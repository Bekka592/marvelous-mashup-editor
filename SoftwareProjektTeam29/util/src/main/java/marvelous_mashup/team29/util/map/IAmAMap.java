package marvelous_mashup.team29.util.map;

import marvelous_mashup.team29.util.Vector2int;
import marvelous_mashup.team29.util.data.FieldState;

/**
 * Interface for the map
 */
public interface IAmAMap {
    void clear();

    IAmAMapTile[][] getMap();

    IAmAMapTile getAt(int x, int y);

    IAmAMapTile[] getSurroundings(int x, int y);

    Vector2int getSize();

    void initialize();

    void loadMap(FieldState[][] map);

    void setState(int x, int y, FieldState newState);
}
