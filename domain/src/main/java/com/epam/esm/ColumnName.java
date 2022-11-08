package com.epam.esm;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ColumnName {

    //For Gift_Certificate Table
    public static final String GIFT_TABLE_NAME = "gift_certificate";
    public static final String GIFT_NAME = "name";
    public static final String GIFT_DESCRIPTION = "description";
    public static final String GIFT_PRICE = "price";
    public static final String GIFT_DURATION = "duration";
    public static final String GIFT_CREATE_DATE = "create_date";
    public static final String GIFT_LAST_UPDATE_DATE = "last_update_date";

    //For tag Table
    public static final String TAG_TABLE_NAME = "tag";
    public static final String TAG_NAME = "tag_name";

    //For user Table
    public static final String USER_TABLE_NAME = "user";
    public static final String USER_NAME = "name";

    //For Order table
    public static final String ORDER_TABLE_NAME = "orders";
    public static final String ORDER_USER_ID = "user_id";
    public static final String ORDER_PRICE = "price";
    public static final String ORDER_ORDER_DATE = "order_date";

    //GIFT_TAGS linking table
    public static final String GIFT_TAGS = "gift_tags";
    public static final String LINK_GIFT_ID = "gift_id";
    public static final String LINK_TAG_ID = "tag_id";

    //ORDER_GIFTS linking table
    public static final String ORDER_GIFTS = "order_gifts";
    public static final String ORDER_GIFTS_ORDER_ID = "order_id";
    public static final String ORDER_GIFTS_GIFT_ID = "gift_id";

}