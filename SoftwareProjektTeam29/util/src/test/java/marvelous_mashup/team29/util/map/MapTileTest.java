package marvelous_mashup.team29.util.map;

import marvelous_mashup.team29.util.data.FieldState;
import marvelous_mashup.team29.util.infinity_stones.IAmAnInfinityStone;
import marvelous_mashup.team29.util.infinity_stones.InfinityStones;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapTileTest {
    private static MapTile mapTile;
    private static IAmAnInfinityStone testStone;

    public MapTileTest() {
        mapTile = new MapTile(FieldState.GRASS);
        testStone = new IAmAnInfinityStone() {
            @Override
            public void setCooldown(int duration) {

            }

            @Override
            public int getCooldown() {
                return 10;
            }

            @Override
            public InfinityStones getType() {
                return null;
            }
        };
    }

    @Test
    void setFieldState() {
        //Get
        assertEquals(FieldState.GRASS, mapTile.getFieldState());
        assertEquals(0, mapTile.getHealthPoints());
        assertNull(mapTile.getInfinityStone());
        //Set
        assertThrows(IllegalStateException.class, () -> mapTile.setInfinityStone(testStone));
        assertThrows(IllegalStateException.class, () -> mapTile.setHealthPoints(0));

        mapTile.setFieldState(FieldState.ROCK);
        //Get
        assertEquals(FieldState.ROCK, mapTile.getFieldState());
        assertEquals(0, mapTile.getHealthPoints());
        assertNull(mapTile.getInfinityStone());
        //Set
        assertThrows(IllegalStateException.class, () -> mapTile.setInfinityStone(testStone));
        mapTile.setHealthPoints(123);
        assertEquals(123, mapTile.getHealthPoints());

        mapTile.setFieldState(FieldState.HERO);
        //Get
        assertEquals(FieldState.HERO, mapTile.getFieldState());
        assertEquals(0, mapTile.getHealthPoints());
        assertNull(mapTile.getInfinityStone());
        //Set
        assertThrows(IllegalStateException.class, () -> mapTile.setInfinityStone(testStone));
        assertThrows(IllegalStateException.class, () -> mapTile.setHealthPoints(0));

        mapTile.setFieldState(FieldState.INFINITY_STONE);
        //Get
        assertEquals(FieldState.INFINITY_STONE, mapTile.getFieldState());
        assertEquals(0, mapTile.getHealthPoints());
        assertNull(mapTile.getInfinityStone());
        //Set
        mapTile.setInfinityStone(testStone);
        assertEquals(testStone.getCooldown(), mapTile.getInfinityStone().getCooldown());
        assertEquals(testStone, mapTile.getInfinityStone());
    }
}