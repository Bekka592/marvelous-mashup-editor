package marvelous_mashup.team29.util;

import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MathUtilitiesTest {
    /**
     * Just checking whether the results are in the right range.
     * The rest is assumed right, because it's the calculation of Math.random().
     */
    @RepeatedTest(10)
    void randomIntInRangeTest() {
        int randomValue = MathUtilities.randomIntInRange(1, 6);
        assertTrue(1 <= randomValue && randomValue <= 6);

        randomValue = MathUtilities.randomIntInRange(0, 1100);
        assertTrue(0 <= randomValue && randomValue <= 1100);

        randomValue = MathUtilities.randomIntInRange(-5, 5);
        assertTrue(-5 <= randomValue && randomValue <= 5);

        randomValue = MathUtilities.randomIntInRange(-7, -3);
        assertTrue(-7 <= randomValue && randomValue <= -3);
    }
}