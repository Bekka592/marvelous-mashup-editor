package marvelous_mashup.team29.util.asset_loader;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

/**
 * Loads the resources of the assets folder using a AssetManager
 * Inspired by: https://stackoverflow.com/questions/31630244/is-there-any-way-to-load-an-entire-directory-at-once-using-libgdx
 * Added Recursion to easily load whole directories of a single type
 * Extended the AssetManager
 */
public class AssetFinder extends AssetManager {
    private static AssetFinder instance;

    public static AssetFinder getAssetFinder() {
        if (instance == null) instance = new AssetFinder();
        return instance;
    }


    //Constructor
    private AssetFinder() {
        super(new InternalFileHandleResolver());
        setLoader(Text.class, new TextLoader(new InternalFileHandleResolver()));

        loadGeneralFiles();
        loadSounds();

        finishLoading();
    }

    public void loadCharacterDisplayTextures() {
        //Characters
        load("textures/characters/complete/Ant Man.png", Texture.class);
        load("textures/characters/complete/Black Panther.png", Texture.class);
        load("textures/characters/complete/Black Widow.png", Texture.class);
        load("textures/characters/complete/Captain America.png", Texture.class);
        load("textures/characters/complete/Captain Marvel.png", Texture.class);
        load("textures/characters/complete/Deadpool.png", Texture.class);
        load("textures/characters/complete/Dr. Strange.png", Texture.class);
        load("textures/characters/complete/Gamora.png", Texture.class);
        load("textures/characters/complete/Ghost Rider.png", Texture.class);
        load("textures/characters/complete/Groot.png", Texture.class);
        load("textures/characters/complete/Hawkeye.png", Texture.class);
        load("textures/characters/complete/Hulk.png", Texture.class);
        load("textures/characters/complete/Iron Man.png", Texture.class);
        load("textures/characters/complete/Jessica Jones.png", Texture.class);
        load("textures/characters/complete/Loki.png", Texture.class);
        load("textures/characters/complete/Mantis.png", Texture.class);
        load("textures/characters/complete/Quicksilver.png", Texture.class);
        load("textures/characters/complete/Rocket Raccoon.png", Texture.class);
        load("textures/characters/complete/Scarlet Witch.png", Texture.class);
        load("textures/characters/complete/Silver Surfer.png", Texture.class);
        load("textures/characters/complete/Spiderman.png", Texture.class);
        load("textures/characters/complete/Starlord.png", Texture.class);
        load("textures/characters/complete/Thor.png", Texture.class);
        load("textures/characters/complete/unknown.png", Texture.class);
        load("textures/characters/complete/Vision.png", Texture.class);
    }

    private void loadGeneralFiles() {
        load("textures/general/background.jpg", Texture.class);
        load("textures/general/logo_editor.png", Texture.class);

        load("json/schema/marvelousmashupschema.json", Text.class);

        load("icons/green_128.png", Texture.class);
        load("icons/green_32.png", Texture.class);
        load("icons/green_64.png", Texture.class);
        load("icons/help.png", Texture.class);
        load("icons/mute.png", Texture.class);
        load("icons/unmute.png", Texture.class);
        load("icons/white_128.png", Texture.class);
        load("icons/white_32.png", Texture.class);
        load("icons/white_64.png", Texture.class);
    }

    private void loadSounds() {
        //Music
        load("music/Editor_Music.ogg", Music.class);

        //Sound
        load("sound/Button_Click_Sound.wav", Sound.class);
    }

    /**
     * Async load of the map textures
     */
    public void loadMapTextures() {
        load("textures/map/grass/standard.png", Texture.class);
        load("textures/map/portal/standard.png", Texture.class);
        load("textures/map/mountain/Center/OneEdge/LeftBottom.png", Texture.class);
        load("textures/map/mountain/Center/OneEdge/LeftTop.png", Texture.class);
        load("textures/map/mountain/Center/OneEdge/RightBottom.png", Texture.class);
        load("textures/map/mountain/Center/OneEdge/RightTop.png", Texture.class);
        load("textures/map/mountain/Center/Standard/Completed.png", Texture.class);
        load("textures/map/mountain/Center/Standard/Normal.png", Texture.class);
        load("textures/map/mountain/Center/TwoEdge/LeftBottomRightTop.png", Texture.class);
        load("textures/map/mountain/Center/TwoEdge/LeftTopRightBottom.png", Texture.class);
        load("textures/map/mountain/Edge/LeftBottom.png", Texture.class);
        load("textures/map/mountain/Edge/LeftBottomFilled.png", Texture.class);
        load("textures/map/mountain/Edge/LeftTop.png", Texture.class);
        load("textures/map/mountain/Edge/LeftTopFilled.png", Texture.class);
        load("textures/map/mountain/Edge/RightBottom.png", Texture.class);
        load("textures/map/mountain/Edge/RightBottomFilled.png", Texture.class);
        load("textures/map/mountain/Edge/RightTop.png", Texture.class);
        load("textures/map/mountain/Edge/RightTopFilled.png", Texture.class);
        load("textures/map/mountain/End/Bottom.png", Texture.class);
        load("textures/map/mountain/End/Left.png", Texture.class);
        load("textures/map/mountain/End/Right.png", Texture.class);
        load("textures/map/mountain/End/Top.png", Texture.class);
        load("textures/map/mountain/Line/Horizontal.png", Texture.class);
        load("textures/map/mountain/Line/HorizontalBottom.png", Texture.class);
        load("textures/map/mountain/Line/HorizontalTop.png", Texture.class);
        load("textures/map/mountain/Line/Vertical.png", Texture.class);
        load("textures/map/mountain/Line/VerticalLeft.png", Texture.class);
        load("textures/map/mountain/Line/VerticalRight.png", Texture.class);
        load("textures/map/mountain/Single/Mountain.png", Texture.class);
        load("textures/map/mountain/Single/MountainOld.png", Texture.class);
        load("textures/map/mountain/Single/MountainOlder.png", Texture.class);
        load("textures/map/mountain/TShape/LeftBottomRight/BackFilled.png", Texture.class);
        load("textures/map/mountain/TShape/LeftBottomRight/BackFirstFilled.png", Texture.class);
        load("textures/map/mountain/TShape/LeftBottomRight/BackSecondFilled.png", Texture.class);
        load("textures/map/mountain/TShape/LeftBottomRight/FirstFilled.png", Texture.class);
        load("textures/map/mountain/TShape/LeftBottomRight/Normal.png", Texture.class);
        load("textures/map/mountain/TShape/LeftBottomRight/SecondFilled.png", Texture.class);
        load("textures/map/mountain/TShape/LeftTopBottom/BackFilled.png", Texture.class);
        load("textures/map/mountain/TShape/LeftTopBottom/BackSecondFilled.png", Texture.class);
        load("textures/map/mountain/TShape/LeftTopBottom/FirstFilled.png", Texture.class);
        load("textures/map/mountain/TShape/LeftTopBottom/Normal.png", Texture.class);
        load("textures/map/mountain/TShape/LeftTopBottom/SecondFilled.png", Texture.class);
        load("textures/map/mountain/TShape/LeftTopRight/BackFilled.png", Texture.class);
        load("textures/map/mountain/TShape/LeftTopRight/FirstFilled.png", Texture.class);
        load("textures/map/mountain/TShape/LeftTopRight/Normal.png", Texture.class);
        load("textures/map/mountain/TShape/LeftTopRight/SecondFilled.png", Texture.class);
        load("textures/map/mountain/TShape/RightTopBottom/BackFilled.png", Texture.class);
        load("textures/map/mountain/TShape/RightTopBottom/BackSecondFilled.png", Texture.class);
        load("textures/map/mountain/TShape/RightTopBottom/FirstFilled.png", Texture.class);
        load("textures/map/mountain/TShape/RightTopBottom/Normal.png", Texture.class);
        load("textures/map/mountain/TShape/RightTopBottom/SecondFilled.png", Texture.class);
    }
}