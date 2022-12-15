package marvelous_mashup.team29.util.asset_loader;

import com.badlogic.gdx.files.FileHandle;

/**
 * Used to read Text files with the AssetManager / AssetFinder
 * This is the container class of the loaded text
 * Source: https://gamedev.stackexchange.com/questions/101326/load-a-simple-text-file-through-asset-manager-in-libgdx
 */
public class Text {
    private String string;

    public Text(FileHandle file) {
        this.string = new String(file.readBytes());
    }

    public String getString() {
        return this.string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
