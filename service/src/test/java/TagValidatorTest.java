import com.epam.esm.Tag;
import com.epam.esm.exception.ExceptionResult;
import com.epam.esm.validator.TagValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TagValidatorTest {
    private static final String CORRECT_NAME = "tagName";
    public static final String INCORRECT_NUMERIC_NAME = "tagName122";
    private static final String INCORRECT_LENGTH_NAME = "to";

    @Test
    void testIncorrectLengthName() {
        ExceptionResult exceptionResult = new ExceptionResult();
        Tag tag = new Tag(1L, INCORRECT_LENGTH_NAME);
        TagValidator.validate(tag, exceptionResult);
        assertFalse(exceptionResult.getExceptionMessages().isEmpty());
    }

    @Test
    void testIncorrectNumericName() {
        ExceptionResult exceptionResult = new ExceptionResult();
        Tag tag = new Tag(1L, INCORRECT_NUMERIC_NAME);
        TagValidator.validate(tag, exceptionResult);
        assertFalse(exceptionResult.getExceptionMessages().isEmpty());
    }

    @Test
    void testCorrectTag() {
        ExceptionResult exceptionResult = new ExceptionResult();
        Tag tag = new Tag(1L, CORRECT_NAME);
        TagValidator.validate(tag, exceptionResult);
        assertTrue(exceptionResult.getExceptionMessages().isEmpty());
    }

}
