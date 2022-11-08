import com.epam.esm.GiftCertificate;
import com.epam.esm.Tag;
import com.epam.esm.exception.ExceptionResult;
import com.epam.esm.validator.GiftValidator;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GiftValidatorTest {
    private static final Set<Tag> INCORRECT_TAGS = Stream.of(new Tag(1L, "1"), new Tag(2L, "2")).collect(Collectors.toSet());
    private static final Set<Tag> CORRECT_TAGS = Stream.of(new Tag(1L, "yellow"), new Tag(2L, "blue")).collect(Collectors.toSet());

    private static final GiftCertificate INCORRECT_GIFT_CERTIFICATE = new GiftCertificate(1L, " ",
            " ", 10.115, 1, LocalDateTime.parse("2020-08-29T06:12:15.156"),
            LocalDateTime.parse("2020-08-29T06:12:15.156"), INCORRECT_TAGS);

    private static final GiftCertificate CORRECT_GIFT_CERTIFICATE = new GiftCertificate(23L, "giftCertificate",
            "description", 10.1, 12, LocalDateTime.parse("2020-08-29T06:12:15.156"),
            LocalDateTime.parse("2020-08-29T06:12:15.156"), CORRECT_TAGS);


    @Test
    void testIncorrectData() {
        ExceptionResult exceptionResult = new ExceptionResult();
        GiftValidator.validate(INCORRECT_GIFT_CERTIFICATE, exceptionResult);
        assertFalse(exceptionResult.getExceptionMessages().isEmpty());
    }

    @Test
    void testCorrectData() {
        ExceptionResult exceptionResult = new ExceptionResult();
        GiftValidator.validate(CORRECT_GIFT_CERTIFICATE, exceptionResult);
        assertTrue(exceptionResult.getExceptionMessages().isEmpty());
    }

    @Test
    void testCorrectTags() {
        ExceptionResult exceptionResult = new ExceptionResult();
        GiftValidator.validateSetOfTags(CORRECT_TAGS, exceptionResult);
        assertTrue(exceptionResult.getExceptionMessages().isEmpty());
    }

    @Test
    void testIncorrectTags() {
        ExceptionResult exceptionResult = new ExceptionResult();
        GiftValidator.validateSetOfTags(INCORRECT_TAGS, exceptionResult);
        assertFalse(exceptionResult.getExceptionMessages().isEmpty());
    }
}
