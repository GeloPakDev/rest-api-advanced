package com.epam.esm.validator;

import com.epam.esm.Tag;
import com.epam.esm.exception.ExceptionResult;
import lombok.experimental.UtilityClass;

import static com.epam.esm.exception.ExceptionIncorrectParameterMessageCodes.BAD_TAG_NAME;
import static com.epam.esm.validator.EntityValidator.isNotString;


@UtilityClass
public class TagValidator {
    private static final int MAX_LENGTH_NAME = 20;
    private static final int MIN_LENGTH_NAME = 3;


    public void validate(Tag tag, ExceptionResult er) {
        validateName(tag.getName(), er);
    }

    public void validateName(String name, ExceptionResult er) {
        if (name == null || name.length() < MIN_LENGTH_NAME || name.length() > MAX_LENGTH_NAME || isNotString(name)) {
            er.addException(BAD_TAG_NAME, name);
        }
    }
}
