package marvelous_mashup.team29.editor.model;

import com.badlogic.gdx.scenes.scene2d.Stage;
import marvelous_mashup.team29.ui_util.pop_ups.PopUpAccept;

import java.io.Serializable;

import static marvelous_mashup.team29.editor.model.ParameterSpecifications.*;

/**
 * Implements a JSON standardized 'match/game configuration' as a java class.
 */
public class MatchConfig extends AbstractConfiguration implements Serializable {
    public static final transient int NR_OF_PARAMETERS = 13;

    private int maxRounds = Integer.MIN_VALUE; // Maximum amount of rounds played until Thanos appears
    private int maxRoundTime = Integer.MIN_VALUE; // Time (seconds) a player has for his round
    private int maxGameTime = Integer.MIN_VALUE; // Maximum time (seconds) played till Thanos appears; optional
    private int maxAnimationTime = Integer.MIN_VALUE; // Maximum time (seconds) until the animation is done

    private int spaceStoneCD = Integer.MIN_VALUE; // Cooldown time (rounds) for the Infinity Stones
    private int mindStoneCD = Integer.MIN_VALUE;
    private int realityStoneCD = Integer.MIN_VALUE;
    private int powerStoneCD = Integer.MIN_VALUE;
    private int timeStoneCD = Integer.MIN_VALUE;
    private int soulStoneCD = Integer.MIN_VALUE;
    private int mindStoneDMG = Integer.MIN_VALUE; // Damage Value of the Mind Stone

    private int maxPauseTime = Integer.MIN_VALUE; // Time Limit (seconds) for a game pause
    private int maxResponseTime = Integer.MIN_VALUE; // Duration the Server waits for a Response of a Client before a Timeout

    /**
     * This method checks whether the object is a valid configuration and can be loaded from or saved into a file.
     * Checks whether the attributes all attributes are defined and within a useful range
     * using {@link AbstractConfiguration#verifyNumber(int, String, int, int)} with the values from {@link ParameterSpecifications}.
     *
     * @param stage needed for displaying error pop-ups onto
     * @return is this configuration object valid? (true/false)
     */
    @Override
    public boolean verifyConfig(Stage stage) {
        try {
            verifyNumber(maxRounds, "max rounds", MIN_VALUE_MAX_ROUNDS, MAX_VALUE_MAX_ROUNDS);
            verifyNumber(maxGameTime, "max game time", MIN_VALUE_MAX_GAME_TIME, MAX_VALUE_MAX_GAME_TIME);
            verifyNumber(maxRoundTime, "max round time", MIN_VALUE_MAX_ROUND_TIME, MAX_VALUE_MAX_ROUND_TIME);
            verifyNumber(maxResponseTime, "max response time", MIN_VALUE_MAX_RESPONSE_TIME, MAX_VALUE_MAX_RESPONSE_TIME);
            verifyNumber(maxAnimationTime, "max animation time", MIN_VALUE_MAX_ANIMATION_TIME, MAX_VALUE_MAX_ANIMATION_TIME);
            verifyNumber(maxPauseTime, "max pause time", MIN_VALUE_MAX_PAUSE_TIME, MAX_VALUE_MAX_PAUSE_TIME);
            verifyNumber(spaceStoneCD, "space stone cooldown", MIN_VALUE_SPACE_STONE_CD, MAX_VALUE_SPACE_STONE_CD);
            verifyNumber(realityStoneCD, "reality stone cooldown", MIN_VALUE_REALITY_STONE_CD, MAX_VALUE_REALITY_STONE_CD);
            verifyNumber(powerStoneCD, "power stone cooldown", MIN_VALUE_POWER_STONE_CD, MAX_VALUE_POWER_STONE_CD);
            verifyNumber(timeStoneCD, "time stone cooldown", MIN_VALUE_TIME_STONE_CD, MAX_VALUE_TIME_STONE_CD);
            verifyNumber(soulStoneCD, "soul stone cooldown", MIN_VALUE_SOUL_STONE_CD, MAX_VALUE_SOUL_STONE_CD);
            verifyNumber(mindStoneCD, "mind stone cooldown", MIN_VALUE_MIND_STONE_CD, MAX_VALUE_MIND_STONE_CD);
            verifyNumber(mindStoneDMG, "mind stone damage", MIN_VALUE_MIND_STONE_DMG, MAX_VALUE_MIND_STONE_DMG);
        } catch (IllegalArgumentException exception) {
            new PopUpAccept("Invalid match configuration:\n" + exception.getMessage() +
                    " in order to load or save the configuration.", stage);
            return false;
        }
        return true;
    }

    public int getMaxRounds() {
        return maxRounds;
    }

    public void setMaxRounds(int maxRounds) {
        this.maxRounds = maxRounds;
    }

    public int getMaxRoundTime() {
        return maxRoundTime;
    }

    public void setMaxRoundTime(int maxRoundTime) {
        this.maxRoundTime = maxRoundTime;
    }

    public int getMaxGameTime() {
        return maxGameTime;
    }

    public void setMaxGameTime(int maxGameTime) {
        this.maxGameTime = maxGameTime;
    }

    public int getMaxAnimationTime() {
        return maxAnimationTime;
    }

    public void setMaxAnimationTime(int maxAnimationTime) {
        this.maxAnimationTime = maxAnimationTime;
    }

    public int getMaxPauseTime() {
        return maxPauseTime;
    }

    public void setMaxPauseTime(int maxPauseTime) {
        this.maxPauseTime = maxPauseTime;
    }

    public int getMaxResponseTime() {
        return maxResponseTime;
    }

    public void setMaxResponseTime(int maxResponseTime) {
        this.maxResponseTime = maxResponseTime;
    }

    public int getMindStoneDMG() {
        return mindStoneDMG;
    }

    public void setMindStoneDMG(int mindStoneDMG) {
        this.mindStoneDMG = mindStoneDMG;
    }

    public int getSpaceStoneCD() {
        return spaceStoneCD;
    }

    public void setSpaceStoneCD(int spaceStoneCD) {
        this.spaceStoneCD = spaceStoneCD;
    }

    public int getMindStoneCD() {
        return mindStoneCD;
    }

    public void setMindStoneCD(int mindStoneCD) {
        this.mindStoneCD = mindStoneCD;
    }

    public int getRealityStoneCD() {
        return realityStoneCD;
    }

    public void setRealityStoneCD(int realityStoneCD) {
        this.realityStoneCD = realityStoneCD;
    }

    public int getPowerStoneCD() {
        return powerStoneCD;
    }

    public void setPowerStoneCD(int powerStoneCD) {
        this.powerStoneCD = powerStoneCD;
    }

    public int getTimeStoneCD() {
        return timeStoneCD;
    }

    public void setTimeStoneCD(int timeStoneCD) {
        this.timeStoneCD = timeStoneCD;
    }

    public int getSoulStoneCD() {
        return soulStoneCD;
    }

    public void setSoulStoneCD(int soulStoneCD) {
        this.soulStoneCD = soulStoneCD;
    }
}
