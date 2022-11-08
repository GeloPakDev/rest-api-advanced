package com.epam.esm.validator;

import com.epam.esm.GiftCertificate;
import com.epam.esm.Tag;
import com.epam.esm.exception.ExceptionResult;
import lombok.experimental.UtilityClass;

import java.util.Set;

import static com.epam.esm.exception.ExceptionIncorrectParameterMessageCodes.*;

@UtilityClass
public class GiftValidator extends EntityValidator {
    private final int MAX_LENGTH_NAME = 45;
    private final int MIN_LENGTH_NAME = 3;
    private final int MAX_LENGTH_DESCRIPTION = 300;
    private final int MAX_DURATION = 366;
    private final int MIN_DURATION = 1;

    public void validate(GiftCertificate giftCertificate, ExceptionResult er) {
        validateName(giftCertificate.getName(), er);
        validateDescription(giftCertificate.getDescription(), er);
        validatePrice(giftCertificate.getPrice(), er);
        validateDuration(giftCertificate.getDuration(), er);
        validateSetOfTags(giftCertificate.getTags(), er);
    }

    public void validateForUpdate(GiftCertificate gift, ExceptionResult er) {
        String name = gift.getName();
        if (name != null) {
            validateName(name, er);
        }

        String description = gift.getDescription();
        if (description != null) {
            validateDescription(description, er);
        }

        Double price = gift.getPrice();
        if (price != null) {
            validatePrice(price, er);
        }

        int duration = gift.getDuration();
        if (duration != 0) {
            validateDuration(duration, er);
        }
    }

    private void validateName(String name, ExceptionResult er) {
        if (name == null || name.length() < MIN_LENGTH_NAME || name.length() > MAX_LENGTH_NAME || isNotString(name)) {
            er.addException(BAD_GIFT_CERTIFICATE_NAME, name);
        }
    }

    private void validateDescription(String description, ExceptionResult er) {
        if (description == null || description.length() > MAX_LENGTH_DESCRIPTION || isNotString(description)) {
            er.addException(BAD_GIFT_CERTIFICATE_DESCRIPTION, description);
        }
    }

    private void validatePrice(Double price, ExceptionResult er) {
        if (price == null || price < 0) {
            er.addException(BAD_GIFT_CERTIFICATE_PRICE, price);
        }
    }

    private void validateDuration(int duration, ExceptionResult er) {
        if (duration < MIN_DURATION || duration > MAX_DURATION) {
            er.addException(BAD_GIFT_CERTIFICATE_DURATION, duration);
        }
    }

    public void validateSetOfTags(Set<Tag> tags, ExceptionResult er) {
        if (tags == null) return;
        for (Tag tag : tags) {
            TagValidator.validate(tag, er);
        }
    }
}