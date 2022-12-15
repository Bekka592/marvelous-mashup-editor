package marvelous_mashup.team29.editor.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConfigurationTypeEnumTest {
    @Test
    void getCorrespondingClassNameTest() {
        ConfigurationTypeEnum characterConfig = ConfigurationTypeEnum.CHARACTER;
        assertEquals("CharacterConfigContainer", characterConfig.getCorrespondingClassName());
    }

    @Test
    void getFileExtensionTest() {
        ConfigurationTypeEnum scenarioConfig = ConfigurationTypeEnum.SCENARIO;
        assertEquals(".scenario.json", scenarioConfig.getFileExtension());
    }
}