package marvelous_mashup.team29.util;

import java.util.Random;

/**
 * Providing helper functions for dealing with numbers.
 */
public class MathUtilities {
    private static final Random random = new Random();

    private MathUtilities() {
    }

    /**
     * Giving an random integer in range [min, max].
     *
     * @param min minimum value
     * @param max maximum value
     * @return random integer
     */
    public static int randomIntInRange(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
}
