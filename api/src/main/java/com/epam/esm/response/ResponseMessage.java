package com.epam.esm.response;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResponseMessage {
    public static final String SUCCESSFULLY_CREATED = "Successfully created data!";
    public static final String SUCCESSFULLY_RECEIVED = "Successfully received data!";
    public static final String SUCCESSFULLY_UPDATED = "Successfully updated GiftCertificate with id ";
    public static final String SUCCESSFULLY_DELETED = "Successfully deleted GiftCertificate with id ";
    public static final String SUCCESSFULLY_DELETED_TAG = "Successfully deleted Tag with id ";
}