package marvelous_mashup.team29.ui_util.options;

/**
 * The MarvelStyle creates many different styles, like a TextButton or a TextField.
 * While creating a (for e.g.) a TextButton, you can set the size using the ComponentSizeEnum.
 * If you simply want to change the size for e.g. all styles with the width COMPONENTS_WIDTH, you can change the integer value (e.g. COMPONENTS_WIDTH(240)).
 * If you want to add a new size, create a new Element in the ComponentSizeEnum with the constructor, right here in this Enum-class.
 */
public enum ComponentsSizeEnum {
    COMPONENT_WIDTH_SMALL(100),
    COMPONENT_WIDTH(230),
    COMPONENT_WIDTH_BIG(370),

    BORDER_SMALL(3),
    BORDER(5),
    BORDER_BIG(20),

    COMPONENT_HEIGHT(50),
    COMPONENT_HEIGHT_SMALL(10),

    TEXT_FIELD_HEIGHT_SMALL(30),
    TEXT_FIELD_HEIGHT_MEDIUM(40),
    TEXT_FIELD_WIDTH_SMALL(80),
    TEXT_FIELD_WIDTH_MEDIUM(120),
    TEXT_FIELD_WIDTH_BIG(305);

    private final int size;

    ComponentsSizeEnum(int size) {
        this.size = size;
    }

    public int getValue() {
        return size;
    }
}
