package marvelous_mashup.team29.util.map;

import marvelous_mashup.team29.util.Vector2int;
import marvelous_mashup.team29.util.data.FieldState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MapTest {
    private static Map map;
    private static final FieldState[][] testStateArray = new FieldState[][]{
            {FieldState.GRASS, FieldState.GRASS, FieldState.ROCK},
            {FieldState.GRASS, FieldState.ROCK, FieldState.HERO},
            {FieldState.INFINITY_STONE, FieldState.INFINITY_STONE, FieldState.ROCK}
    };

    public MapTest() {
        map = new Map();
    }

    private static Stream<Arguments> getSurroundingsSource() {
        return Stream.of(
                Arguments.arguments(new FieldState[]{null, null, null, null, FieldState.GRASS, null, FieldState.GRASS, FieldState.ROCK}, new Vector2int(0, 0)),
                Arguments.arguments(new FieldState[]{null, null, null, FieldState.GRASS, FieldState.ROCK, FieldState.GRASS, FieldState.ROCK, FieldState.HERO}, new Vector2int(1, 0)),
                Arguments.arguments(new FieldState[]{null, null, null, FieldState.GRASS, null, FieldState.ROCK, FieldState.HERO, null}, new Vector2int(2, 0)),

                Arguments.arguments(new FieldState[]{null, FieldState.GRASS, FieldState.GRASS, null, FieldState.ROCK, null, FieldState.INFINITY_STONE, FieldState.INFINITY_STONE}, new Vector2int(0, 1)),
                Arguments.arguments(new FieldState[]{FieldState.GRASS, FieldState.GRASS, FieldState.ROCK, FieldState.GRASS, FieldState.HERO, FieldState.INFINITY_STONE, FieldState.INFINITY_STONE, FieldState.ROCK}, new Vector2int(1, 1)),
                Arguments.arguments(new FieldState[]{FieldState.GRASS, FieldState.ROCK, null, FieldState.ROCK, null, FieldState.INFINITY_STONE, FieldState.ROCK, null}, new Vector2int(2, 1)),

                Arguments.arguments(new FieldState[]{null, FieldState.GRASS, FieldState.ROCK, null, FieldState.INFINITY_STONE, null, null, null}, new Vector2int(0, 2)),
                Arguments.arguments(new FieldState[]{FieldState.GRASS, FieldState.ROCK, FieldState.HERO, FieldState.INFINITY_STONE, FieldState.ROCK, null, null, null}, new Vector2int(1, 2)),
                Arguments.arguments(new FieldState[]{FieldState.ROCK, FieldState.HERO, null, FieldState.INFINITY_STONE, null, null, null, null}, new Vector2int(2, 2))
        );
    }

    @Test
    void clear() {
        map.loadMap(testStateArray);
        map.clear();
        for (IAmAMapTile[] iAmAMapTiles : map.getMap()) {
            for (IAmAMapTile iAmAMapTile : iAmAMapTiles) {
                assertEquals(FieldState.GRASS, iAmAMapTile.getFieldState());
            }
        }
    }

    @Test
    void loadMap() {
        map.loadMap(testStateArray);
        IAmAMapTile[][] mapMap = map.getMap();
        for (int y = 0; y < mapMap.length; y++) {
            for (int x = 0; x < mapMap[y].length; x++) {
                //Also testing getAt
                assertEquals(testStateArray[y][x], map.getAt(x, y).getFieldState());
            }
        }
    }


    @ParameterizedTest
    @MethodSource("getSurroundingsSource")
    void getSurroundings(FieldState[] expected, Vector2int pair) {
        map.loadMap(testStateArray);
        IAmAMapTile[] surroundings = map.getSurroundings(pair.getX(), pair.getY());
        for (int i = 0; i < surroundings.length; i++) {
            assertEquals(expected[i], surroundings[i] != null ? surroundings[i].getFieldState() : surroundings[i]);
        }
    }
}