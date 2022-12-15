package marvelous_mashup.team29.util.map;

import marvelous_mashup.team29.util.data.FieldState;
import marvelous_mashup.team29.util.infinity_stones.IAmAnInfinityStone;

/**
 * This class represents a single tile of the map.
 * Container class
 */
public class MapTile implements IAmAMapTile {
    //Variables
    private FieldState currentTileState;
    private IAmAnInfinityStone infinityStone;
    private int healthPoints;

    //Constructor
    public MapTile(FieldState state) {
        this.currentTileState = state;
    }

    //Setter

    //Getter
    @Override
    public FieldState getFieldState() {
        return this.currentTileState;
    }

    /**
     * Sets the state and resets unwanted values
     *
     * @param state The new state
     */
    @Override
    public void setFieldState(FieldState state) {
        if (state != FieldState.ROCK) this.healthPoints = 0;
        if (state != FieldState.INFINITY_STONE) this.infinityStone = null;
        this.currentTileState = state;
    }

    @Override
    public IAmAnInfinityStone getInfinityStone() {
        return this.infinityStone;
    }

    @Override
    public void setInfinityStone(IAmAnInfinityStone stone) {
        if (currentTileState != FieldState.INFINITY_STONE) throw new IllegalStateException();
        this.infinityStone = stone;
    }

    @Override
    public int getHealthPoints() {
        return this.healthPoints;
    }

    @Override
    public void setHealthPoints(int healthPoints) {
        if (currentTileState != FieldState.ROCK) throw new IllegalStateException();
        this.healthPoints = healthPoints;
    }
}
