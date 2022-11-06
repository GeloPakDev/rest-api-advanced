package com.epam.esm.util;

public final class QueryParam {
    private QueryParam() {
    }

    //Settings Parameters
    public static final String JSON = "application/json";
    public static final String LOCALHOST = "http://localhost:8080";

    //Request Parameters
    public static final String ORDER_ID = "orderId";
    public static final String REQUEST_ID = "id";
    public static final String TAG_NAME = "tagName";
    public static final String PAGE = "page";
    public static final String SIZE = "size";

    public static final int DEFAULT_MIN_PAGE = 0;
    public static final int DEFAULT_MIN_SIZE = 5;

}