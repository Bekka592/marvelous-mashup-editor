package marvelous_mashup.team29.ui_util;

/**
 * All types of GUI based applications that are part of the Marvelous Mashup project.
 */
public enum ApplicationTypeEnum {
    EDITOR("Editor");

    private final String name;

    ApplicationTypeEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
