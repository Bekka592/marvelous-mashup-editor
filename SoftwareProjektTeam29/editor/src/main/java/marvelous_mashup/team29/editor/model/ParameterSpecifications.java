package marvelous_mashup.team29.editor.model;

/**
 * Class storing all of the constants for specifying the configuration's parameter ranges etc.
 * You can configure the values here according to personal preferences.
 */
public class ParameterSpecifications {
    /**
     * Specifications for verifying valid configurations.
     * For example if you want only match configurations with a space stone cooldown between 1 and 100 to be validated be the
     * editor (so you can load/save that configuration), you can define {@link ParameterSpecifications#MIN_VALUE_SPACE_STONE_CD}
     * as 1 and {@link ParameterSpecifications#MAX_VALUE_SPACE_STONE_CD} as 100.
     */

    // CHARACTER
    public static final int MIN_VALUE_CHARACTER_ID = 1;
    public static final int MAX_VALUE_CHARACTER_ID = CharacterConfigContainer.NEEDED_NR_OF_CHARACTERS;
    public static final int MIN_VALUE_HP = 1;
    public static final int MAX_VALUE_HP = 2000;
    public static final int MIN_VALUE_MP = 0;
    public static final int MAX_VALUE_MP = 1000;
    public static final int MIN_VALUE_AP = 0;
    public static final int MAX_VALUE_AP = 1000;
    public static final int MIN_VALUE_MELEE_DAMAGE = 0;
    public static final int MAX_VALUE_MELEE_DAMAGE = 999;
    public static final int MIN_VALUE_RANGE_COMBAT_DAMAGE = 0;
    public static final int MAX_VALUE_RANGE_COMBAT_DAMAGE = 2000;
    public static final int MIN_VALUE_RANGE_COMBAT_REACH = 1;
    public static final int MAX_VALUE_RANGE_COMBAT_REACH = 500;

    // MATCH
    public static final int MIN_VALUE_MAX_ROUNDS = 0;
    public static final int MAX_VALUE_MAX_ROUNDS = 500;
    public static final int MIN_VALUE_MAX_GAME_TIME = 0;
    public static final int MAX_VALUE_MAX_GAME_TIME = Integer.MAX_VALUE;
    public static final int MIN_VALUE_MAX_ROUND_TIME = 1;
    public static final int MAX_VALUE_MAX_ROUND_TIME = 5000;
    public static final int MIN_VALUE_MAX_RESPONSE_TIME = 1;
    public static final int MAX_VALUE_MAX_RESPONSE_TIME = 5000;
    public static final int MIN_VALUE_MAX_ANIMATION_TIME = 0;
    public static final int MAX_VALUE_MAX_ANIMATION_TIME = 5000;
    public static final int MIN_VALUE_MAX_PAUSE_TIME = 0;
    public static final int MAX_VALUE_MAX_PAUSE_TIME = Integer.MAX_VALUE;

    public static final int MIN_VALUE_SPACE_STONE_CD = 0;
    public static final int MAX_VALUE_SPACE_STONE_CD = 50;
    public static final int MIN_VALUE_REALITY_STONE_CD = 0;
    public static final int MAX_VALUE_REALITY_STONE_CD = 50;
    public static final int MIN_VALUE_POWER_STONE_CD = 0;
    public static final int MAX_VALUE_POWER_STONE_CD = 50;
    public static final int MIN_VALUE_TIME_STONE_CD = 0;
    public static final int MAX_VALUE_TIME_STONE_CD = 50;
    public static final int MIN_VALUE_SOUL_STONE_CD = 0;
    public static final int MAX_VALUE_SOUL_STONE_CD = 50;
    public static final int MIN_VALUE_MIND_STONE_CD = 0;
    public static final int MAX_VALUE_MIND_STONE_CD = 50;
    public static final int MIN_VALUE_MIND_STONE_DMG = 0;
    public static final int MAX_VALUE_MIND_STONE_DMG = 1000;

    // SCENARIO
    public static final int MIN_MAP_HEIGHT = 1;
    public static final int MAX_MAP_HEIGHT = 100;
    public static final int MIN_MAP_WIDTH = 1;
    public static final int MAX_MAP_WIDTH = 100;
    public static final int MIN_NR_OF_GRASS_FIELDS = 20;
    public static final int MIN_NR_OF_PORTAL_FIELDS = 2;

    /**
     * Specifications for the random generated configurations.
     * For example if you want the random generator to always set the space stone cooldown between 1 and 10,
     * you can define {@link ParameterSpecifications#USEFUL_MIN_VALUE_SPACE_STONE_CD} as 1 and
     * {@link ParameterSpecifications#USEFUL_MAX_VALUE_SPACE_STONE_CD} as 10.
     */

    // CHARACTER
    public static final int USEFUL_MIN_VALUE_HP = 50;
    public static final int USEFUL_MAX_VALUE_HP = 150;
    public static final int USEFUL_MIN_VALUE_FEATURE_POINTS = 1;
    public static final int USEFUL_MIN_VALUE_TOTAL_FEATURE_POINTS = 20;
    public static final int USEFUL_MAX_VALUE_TOTAL_FEATURE_POINTS = 40;

    // MATCH
    public static final int USEFUL_MIN_VALUE_MAX_ROUNDS = 0;
    public static final int USEFUL_MAX_VALUE_MAX_ROUNDS = 30;
    public static final int USEFUL_MIN_VALUE_MAX_GAME_TIME = 0;
    public static final int USEFUL_MAX_VALUE_MAX_GAME_TIME = 3600;
    public static final int USEFUL_MIN_VALUE_MAX_ROUND_TIME = 30;
    public static final int USEFUL_MAX_VALUE_MAX_ROUND_TIME = 600;
    public static final int USEFUL_MIN_VALUE_MAX_RESPONSE_TIME = 10;
    public static final int USEFUL_MAX_VALUE_MAX_RESPONSE_TIME = 200;
    public static final int USEFUL_MIN_VALUE_MAX_ANIMATION_TIME = 0;
    public static final int USEFUL_MAX_VALUE_MAX_ANIMATION_TIME = 300;
    public static final int USEFUL_MIN_VALUE_MAX_PAUSE_TIME = 0;
    public static final int USEFUL_MAX_VALUE_MAX_PAUSE_TIME = 1500;

    public static final int USEFUL_MIN_VALUE_SPACE_STONE_CD = 1;
    public static final int USEFUL_MAX_VALUE_SPACE_STONE_CD = 6;
    public static final int USEFUL_MIN_VALUE_REALITY_STONE_CD = 1;
    public static final int USEFUL_MAX_VALUE_REALITY_STONE_CD = 6;
    public static final int USEFUL_MIN_VALUE_POWER_STONE_CD = 1;
    public static final int USEFUL_MAX_VALUE_POWER_STONE_CD = 6;
    public static final int USEFUL_MIN_VALUE_TIME_STONE_CD = 1;
    public static final int USEFUL_MAX_VALUE_TIME_STONE_CD = 6;
    public static final int USEFUL_MIN_VALUE_SOUL_STONE_CD = 1;
    public static final int USEFUL_MAX_VALUE_SOUL_STONE_CD = 6;
    public static final int USEFUL_MIN_VALUE_MIND_STONE_CD = 1;
    public static final int USEFUL_MAX_VALUE_MIND_STONE_CD = 6;
    public static final int USEFUL_MIN_VALUE_MIND_STONE_DMG = 1;
    public static final int USEFUL_MAX_VALUE_MIND_STONE_DMG = 50;

    // SCENARIO
    public static final int USEFUL_MIN_MAP_HEIGHT = 5;
    public static final int USEFUL_MAX_MAP_HEIGHT = 50;
    public static final int USEFUL_MIN_MAP_WIDTH = 5;
    public static final int USEFUL_MAX_MAP_WIDTH = 100;
    public static final int MIN_GRASS_RATIO = 50; // about 50% of the map will be grass
    public static final int MAX_GRASS_RATIO = 85; // about 85% of the map will be grass
    public static final int MIN_PORTAL_RATIO = 1; // about 1% of the map will be portals
    public static final int MAX_PORTAL_RATIO = 3; // about 3% of the map will be portals

    private ParameterSpecifications() {
    }
}
