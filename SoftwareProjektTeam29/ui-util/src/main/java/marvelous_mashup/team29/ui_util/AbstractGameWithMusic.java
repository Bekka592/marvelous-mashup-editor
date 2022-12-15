package marvelous_mashup.team29.ui_util;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import marvelous_mashup.team29.ui_util.components.UIShapes;

/**
 * Extending the basic {@link Game} class of LibGdx.
 * Providing functionalities for handling the application's sounds.
 * For information on how to use this screen, see editor/Editor.
 */
public abstract class AbstractGameWithMusic extends Game {
    private static final float BASIC_SFX_VOLUME = 0.5f;
    private static final float BASIC_MUSIC_VOLUME = 0.2f;

    private static Music backgroundMusic;
    private static Sound buttonClickSound;
    private static float sfxVolume = 0.5f;

    private static ApplicationTypeEnum applicationType;

    /**
     * This method needs to be called in the very beginning to set (aka enable) the button click sound.
     */
    public static void initSfx() {
        buttonClickSound = UIConstants.ASSET_FINDER.get("sound/Button_Click_Sound.wav");
    }

    /**
     * Loading music to play in the background.
     * Currently only supporting one single music file at a time.
     *
     * @param backgroundMusic to play, needs to be an .ogg in order to get looped!
     */
    protected static void setBackgroundMusic(Music backgroundMusic) {
        AbstractGameWithMusic.backgroundMusic = backgroundMusic;
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.2f);
        backgroundMusic.play();
    }

    /**
     * Method called whenever the mute button is clicked.
     * Inverts the current mute setting and alters the volume values.
     */
    public static void setSoundsMuted() {
        if (!isSoundsMuted()) {
            playButtonClickSound();
            backgroundMusic.setVolume(0);
            sfxVolume = 0;
        } else {
            backgroundMusic.setVolume(BASIC_MUSIC_VOLUME);
            sfxVolume = BASIC_SFX_VOLUME;
            playButtonClickSound();
        }
    }

    /**
     * When this method is called the button click sound will appear.
     */
    public static void playButtonClickSound() {
        if (buttonClickSound == null) return;
        var buttonClickSound = AbstractGameWithMusic.buttonClickSound;
        long buttonClickSoundId = buttonClickSound.play(sfxVolume);
        buttonClickSound.setPitch(buttonClickSoundId, 2f);
    }

    /**
     * Needed for the settings screen.
     */
    public static float getSfxVolume() {
        return sfxVolume;
    }

    /**
     * Needed for the settings screen.
     */
    public static void setSfxVolume(float sfxVolume) {
        AbstractGameWithMusic.sfxVolume = sfxVolume;
    }

    /**
     * Needed for the settings screen.
     */
    public static float getMusicVolume() {
        return (backgroundMusic == null) ? 0 : backgroundMusic.getVolume();
    }

    /**
     * Needed for the settings screen.
     */
    public static void setMusicVolume(float musicVolume) {
        backgroundMusic.setVolume(musicVolume);
    }

    public static boolean isSoundsMuted() {
        return getMusicVolume() == 0 && getSfxVolume() == 0;
    }

    public static ApplicationTypeEnum getApplicationType() {
        return applicationType;
    }

    public static void setApplicationType(ApplicationTypeEnum applicationType) {
        AbstractGameWithMusic.applicationType = applicationType;
    }

    /**
     * Calls {@link Game#render()} and keeps the asset finder up-to-date.
     */
    @Override
    public void render() {
        super.render();
        UIConstants.ASSET_FINDER.update();
    }

    /**
     * When setting a new screen the old screen now automatically is disposed.
     *
     * @param screen new screen
     */
    @Override
    public void setScreen(Screen screen) {
        var oldScreen = this.screen;
        super.setScreen(screen);
        if (oldScreen != null) oldScreen.dispose();
    }

    /**
     * Called when the application is exited.
     * Releasing all sound resources to have a clean exit.
     */
    @Override
    public void dispose() {
        UIConstants.ASSET_FINDER.dispose();
        UIShapes.disposeShapeRenderer();
        if (backgroundMusic != null) backgroundMusic.dispose();
        buttonClickSound.dispose();
    }
}
