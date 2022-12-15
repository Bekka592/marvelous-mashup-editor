package marvelous_mashup.team29.util.map;

import marvelous_mashup.team29.util.data.FieldState;
import marvelous_mashup.team29.util.infinity_stones.IAmAnInfinityStone;

/**
 * Interface for the map tiles
 */
public interface IAmAMapTile {
    FieldState getFieldState();

    void setFieldState(FieldState state);

    IAmAnInfinityStone getInfinityStone();

    void setInfinityStone(IAmAnInfinityStone stone);

    int getHealthPoints();

    void setHealthPoints(int healthPoints);
}
