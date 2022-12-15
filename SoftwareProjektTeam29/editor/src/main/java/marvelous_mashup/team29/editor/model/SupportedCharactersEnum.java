package marvelous_mashup.team29.editor.model;

/**
 * Data structure to access all of the 24 supported characters along with their characterIds for the
 * {@link marvelous_mashup.team29.editor.gui.config_screens.CharacterConfigScreen}.
 */
public enum SupportedCharactersEnum {
    ROCKET_RACCOON("Rocket Raccoon", 1),
    QUICKSILVER("Quicksilver", 2),
    HULK("Hulk", 3),
    BLACK_WIDOW("Black Widow", 4),
    HAWKEYE("Hawkeye", 5),
    CAPTAIN_AMERICA("Captain America", 6),
    SPIDERMAN("Spiderman", 7),
    DR_STRANGE("Dr. Strange", 8),
    IRON_MAN("Iron Man", 9),
    BLACK_PANTHER("Black Panther", 10),
    THOR("Thor", 11),
    CAPTAIN_MARVEL("Captain Marvel", 12),
    GROOT("Groot", 13),
    STARLORD("Starlord", 14),
    GAMORA("Gamora", 15),
    ANT_MAN("Ant Man", 16),
    VISION("Vision", 17),
    DEADPOOL("Deadpool", 18),
    LOKI("Loki", 19),
    SILVER_SURFER("Silver Surfer", 20),
    MANTIS("Mantis", 21),
    GHOST_RIDER("Ghost Rider", 22),
    JESSICA_JONES("Jessica Jones", 23),
    SCARLET_WITCH("Scarlet Witch", 24);

    private final String name;
    private final int id;

    SupportedCharactersEnum(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
