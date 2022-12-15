package marvelous_mashup.team29.editor.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SupportedCharactersEnumTest {
    @Test
    void getNameTest() {
        SupportedCharactersEnum groot = SupportedCharactersEnum.GROOT;
        assertEquals("Groot", groot.getName());

        SupportedCharactersEnum drStrange = SupportedCharactersEnum.DR_STRANGE;
        assertEquals("Dr. Strange", drStrange.getName());
    }

    @Test
    void getIdTest() {
        SupportedCharactersEnum jessicaJones = SupportedCharactersEnum.JESSICA_JONES;
        assertEquals(23, jessicaJones.getId());

        SupportedCharactersEnum blackWidow = SupportedCharactersEnum.BLACK_WIDOW;
        assertEquals(4, blackWidow.getId());
    }
}