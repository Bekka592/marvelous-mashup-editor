package marvelous_mashup.team29.editor.model;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.google.gson.annotations.SerializedName;
import marvelous_mashup.team29.ui_util.pop_ups.PopUpAccept;

import java.io.Serializable;

import static marvelous_mashup.team29.editor.model.ParameterSpecifications.*;

/**
 * Implements a playable character (hero) for the Marvelous Mashup game as a java class.
 */
public class CharacterConfig extends AbstractConfiguration implements Serializable {
    public static final transient int NR_OF_PARAMETERS = 7;
    public static final transient String IMAGE_OF_UNKNOWN_CHARACTER = "unknown.png";

    private int characterID = Integer.MIN_VALUE;
    private String name = "";

    @SerializedName(value = "HP")
    private int hp = Integer.MIN_VALUE; // health points
    @SerializedName(value = "MP")
    private int mp = Integer.MIN_VALUE; // movement points
    @SerializedName(value = "AP")
    private int ap = Integer.MIN_VALUE; // action points

    private int meleeDamage = Integer.MIN_VALUE;
    private int rangeCombatDamage = Integer.MIN_VALUE;
    private int rangeCombatReach = Integer.MIN_VALUE;

    /**
     * This method checks whether the object is a valid configuration and can be loaded from or saved into a file.
     * Checks whether the attributes name, HP, MP, AP, meleeDamage, range combat damage and range combat reach are
     * defined and within a useful range using {@link AbstractConfiguration#verifyNumber(int, String, int, int)}
     * and {@link AbstractConfiguration#verifyString(String, String)}.
     * By validating the character ID the method checks whether this config belongs to one of the 24 supported characters.
     * Only if it does so, the configuration will be verified.
     *
     * @param stage needed for displaying error pop-ups onto
     * @return is this character valid? (true/false)
     */
    @Override
    public boolean verifyConfig(Stage stage) {
        try {
            verifyNumber(characterID, "character ID", MIN_VALUE_CHARACTER_ID, MAX_VALUE_CHARACTER_ID);
            verifyString(name, "name");
            verifyNumber(hp, "HP", MIN_VALUE_HP, MAX_VALUE_HP);
            verifyNumber(mp, "MP", MIN_VALUE_MP, MAX_VALUE_MP);
            verifyNumber(ap, "AP", MIN_VALUE_AP, MAX_VALUE_AP);
            verifyNumber(meleeDamage, "melee damage", MIN_VALUE_MELEE_DAMAGE, MAX_VALUE_MELEE_DAMAGE);
            verifyNumber(rangeCombatDamage, "range combat damage", MIN_VALUE_RANGE_COMBAT_DAMAGE, MAX_VALUE_RANGE_COMBAT_DAMAGE);
            verifyNumber(rangeCombatReach, "range combat reach", MIN_VALUE_RANGE_COMBAT_REACH, MAX_VALUE_RANGE_COMBAT_REACH);
        } catch (IllegalArgumentException exception) {
            if (exception.getMessage().contains("character ID")) {
                new PopUpAccept("Invalid character configuration:\nOnly the 24 characters defined in the network " +
                        "standard document are supported by this Editor and can be loaded from or saved to a file.", stage);
            } else {
                new PopUpAccept("Invalid character configuration:\n" + exception.getMessage() +
                        " in order to load or save the configuration.", stage);
            }
            return false;
        }
        return true;
    }

    public int getCharacterID() {
        return characterID;
    }

    public void setCharacterID(int characterID) {
        this.characterID = characterID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public int getAp() {
        return ap;
    }

    public void setAp(int ap) {
        this.ap = ap;
    }

    public int getMeleeDamage() {
        return meleeDamage;
    }

    public void setMeleeDamage(int meleeDamage) {
        this.meleeDamage = meleeDamage;
    }

    public int getRangeCombatDamage() {
        return rangeCombatDamage;
    }

    public void setRangeCombatDamage(int rangeCombatDamage) {
        this.rangeCombatDamage = rangeCombatDamage;
    }

    public int getRangeCombatReach() {
        return rangeCombatReach;
    }

    public void setRangeCombatReach(int rangeCombatReach) {
        this.rangeCombatReach = rangeCombatReach;
    }
}
