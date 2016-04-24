package com.app.dabbawalashop.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sonia on 18/8/15.
 */
public class AppConstant {


    public static final String APPLICATION_ID = "ANDSH1002";
    public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final String GCM_ID = "187896396066";
    public static final String COUNTRY = "India";
    public static final String DEFAULT_LOCALE = "en";

    public interface BUNDLE_KEY {

        String OrderPlacedTo= "OrderPlacedTo";
        String OrderPlacedBy= "OrderPlacedBy";
        String OrderQuotedAmount= "OrderQuotedAmount";
        String OrderDeliveryType= "OrderDeliveryType";
        String OrderPaymentMethod= "OrderPaymentMethod";
        String PaymentStatus= "PaymentStatus";
        String OrderCreationTimestamp= "OrderCreationTimestamp";
        String OrderDeliveryAddressIdentifier= "OrderDeliveryAddressIdentifier";
        String DeliveryDates= "DeliveryDates";
        String OrderSubscriptionType= "OrderSubscriptionType";
        String AmountAdjustmentDone= "AmountAdjustmentDone";
        String OrderBalanceAmount= "OrderBalanceAmount";
        String OrderInvoiceAmount= "OrderInvoiceAmount";
        String OrderCancellationReason= "OrderCancellationReason";
        String ToOrderStatus= "ToOrderStatus";
        String IS_ADD_ASSOCIATE_PRODUCT_HOME = "IS_ADD_ASSOCIATE_PRODUCT_HOME";
        String SKU_ID = "skuid";
        String SHOP_ID = "shopid";
        String DELIVERY_PERSON_MOBILE = "shopid";
        String ENGLISH_NAME = "english_name";
        String MARATHI_NAME = "MARATHI_NAME";
        String DES = "DES";
        String UNIT = "UNIT";
        String DAILY_PRICE = "price";
        String WEEKLY_PRICE = "WEEKLY_PRICE";
        String MONTHLY_PRICE = "MONTHLY_PRICE";
        String IMAGE = "image";
        String ORDER_ID = "ORDER_ID";
        String ORDER_STATUS = "ORDER_STATUS";
        String DELIVERY_DATE = "DELIVERY_DATE";
        String PRODUCT_STATUS = "PRODUCT_STATUS";
        String DELIVERY_STATUS = "DELIVERY_STATUS";
        String TODAYS_DELIVERY_DATE_FLAG = "TODAYS_DELIVERY_DATE_FLAG";
    }

    public interface PreferenceKeeperNames {
        String LOGIN = "user_login";
        String USER_ID = "user_id";
        String USER_TYPE = "user_type";
        String GCM_REG_ID = "gcm_id";
        String ASSOCIATED_SHOP_ID = "ASSOCIATED_SHOP_ID";
        String LOCALE = "LOCALE";
    }

    public interface UserType {
        String SELLER_HUB_TYPE = "SellerHub";
        String SHOP_TYPE = "Shop";
        String DELIVERY_PERSON_TYPE = "DeliveryPerson";
        String ADMIN_TYPE = "Admin";
        String CUSTOMER_TYPE = "Customer";
    }

    public interface CITY {
        String CITY_PUNE = "Pune";
        String CITY_MUMBAI = "Mumbai";
        String CITY_HYD = "Hyderabad";
        String CITY_BLR = "Banglore";
    }

    public interface LANGUAGE {
        String LANGUAGE_ENGLISH = "English";
        String LANGUAGE_MARATHI = "Marathi";
        String LANGUAGE_HINDI = "Hindi";
    }

    public static List<String> fetchLanguages() {
        final List<String> languageList = new ArrayList<>();
        languageList.add(LANGUAGE.LANGUAGE_ENGLISH);
        languageList.add(LANGUAGE.LANGUAGE_HINDI);
        languageList.add(LANGUAGE.LANGUAGE_MARATHI);
        return languageList;
    }



    public interface STATE {
        String STATE_MH = "Maharashtra";
        String STATE_KR = "Karnataka";
        String STATE_TL = "Telangana";
    }

    public static List<String> fetchState() {
        final List<String> stateList = new ArrayList<>();
        stateList.add(STATE.STATE_MH);
        stateList.add(STATE.STATE_KR);
        stateList.add(STATE.STATE_TL);
        return stateList;
    }



    public static List<String> fetchCities(String state)
    {
        final List<String> cityList = new ArrayList<>();
        if(state.equals(STATE.STATE_KR)) {
            cityList.add(CITY.CITY_BLR);
        }
        if(state.equals(STATE.STATE_MH)) {
            cityList.add(CITY.CITY_PUNE);
            cityList.add(CITY.CITY_MUMBAI);
        }
        if(state.equals(STATE.STATE_TL)) {
            cityList.add(CITY.CITY_HYD);
        }
        return cityList;
    }

    public interface STATUS {
        String STATUS_ALL = "ALL";
        String STATUS_APPROVED = "Approved";
        String STATUS_VERIFIED = "Verified";
        String STATUS_REGISTERED = "Registered";
        String STATUS_REJECTED = "Rejected";
        String STATUS_BLOCKED = "Blocked";
        String STATUS_CONFIRMED = "Confirmed";
        String STATUS_ORDERED = "Ordered";
        String STATUS_PREPARED = "Prepared";
        String STATUS_DISPATCHED = "Dispatched";
        String STATUS_DELIVERED = "Delivered";
        String STATUS_CLOSED = "Closed";
        String STATUS_DISPUTED = "Disputed";
        String STATUS_CANCELLED = "Cancelled";
        String STATUS_SUBSCRIBED = "Subscribed";
        String STATUS_INCREASED = "Increased";
        String STATUS_DECREASED = "Decreased";
        String STATUS_UNKNOWN = "Unknown";

    }

}
