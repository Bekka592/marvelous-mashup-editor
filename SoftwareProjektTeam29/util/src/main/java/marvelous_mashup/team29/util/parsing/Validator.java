package marvelous_mashup.team29.util.parsing;

import com.badlogic.gdx.Gdx;
import jakarta.json.stream.JsonParser;
import marvelous_mashup.team29.util.asset_loader.AssetFinder;
import marvelous_mashup.team29.util.asset_loader.Text;
import org.leadpony.justify.api.JsonSchema;
import org.leadpony.justify.api.JsonValidationService;
import org.leadpony.justify.api.ProblemHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
/**
 * Implements a easy to use service to test and validate json against the official marvelous mashup schema
 */
public class Validator {
    private static Validator self;

    private final JsonValidationService service;
    private final JsonSchema marvelousMashupSchema;
    private final ProblemHandler errorHandler;
    private final ProblemHandler printHandler;

    private static final String TEST_SCHEMA_PATH = "json/schema/marvelousmashupschema.json";


    private Validator(InputStream stream) {
        service = JsonValidationService.newInstance();

        marvelousMashupSchema = service.readSchema(stream);

        // throwing an exception when we have a problem is enough because we only wanna know if we have a problem and stop
        errorHandler = ProblemHandler.throwing();
        printHandler = service.createProblemPrinter(s -> Gdx.app.log("Validator", s));
    }

    /**
     * @return A Validator instance to validate json
     */
    public static Validator getInstance() {
        if (self == null) {
            var stream = new ByteArrayInputStream(((Text) AssetFinder.getAssetFinder()
                    .get(TEST_SCHEMA_PATH)).getString().getBytes(StandardCharsets.UTF_8));
            self = new Validator(stream);
        }
        return self;
    }

    /**
     * @return A Validator instance for test purposes only
     */
    public static Validator getTestInstance() {
        File file = new File(TEST_SCHEMA_PATH);
        try {
            return new Validator(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Test if a json string is valid against the official schema
     *
     * @param json JSON formatted String to validate
     * @return IS the input a valid json structure
     */
    public static boolean isValid(String json) {
        var validator = getInstance();
        return validator.validate(json);
    }

    /**
     * Validates an input against the schema
     *
     * @param json JSON formatted String to validate
     * @return IS the input a valid json structure
     */
    public boolean validate(String json) {
        return validate(new StringReader(json));
    }

    /**
     * Validates an input against the schema
     *
     * @param in JSON File to validate
     * @return IS the input a valid json structure
     */
    public boolean validate(Reader in) {
        return validateWithHandler(in, errorHandler);
    }

    /**
     * Validates an input and prints all encountered problems to System.out. The methode is intended
     * for debug
     *
     * @param json JSON formatted String to validate
     */
    public void validateButPrintProblems(String json) {
        validateWithHandler(new StringReader(json), printHandler);
    }

    /**
     * Validates an input and prints all encountered problems to System.out. The methode is intended
     * for debug
     *
     * @param in JSON File to validate
     */
    public void validateButPrintProblems(Reader in) {
        validateWithHandler(in, printHandler);
    }

    private boolean validateWithHandler(Reader in, ProblemHandler handler) {
        try (JsonParser parser = service.createParser(in, marvelousMashupSchema, handler)) {
            while (parser.hasNext()) {
                parser.next();
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

}
