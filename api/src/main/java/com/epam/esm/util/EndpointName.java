package com.epam.esm.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EndpointName {

    public static final String BASE_URL = "/api";
    public static final String TAGS = "/tags";
    public static final String POPULAR_TAG = "/popular";
    public static final String GIFT_CERTIFICATES = "/certificates";
    public static final String ORDERS = "/orders";
    public static final String USERS = "/users";
    public static final String FILTER = "/filter";
    public static final String ID = "/{id}";
    public static final String USER_ORDER_ID = "/{orderId}";
}