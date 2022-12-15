package marvelous_mashup.team29.editor.gui.file_io_dialogs;

import marvelous_mashup.team29.editor.gui.config_screens.AbstractConfigurationScreen;
import marvelous_mashup.team29.editor.model.ConfigurationTypeEnum;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/**
 * Class that implements the File Chooser Pop-Up for loading/saving files as a JFileChooser.
 */
public class FileChooserDialog {
    private FileChooserDialog() {
    }

    /**
     * Here a File Chooser PopUp is called and opened whenever the save-button or the load-button on an
     * {@link AbstractConfigurationScreen} is clicked.
     * Configures a new FileChooser as a {@link FileChooserEnum#LOAD_DIALOG} or {@link FileChooserEnum#SAVE_DIALOG}
     * that lets the user select a file to load.
     * Info: This method is always called in a separate Thread.
     *
     * @param fileChooserType is this file chooser used to load a file, or to save a file?
     * @param configType      are we looking for / handling with a character, match or scenario config?
     * @return file selected by the user
     */
    public static File showFileChooser(FileChooserEnum fileChooserType, ConfigurationTypeEnum configType) {
        if (fileChooserType == null || fileChooserType == FileChooserEnum.NONE) return null;

        var fileChooser = new JFileChooser();
        fileChooser.setDialogTitle((fileChooserType == FileChooserEnum.LOAD_DIALOG) ? "Choose a file to load."
                : "Specify a file to save your configuration to.");
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setSelectedFile(new File("myConfig" + configType.getFileExtension()));

        if (fileChooserType == FileChooserEnum.LOAD_DIALOG) fileChooser.setFileFilter
                (new FileNameExtensionFilter("JSON Files", "json", "JSON"));

        var frame = new JFrame();
        frame.setVisible(true);
        frame.toFront();
        frame.setAlwaysOnTop(true);
        frame.setVisible(false);

        int result = (fileChooserType == FileChooserEnum.LOAD_DIALOG) ? fileChooser.showOpenDialog(frame)
                : fileChooser.showSaveDialog(frame);
        frame.dispose();
        return (result == JFileChooser.APPROVE_OPTION) ? fileChooser.getSelectedFile() : null;
    }
}
