package com.hvt.ultimatespy.utils;

public class Constants {

    public static final String ROUTE_AUTHENTICATE = "/api/v1/authenticate";
    public static final String ROUTE_USER = "/api/v1/user";
    public static final String ROUTE_USER_ID = "/api/v1/user/{id}";
    public static final String ROUTE_USER_ID_POST = "/api/v1/user/{id}/post";
    public static final String ROUTE_USER_CHANGE_PASSWORD = "/api/v1/user/{id}/change-password";
    public static final String ROUTE_USER_FORGOT_PASSWORD = "/api/v1/forgot-password/{mail}";
    public static final String ROUTE_USER_RESET_PASSWORD = "/api/v1/reset-password";
    public static final String ROUTE_USER_CONFIRM_ID = "/api/v1/user/{userId}/confirm/{id}";
    public static final String ROUTE_USER_SUBSCRIPTION = "/api/v1/user/{id}/subscription";
    public static final String ROUTE_POST_FACEBOOK = "/api/v1/facebook-post";
    public static final String ROUTE_POST_FACEBOOK_ID = "/api/v1/facebook-post/{id}";
    public static final String ROUTE_POST_FACEBOOK_SEARCH = "/api/v1/search/facebook-post";
    public static final String ROUTE_PAYMENT = "/api/v1/payment";
    public static final String ROUTE_PAYMENT_ID = "/api/v1/payment/{id}";
    public static final String ROUTE_SUBSCRIPTION_PLAN = "/api/v1/subscription-plan";
    public static final String ROUTE_SUBSCRIBER_EMAIL = "/api/v1/subscriber-email";
    public static final String ROUTE_PAYPAL_SUBSCRIPTION = "/api/v1/paypal/subscription";
    public static final String ROUTE_PAYPAL_SUBSCRIPTION_ID = "/api/v1/paypal/subscription/{id}";
    public static final String ROUTE_PROMOTION_CODE = "/api/v1/promotion/code/{code}";
    public static final String ROUTE_REFERRAL = "/api/v1/referral";
    public static final String ROUTE_REFERRER = "/api/v1/referrer";
    public static final String ROUTE_REFERRER_ID = "/api/v1/referrer/{id}";
    public static final String ROUTE_REFERRAL_SUMMARY = "/api/v1/referral-summary";
    public static final String ROUTE_REFERRAL_REQUEST_PAYOUT = "/api/v1/referral-request-payout";
    public static final String ROUTE_REFERRAL_REQUEST_PAYOUT_REFERRER_ID = "/api/v1/referral-request-payout/{referrerId}";

    public static final String ROUTE_ADMIN = "/api/v1/admin";

    public static final String USER_ID_PREFIX = "usr_";
    public static final String PAYMENT_ID_PREFIX = "pay_";

    public static final String X_USER_ID = "X-User-Id";

    public static final String ID = "id";
    public static final String FROM_DATE = "fromDate";
    public static final String TO_DATE = "toDate";
    public static final String PAGE = "page";
    public static final String PAGE_SIZE = "pageSize";
    public static final String PIXEL_ID = "pixelId";
    public static final String FACEBOOK_PAGE_ID = "facebookPageId";
    public static final String KEYWORD = "keyword";
    public static final String CATEGORY = "category";
    public static final String TYPE = "type";
    public static final String COUNTRY = "country";
    public static final String LANGUAGE = "language";
    public static final String WEBSITE = "website";
    public static final String PLATFORM = "platform";
    public static final String MIN_LIKES = "minLikes";
    public static final String MAX_LIKES = "maxLikes";
    public static final String MIN_COMMENTS = "minComments";
    public static final String MAX_COMMENTS = "maxComments";
    public static final String FILTER = "filter";
    public static final String SORT = "sort";

    public static final String BLANK = "";
    public static final String NAME = "name";
    public static final String SERIES = "series";
    public static final String VALUE = "value";

}
