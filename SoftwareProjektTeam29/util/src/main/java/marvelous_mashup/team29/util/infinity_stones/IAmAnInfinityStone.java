package marvelous_mashup.team29.util.infinity_stones;

/**
 * Interface fot the Infinity Stones
 */
public interface IAmAnInfinityStone {
    int getCooldown();

    void setCooldown(int duration);

    InfinityStones getType();
}
