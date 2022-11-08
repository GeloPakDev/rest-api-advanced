package com.epam.esm.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class QueryParam {

    //Settings Parameters
    public static final String JSON = "application/json";

    //Request Parameters
    public static final String ORDER_ID = "orderId";
    public static final String REQUEST_ID = "id";
    public static final String TAG_NAME = "tagName";
    public static final String PAGE = "page";
    public static final String SIZE = "size";
    //HATEOAS Rel parameters
    public static final String CREATE = "create";
    public static final String DELETE = "delete";
    public static final String UPDATE = "update";
    public static final String FIND_BY_ID = "findById";
    public static final String FIND_BY_NAME = "findByName";
    public static final String USER_ORDERS = "userOrders";
    public static final String USER_ORDER_BY_ID = "userOrderById";
    public static final String POPULAR = "popular";



    public static final int DEFAULT_MIN_PAGE = 0;
    public static final int DEFAULT_MIN_SIZE = 5;

}