package com.app.dabbawalashop.network;

import com.app.dabbawalashop.activity.HomeActivity;
import com.app.dabbawalashop.api.output.AssociatedProductCategoryResponse;
import com.app.dabbawalashop.api.output.AssociatedProductResponse;
import com.app.dabbawalashop.api.output.AssociatedShopIdResponse;
import com.app.dabbawalashop.api.output.AssociatedShopsResponse;
import com.app.dabbawalashop.api.output.CommonResponse;
import com.app.dabbawalashop.api.output.DeliveryDetailResponse;
import com.app.dabbawalashop.api.output.DeliveryLocationResponse;
import com.app.dabbawalashop.api.output.DeliveryPersonResponse;
import com.app.dabbawalashop.api.output.LoginResponse;
import com.app.dabbawalashop.api.output.MyOrderDetailResponse;
import com.app.dabbawalashop.api.output.OrderUnitResponse;
import com.app.dabbawalashop.api.output.ProductCategoryResponse;
import com.app.dabbawalashop.api.output.ProductResponse;
import com.app.dabbawalashop.api.output.RecentOrderResponse;
import com.app.dabbawalashop.api.output.SellerHubProfileResponse;
import com.app.dabbawalashop.api.output.ShopCategoryResponse;
import com.app.dabbawalashop.api.output.ShopOperationalTimeResponse;
import com.app.dabbawalashop.api.output.ShopProfile;
import com.app.dabbawalashop.api.output.ShopProfileResponse;
import com.app.dabbawalashop.api.output.ShopReferenceDataResponse;
import com.app.dabbawalashop.api.output.SupportedIdTypeResponse;
import com.app.dabbawalashop.api.output.ViewAvailableProductResponse;
import com.app.dabbawalashop.constant.AppConstant;
import com.app.dabbawalashop.utils.Logger;
import com.app.dabbawalashop.utils.PreferenceKeeper;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/*
 * AppRequestBuilder : All api access code
 */
public class AppRequestBuilder {

    public static String TAG = HomeActivity.class.getSimpleName();
    private static final String BASE_URL = "http://stupendoustanuj.co.nf/Dabbawala";
    public static String USER_TYPE = "";
    public static String USER_ID = "";

    // http://stupendoustanuj.co.nf/Dabbawala/ Verify_Mobile_Number.php

    static{
        USER_TYPE = PreferenceKeeper.getInstance().getUserType();
        USER_ID = PreferenceKeeper.getInstance().getUserId();
    }

    private static void setUserHeader(Map<String, String> map) {
        map.put("applicationId", AppConstant.APPLICATION_ID);
        USER_ID = PreferenceKeeper.getInstance().getUserId();
        if((USER_ID.equals("")) || (USER_ID == null) || (USER_ID.equals("Admin"))) {
            map.put("userId", "Admin");
        }
        else {
            map.put("userId", USER_ID);
        }
        USER_TYPE = PreferenceKeeper.getInstance().getUserType();


    }

    private static String setRequestBody(Map<String, String> map) {
        Gson gson = new Gson();
        String json = gson.toJson(map, LinkedHashMap.class);
        Logger.INFO("REQUEST", "JSON : " + json);
        return json;
    }

    // http://stupendoustanuj.co.nf/Dabbawala/User_Login.php\
    public static AppHttpRequest loginAPI(String userId, String userType, String password, AppResponseListener<LoginResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/User_Login.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("userType", userType);
        map.put("userId", userId);
        map.put("password", password);
        //TODO IP Address to be put here
        map.put("IPAddress", "12.33.534.45");
        //TODO Device ID to be put here
        //map.put("deviceId", PreferenceKeeper.getInstance().getDeviceInfo());
        map.put("deviceId", "Test");

        request.addParam("input", setRequestBody(map));
        return request;
    }


    // http://stupendoustanuj.co.nf/Dabbawala/User_Login.php\
    public static AppHttpRequest updateGCMIdAPI(String GCMID,AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Update_GCMID.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("userType", USER_TYPE);
        map.put("GCMID", GCMID);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    // http://stupendoustanuj.co.nf/Dabbawala/User_Login.php\
    public static AppHttpRequest changePasswordAPI(String oldPassword,String newPassword, AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Change_Password.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("userType", USER_TYPE);
        map.put("oldPassword", oldPassword);
        map.put("newPassword", newPassword);
        request.addParam("input", setRequestBody(map));
        return request;
    }


    // http://stupendoustanuj.co.nf/Dabbawala/User_Login.php\
    public static AppHttpRequest resetPasswordAPI(String emailId,String mobileNumber, String userId, String userType, AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Forget_Password.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("applicationId", AppConstant.APPLICATION_ID);
        map.put("userId", userId);
        map.put("userType", userType);
        map.put("emailId", emailId);
        map.put("mobileNumber", mobileNumber);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    // http://stupendoustanuj.co.nf/Dabbawala/Fetch_All_Orders.php

    public static AppHttpRequest fetchMyOrderDetailAPI(String fromDate, String toDate,String shopIdValue,String orderStatusValue, AppResponseListener<MyOrderDetailResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_All_Orders.php", appResponseListener);

        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("userType", USER_TYPE);
        map.put("fromDate", fromDate);
        map.put("toDate", toDate);
        map.put("orderStatus", orderStatusValue);
        map.put("shopId", shopIdValue);
        request.addParam("input", setRequestBody(map));
        return request;
    }


    public static AppHttpRequest fetchTodaysDeliveryAPI(String deliveryDate,String deliveryStatus,String shopId, AppResponseListener<DeliveryDetailResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_Todays_Delivery.php", appResponseListener);

        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("userType", USER_TYPE);
        map.put("deliveryDate", deliveryDate);
        map.put("deliveryStatus", deliveryStatus);
        map.put("shopId", shopId);
        request.addParam("input", setRequestBody(map));
        return request;
    }


    // http://stupendoustanuj.co.nf/Dabbawala/Fetch_All_Orders.php

    public static AppHttpRequest fetchOrderDetailsAPI(String orderId, String orderStatus, AppResponseListener<MyOrderDetailResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_Order_Details.php", appResponseListener);

        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("orderId", orderId);
        map.put("orderStatus", orderStatus);
        map.put("userType", USER_TYPE);
        request.addParam("input", setRequestBody(map));
        return request;
    }


    // http://stupendoustanuj.co.nf/Dabbawala/Fetch_All_Orders.php

    public static AppHttpRequest fetchDeliveryDetailAPI(String orderId, String deliveryDate, AppResponseListener<DeliveryDetailResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_Delivery_Details.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("orderId", orderId);
        map.put("deliveryDate", deliveryDate);
        map.put("deliveryStatus", "Unknown");
        map.put("userType", USER_TYPE);
        request.addParam("input", setRequestBody(map));
        return request;
    }


    // http://stupendoustanuj.co.nf/Dabbawala/ Fetch_Order_Details.php

    public static AppHttpRequest fetchRecentTracOrderAPI(String orderId, String orderStatus, AppResponseListener<RecentOrderResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_Order_Details.php", appResponseListener);

        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("orderId", orderId);
        map.put("orderStatus", orderStatus);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    // http://stupendoustanuj.co.nf/Dabbawala/Fetch_Shop_Profile.php
    public static AppHttpRequest fetchShopKeeperProfileApi(AppResponseListener<ShopProfileResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_Shop_Profile.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("shopId", USER_ID);
        request.addParam("input", setRequestBody(map));
        return request;
    }


    // http://stupendoustanuj.co.nf/Dabbawala/Fetch_Shop_Profile.php
    public static AppHttpRequest fetchShopKeeperProfileApi(String shopId,AppResponseListener<ShopProfileResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_Shop_Profile.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("shopId", shopId);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    //http://stupendoustanuj.co.nf/Dabbawala/Update_Shop_Profile.php
    public static AppHttpRequest updateShopKeeperProfileApi(String shopId, String shopName, String shopAddress,
                                                            String city, String pincode, String state,
                                                            String landmark, String ownerNumber,
                                                            String supportNumber, String procesingNumber,
                                                            String emailId, String minimumAccOrder, String deliveryCharge,
                                                            String deliveryType, String deliveryMethod, AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Update_Shop_Profile.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("shopId", shopId);
        map.put("shopName", shopName);
        map.put("shopAddress", shopAddress);
        map.put("ShopAddressCity", city);
        map.put("shopAddressPincode", pincode);
        map.put("shopAddressState", state);
        map.put("shopAddressCountry", "India");
        map.put("shopAddressLandmark", landmark);
        map.put("shopOwnerContactNumber", ownerNumber);
        map.put("shopSupportContactNumber", supportNumber);
        map.put("shopOrderProcessingContactNumber", procesingNumber);
        map.put("shopEmailId", emailId);
        map.put("shopMinimumAcceptedOrder", minimumAccOrder);
        map.put("shopDeliveryCharges", deliveryCharge);
        map.put("shopDeliveryTypeSupported", deliveryType);
        map.put("shopPaymentMethodSupported", deliveryMethod);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    public static AppHttpRequest addAShopAPI(ShopProfile shop, AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Add_A_Shop.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("shopName", shop.getShopName());
        map.put("shopRegistrationStatus", shop.getShopRegistrationStatus());
        map.put("shopOwnerName", shop.getShopOwnerName());
        map.put("shopDescription", shop.getShopDescription());
        map.put("shopAddress", shop.getShopAddress());
        map.put("shopAddressAreaSector", shop.getShopAddressAreaSector());
        map.put("shopAddressCity", shop.getShopAddressCity());
        map.put("shopAddressPincode", shop.getShopAddressPincode());
        map.put("shopAddressState", shop.getShopAddressState());
        map.put("shopAddressCountry", shop.getShopAddressCountry());
        map.put("shopAddressLandmark", shop.getShopAddressLandmark());
        map.put("shopOwnerContactNumber", shop.getShopOwnerContactNumber());
        map.put("shopSupportContactNumber", shop.getShopSupportContactNumber());
        map.put("shopOrderProcessingContactNumber", shop.getShopOrderProcessingContactNumber());
        map.put("shopEmailId", shop.getShopEmailId());
        map.put("shopMinimumAcceptedOrder", shop.getShopMinimumAcceptedOrder());
        map.put("shopDeliveryCharges", shop.getShopDeliveryCharges());
        map.put("shopOwnerIDType", shop.getShopOwnerIDType());
        map.put("shopOwnerIDNumber", shop.getShopOwnerIDNumber());
        map.put("shopDeliveryTypeSupported", shop.getShopDeliveryTypeSupported());
        map.put("shopPaymentMethodSupported", shop.getShopPaymentMethodSupported());
        request.addParam("input", setRequestBody(map));
        return request;
    }


    //http://stupendoustanuj.co.nf/Dabbawala/Update_Shop_Profile.php
    public static AppHttpRequest updateSellerHubProfileAPI(String mobileNumber, String supportNumber,
                                                            String emailId, AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Update_SellerHub_Profile.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("sellerHubMobileNumber", mobileNumber);
        map.put("sellerHubSupportContactNumber", supportNumber);
        map.put("sellerHubEmailID", emailId);
        request.addParam("input", setRequestBody(map));
        return request;
    }


    // http://stupendoustanuj.co.nf/Dabbawala/Fetch_SellerHub_Profile.php

    public static AppHttpRequest updateOrderStatusAPI(String orderId,String fromOrderStatus, String toOrderStatus, String additionalField, AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Update_Order_Status.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("orderId", orderId);
        map.put("toOrderStatus", toOrderStatus);
        map.put("fromOrderStatus", fromOrderStatus);
        if(toOrderStatus.equals("Subscribed"))
            map.put("orderInvoiceAmount", additionalField);
        if(toOrderStatus.equals("Cancelled"))
            map.put("orderCancellationReason", additionalField);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    // http://stupendoustanuj.co.nf/Dabbawala/Update_Delivery_Status.php

    public static AppHttpRequest updateDeliveryStatusAPI(String orderId,String fromDeliveryStatus, String toDeliveryStatus, String deliveryDate, String additionalField, AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Update_Delivery_Status.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("orderId", orderId);
        map.put("deliveryDate", deliveryDate);
        map.put("toDeliveryStatus", toDeliveryStatus);
        map.put("fromDeliveryStatus", fromDeliveryStatus);
        if(toDeliveryStatus.equals("Closed"))
            map.put("amountAdjustmentDone", additionalField);
        if(toDeliveryStatus.equals("Prepared"))
            map.put("orderBeingShippedBy", additionalField);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    // http://stupendoustanuj.co.nf/Dabbawala/Update_Order_Status.php

    public static AppHttpRequest fetchSellerHubProfileAPI(AppResponseListener<SellerHubProfileResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_SellerHub_Profile.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    /// http://stupendoustanuj.co.nf/Dabbawala/Fetch_Associated_Products.php

    public static AppHttpRequest fetchAssociatedProductListAPI(String shopId,String productStatus,AppResponseListener<AssociatedProductResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_Associated_Products.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("shopId", shopId);
        map.put("productCategoryName", "ALL");
        map.put("shopCategoryName", "ALL");
        map.put("productStatus", productStatus);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    // http://stupendoustanuj.co.nf/Dabbawala/Deassociate_A_Product.php
    public static AppHttpRequest deassociatedProductAPI(String shopId, String productSKUID, AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Deassociate_A_Product.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("shopId", shopId);
        map.put("productSKUID", productSKUID);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    // http://stupendoustanuj.co.nf/Dabbawala/Deassociate_A_Product.php
    public static AppHttpRequest removeShopOperationalTimeAPI(String shopId, String closingDate, String shopCategory, String productCategory, AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Remove_Shop_Timings.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("shopId", shopId);
        map.put("closingDate", closingDate);
        map.put("shopCategory", shopCategory);
        map.put("productCategory", productCategory);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    // http://stupendoustanuj.co.nf/Dabbawala/Deassociate_A_Product.php
    public static AppHttpRequest removeAssociatedProductCategoryAPI(String shopId, String shopCategory, String productCategory, AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Deassociate_Product_Category.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("shopId", shopId);
        map.put("shopCategory", shopCategory);
        map.put("productCategory", productCategory);
        request.addParam("input", setRequestBody(map));
        return request;
    }


    ///Fetch_All_Shop_Categories
    // http://stupendoustanuj.co.nf/Dabbawala/Fetch_All_Shop_Categories.php

    public static AppHttpRequest fetchAllShopCategoryApi(AppResponseListener<ShopCategoryResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_All_Shop_Categories.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    // http://stupendoustanuj.co.nf/Dabbawala/Fetch_All_Product_Categories.php
    public static AppHttpRequest fetchAllProductCategoryApi(String shopcategoryName, AppResponseListener<ProductCategoryResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_All_Product_Categories.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("shopCategoryName", shopcategoryName);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    // http://stupendoustanuj.co.nf/Dabbawala/Fetch_All_Product_Categories.php
    public static AppHttpRequest fetchDeliveryDetailsApi(String orderId, String deliveryDate, String deliveryStatus, AppResponseListener<DeliveryDetailResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_Delivery_Details.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("orderId", orderId);
        map.put("deliveryDate", deliveryDate);
        map.put("deliveryStatus", deliveryStatus);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    public static AppHttpRequest fetchAvailableProductApi(String shopcategoryName, String productCategoryName, AppResponseListener<ProductResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_Available_Products.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("shopId", USER_ID);
        map.put("shopCategoryName", shopcategoryName);
        map.put("productCategoryName", productCategoryName);
        request.addParam("input", setRequestBody(map));
        return request;
    }


    /// http://stupendoustanuj.co.nf/Dabbawala/Associate_A_Product.php

    public static AppHttpRequest addAssociatedProductAPI(String productSKUID,String englishName,String marathiName,String productDes, String productAvailability, String dailyPrice, String weeklyPrice,String monthlyPrice, String productStatusValue,AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Associate_A_Product.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("shopId", USER_ID);
        map.put("productSKUID", productSKUID);
        map.put("productAvailability", productAvailability);
        map.put("dailySubscriptionPrice", dailyPrice);
        map.put("weeklySubscriptionPrice", weeklyPrice);
        map.put("monthlySubscriptionPrice", monthlyPrice);
        map.put("productNameEnglish", englishName);
        map.put("productNameMarathi", marathiName);
        map.put("productDescription", productDes);
        map.put("productStatus", productStatusValue);
        request.addParam("input", setRequestBody(map));
        return request;

    }

    /// http://stupendoustanuj.co.nf/Dabbawala/Associate_A_Product.php

    public static AppHttpRequest updateAssociatedProductAPI(String productSKUID,String englishName,String marathiName,String productDes, String productAvailability, String dailyPrice, String weeklyPrice,String monthlyPrice, String productStatusValue, AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Associate_A_Product.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("shopId", USER_ID);
        map.put("productSKUID", productSKUID);
        map.put("productAvailability", productAvailability);
        map.put("dailySubscriptionPrice", dailyPrice);
        map.put("weeklySubscriptionPrice", weeklyPrice);
        map.put("monthlySubscriptionPrice", monthlyPrice);
        map.put("productNameEnglish", englishName);
        map.put("productNameMarathi", marathiName);
        map.put("productDescription", productDes);
        map.put("productStatus", productStatusValue);

        request.addParam("input", setRequestBody(map));
        return request;
    }


    // http://stupendoustanuj.co.nf/Dabbawala/Fetch_Available_Products.php
    public static AppHttpRequest viewAvailableProductAPI(String shopId, String shopCategoryName, String productCategoryName, AppResponseListener<ViewAvailableProductResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_Available_Products.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("shopId", shopId);
        map.put("shopCategoryName", shopCategoryName);
        map.put("productCategoryName", productCategoryName);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    // http://stupendoustanuj.co.nf/Dabbawala/Fetch_Available_Products.php
    public static AppHttpRequest fetchAvailableDeliveryLocationAPI(String shopId, AppResponseListener<DeliveryLocationResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_Available_DeliveryLocations.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("shopId", shopId);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    // http://stupendoustanuj.co.nf/Dabbawala/Add_A_Product.php
    public static AppHttpRequest addAProductAPI(String shopId,String productNameEnglish, String productNameHindi, String productDescription,
                                                String productCategoryName, String shopCategoryName, String productOrderUnit, String productPriceForUnits,
            String dailyPrice,String weeklyPrice,String monthlyPrice,String productAvailability,
                                                AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Add_A_Product.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<>();
        setUserHeader(map);
        map.put("productNameEnglish", productNameEnglish);
        map.put("productNameMarathi", productNameHindi);
        map.put("productDescription", productDescription);
        map.put("productCategoryName", productCategoryName);
        map.put("shopCategoryName", shopCategoryName);
        map.put("productOrderUnit", productOrderUnit);
        map.put("productPriceForUnits", productPriceForUnits);
        map.put("dailySubscriptionPrice", dailyPrice);
        map.put("weeklySubscriptionPrice", weeklyPrice);
        map.put("monthlySubscriptionPrice", monthlyPrice);
        map.put("productAvailability", productAvailability);
        map.put("shopId", shopId);
        request.addParam("input", setRequestBody(map));
        return request;
    }


    // http://stupendoustanuj.co.nf/Dabbawala/Add_A_Product.php
    public static AppHttpRequest associateADeliveryLocationAPI(String shopId,String deliveryLocation,
                                                               AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Associate_A_DeliveryLocation.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<>();
        setUserHeader(map);
        map.put("shopId", shopId);
        map.put("deliveryLocation", deliveryLocation);
        request.addParam("input", setRequestBody(map));
        return request;
    }


    // http://stupendoustanuj.co.nf/Dabbawala/Fetch_Associated_DeliveryPersons.php
    public static AppHttpRequest associatedDeliveryPersonAPI(String shopId, String all,
                                                             AppResponseListener<DeliveryPersonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_Associated_DeliveryPersons.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<>();
        setUserHeader(map);
        if(all.equals("1"))
            map.put("shopIdORSellerHubId", USER_ID);
        else
            map.put("shopIdORSellerHubId", shopId);
        map.put("all", all);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    // http://stupendoustanuj.co.nf/Dabbawala/Fetch_Shop_DeliveryLocations.php
    public static AppHttpRequest associatedDeliveryLocationAPI(String shopId,
                                                            AppResponseListener<DeliveryLocationResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_Shop_DeliveryLocations.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<>();
        setUserHeader(map);
        map.put("shopId", shopId);
        request.addParam("input", setRequestBody(map));
        return request;
    }


    // http://stupendoustanuj.co.nf/Dabbawala/Fetch_Shop_DeliveryLocations.php
    public static AppHttpRequest fetchShopOperationalTimeAPI(String shopId,String closingDate,
                                                               AppResponseListener<ShopOperationalTimeResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_Shop_Timings.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<>();
        setUserHeader(map);
        map.put("shopId", shopId);
        map.put("closingDate", closingDate);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    // http://stupendoustanuj.co.nf/Dabbawala/Fetch_Shop_DeliveryLocations.php
    public static AppHttpRequest fetchAssociatedProductCategoriesAPI(String shopId,
                                                             AppResponseListener<AssociatedProductCategoryResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_Associated_ProductCategories.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<>();
        setUserHeader(map);
        map.put("shopId", shopId);
        map.put("productCategoryName", "ALL");
        map.put("shopCategoryName", "ALL");
        request.addParam("input", setRequestBody(map));
        return request;
    }

    // http://stupendoustanuj.co.nf/Dabbawala/Fetch_Shop_DeliveryLocations.php
    public static AppHttpRequest associatedShopsAPI(String onlyShopId,AppResponseListener<AssociatedShopsResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_Associated_Shops.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<>();
        setUserHeader(map);
        map.put("shopCategory", "ALL");
        map.put("productCategory", "ALL");
        map.put("deliveryLocation", "ALL");
        map.put("onlyShopId", onlyShopId);
        map.put("userType", USER_TYPE);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    // http://stupendoustanuj.co.nf/Dabbawala/Fetch_Shop_DeliveryLocations.php
    public static AppHttpRequest associatedShopsAPI(String deliveryPersonId, String onlyShopId,AppResponseListener<AssociatedShopsResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_Associated_Shops.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<>();
        map.put("applicationId", AppConstant.APPLICATION_ID);
        map.put("userId", deliveryPersonId);
        map.put("shopCategory", "ALL");
        map.put("productCategory", "ALL");
        map.put("deliveryLocation", "ALL");
        map.put("onlyShopId", onlyShopId);
        map.put("userType", AppConstant.UserType.DELIVERY_PERSON_TYPE);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    // http://stupendoustanuj.co.nf/Dabbawala/Fetch_Shop_DeliveryLocations.php
    public static AppHttpRequest associatedShopId(AppResponseListener<AssociatedShopIdResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_Associated_Shops.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<>();
        setUserHeader(map);
        map.put("shopCategory", "ALL");
        map.put("productCategory", "ALL");
        map.put("deliveryLocation", "ALL");
        map.put("onlyShopId", "Y");
        map.put("userType", USER_TYPE);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    // http://stupendoustanuj.co.nf/Dabbawala/Fetch_Shop_DeliveryLocations.php
    public static AppHttpRequest addADeliveryLocationAPI(String deliveryLocation, String city,AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Add_A_DeliveryLocation.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<>();
        setUserHeader(map);
        map.put("deliveryLocation", deliveryLocation);
        map.put("city", city);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    public static AppHttpRequest VerifyApplicationIDAPI(AppResponseListener<CommonResponse> appResponseListener) {

        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Verify_ApplicationID.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    // http://stupendoustanuj.co.nf/Dabbawala/Deassociate_A_DeliveryPerson.php
    public static AppHttpRequest deAssociateDeliveryPersonAPI(String shopIdORSellerHubId, String deliveryPersonId,
                                                              AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Deassociate_A_DeliveryPerson.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<>();
        setUserHeader(map);
        map.put("shopIdORSellerHubId", shopIdORSellerHubId);
        map.put("deliveryPersonId", deliveryPersonId);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    // http://stupendoustanuj.co.nf/Dabbawala/Deassociate_A_DeliveryPerson.php
    public static AppHttpRequest associateADeliveryPersonAPI(String shopIdORSellerHubId, String deliveryPersonId,
                                                              AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Associate_A_DeliveryPerson.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<>();
        setUserHeader(map);
        map.put("shopIdORSellerHubId", shopIdORSellerHubId);
        map.put("deliveryPersonId", deliveryPersonId);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    // http://stupendoustanuj.co.nf/Dabbawala/Deassociate_A_DeliveryLocation.php
    public static AppHttpRequest deAssociateDeliveryLocationAPI(String shopId, String deliveryLocation,
                                                              AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Deassociate_A_DeliveryLocation.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<>();
        setUserHeader(map);
        map.put("shopId", shopId);
        map.put("deliveryLocation", deliveryLocation);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    //  http://stupendoustanuj.co.nf/Dabbawala/Fetch_SupportedID_Types.php

    public static AppHttpRequest supportedPersonIdTypeApi(AppResponseListener<SupportedIdTypeResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_SupportedID_Types.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<>();
        setUserHeader(map);
        request.addParam("input", setRequestBody(map));
        return request;
    }


    //  http://stupendoustanuj.co.nf/Dabbawala/Fetch_SupportedID_Types.php

    public static AppHttpRequest shopReferenceDataAPI(AppResponseListener<ShopReferenceDataResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Shop_Reference_Data.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<>();
        setUserHeader(map);
        request.addParam("input", setRequestBody(map));
        return request;
    }


    // http://stupendoustanuj.co.nf/Dabbawala/Add_A_DeliveryPerson.php
    public static AppHttpRequest addAssociateDeliveryPersonAPI(String deliveryPersonName,
                                                               String deliveryPersonMobileNumber,
                                                               String deliveryPersonResidentialAddress,
                                                               String deliveryPersonIDType,
                                                               String deliveryPersonIDNumber,
                                                               String emailId,
                                                               AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Add_A_DeliveryPerson.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<>();
        setUserHeader(map);
        map.put("deliveryPersonName", deliveryPersonName);
        map.put("deliveryPersonMobileNumber", deliveryPersonMobileNumber);
        map.put("deliveryPersonEmailId", emailId);
        map.put("deliveryPersonResidentialAddress", deliveryPersonResidentialAddress);
        map.put("deliveryPersonIDType", deliveryPersonIDType);
        map.put("deliveryPersonIDNumber", deliveryPersonIDNumber);
        map.put("shopIdORSellerHubId", USER_ID);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    // http://stupendoustanuj.co.nf/Dabbawala/Contact_us.php
    public static AppHttpRequest fetchDeliveryPersonProfileAPI(String deliveryPersonMobileNumber,AppResponseListener<DeliveryPersonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_DeliveryPerson_Profile.php", appResponseListener);

        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("deliveryPersonMobileNumber", deliveryPersonMobileNumber);
        request.addParam("input", setRequestBody(map));
        return request;
    }


    // http://stupendoustanuj.co.nf/Dabbawala/Contact_us.php
    public static AppHttpRequest contactUsSendMessageApi(String message, AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Contact_us.php", appResponseListener);

        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        map.put("message", message);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    // http://stupendoustanuj.co.nf/Dabbawala/Update_Shop_Timings.php
    public static AppHttpRequest addShopTimeAPI(String shopId,String closingDate,
                                                String shopCategory,String pCategory,
                                                AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Update_Shop_Timings.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<>();
        setUserHeader(map);
        map.put("shopId", shopId);
        map.put("closingDate", closingDate);
        map.put("shopCategory", shopCategory);
        map.put("productCategory", pCategory);
        map.put("updatedBy",USER_ID);
        request.addParam("input", setRequestBody(map));
        return request;
    }


    // http://stupendoustanuj.co.nf/Dabbawala/Update_Shop_Timings.php
    public static AppHttpRequest associateAProductCategoryAPI(String shopId, String fromTime, String toTime,
                                                              String shopCategory, String pCategory,
                                                              AppResponseListener<CommonResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Associate_A_ProductCategory.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<>();
        setUserHeader(map);
        map.put("shopId", shopId);
        map.put("fromDeliveryTime", fromTime);
        map.put("toDeliveryTime", toTime);
        map.put("shopCategoryName", shopCategory);
        map.put("productCategoryName", pCategory);
        map.put("updatedBy",USER_ID);
        request.addParam("input", setRequestBody(map));
        return request;
    }

    public static AppHttpRequest getLocationsBasedOnString(String loc, LocationResponseListener<ArrayList> appResponseListener) {
        return AppHttpRequest.getGetRequest("https://maps.googleapis.com/maps/api/place/autocomplete/" + loc, appResponseListener);
    }

    // http://stupendoustanuj.co.nf/Dabbawala/Fetch_Order_Units.php
    public static AppHttpRequest fetchOrderUnitApi(AppResponseListener<OrderUnitResponse> appResponseListener) {
        AppHttpRequest request = AppHttpRequest.getPostRequest(BASE_URL + "/Fetch_Order_Units.php", appResponseListener);
        Map<String, String> map = new LinkedHashMap<String, String>();
        setUserHeader(map);
        request.addParam("input", setRequestBody(map));
        return request;
    }

}
