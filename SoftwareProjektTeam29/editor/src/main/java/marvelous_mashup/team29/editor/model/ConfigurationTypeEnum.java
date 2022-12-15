package marvelous_mashup.team29.editor.model;

/**
 * All possible types of configurations that can be edited using the Editor.
 */
public enum ConfigurationTypeEnum {
    CHARACTER("CharacterConfigContainer", ".character.json"),
    MATCH("MatchConfig", ".game.json"),
    SCENARIO("ScenarioConfig", ".scenario.json");

    private final String correspondingClassName;
    private final String fileExtension;

    ConfigurationTypeEnum(String correspondingClassName, String fileExtension) {
        this.correspondingClassName = correspondingClassName;
        this.fileExtension = fileExtension;
    }

    public String getCorrespondingClassName() {
        return correspondingClassName;
    }

    public String getFileExtension() {
        return fileExtension;
    }
}
