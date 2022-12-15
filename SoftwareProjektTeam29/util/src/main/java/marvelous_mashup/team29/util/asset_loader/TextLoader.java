package marvelous_mashup.team29.util.asset_loader;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

/**
 * Used to read Text files with the AssetManager / AssetFinder
 * Source: https://gamedev.stackexchange.com/questions/101326/load-a-simple-text-file-through-asset-manager-in-libgdx
 */
public class TextLoader extends AsynchronousAssetLoader<Text, TextLoader.TextParameter> {
    private Text text;

    public TextLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, TextParameter parameter) {
        this.text = null;
        this.text = new Text(file);
    }

    @Override
    public Text loadSync(AssetManager manager, String fileName, FileHandle file, TextParameter parameter) {
        var newText = this.text;
        this.text = null;
        return newText;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, TextParameter parameter) {
        return null;
    }

    public static class TextParameter extends AssetLoaderParameters<Text> {
        //Empty because all the functionality is in the Super class
    }
}
