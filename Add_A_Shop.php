<?php
/*
{"applicationId": "ANDSH1001", "userId": "SP1001", "shopName": "shopName", "shopRegistrationStatus": "shopRegistrationStatus", "shopOwnerName": "shopOwnerName", "shopDescription": "shopDescription", "shopAddress": "shopAddress", "shopAddressAreaSector": "shopAddressAreaSector", "shopAddressCity": "shopAddressCity", "shopAddressPincode": "shopAddressPincode", "shopAddressState": "shopAddressState", "shopAddressCountry": "shopAddressCountry", "shopAddressLandmark": "shopAddressLandmark", "shopOwnerContactNumber": "9503157441","shopSupportContactNumber": "9503157441",  "shopOrderProcessingContactNumber": "9503157441","shopEmailId": "shopEmailId", "shopMinimumAcceptedOrder": "200", "shopDeliveryCharges": "10", "shopOwnerIDType": "PAN", "shopOwnerIDNumber": "hbbbu", "shopDeliveryTypeSupported": "Delivery", "shopPaymentMethodSupported": "Payment"}
ShopTheFortune/Add_A_Product.php?input={"applicationId": "ANDSH1001", "userId": "SP1001", "shopName": "shopName", "shopRegistrationStatus": "shopRegistrationStatus", "shopOwnerName": "shopOwnerName", "shopDescription": "shopDescription", "shopAddress": "shopAddress", "shopAddressAreaSector": "shopAddressAreaSector", "shopAddressCity": "shopAddressCity", "shopAddressPincode": "shopAddressPincode", "shopAddressState": "shopAddressState", "shopAddressCountry": "shopAddressCountry", "shopAddressLandmark": "shopAddressLandmark", "shopOwnerContactNumber": "9503157441","shopSupportContactNumber": "9503157441",  "shopOrderProcessingContactNumber": "9503157441","shopEmailId": "shopEmailId", "shopMinimumAcceptedOrder": "200", "shopDeliveryCharges": "10", "shopOwnerIDType": "PAN", "shopOwnerIDNumber": "hbbbu", "shopDeliveryTypeSupported": "Delivery", "shopPaymentMethodSupported": "Payment"}
*/
include 'database.php';
header('Content-type: x-www-form-urlencoded');
$jsonRequest = json_decode( $_POST['input'], true);
$applicationId = mysqli_real_escape_string($link,$jsonRequest['applicationId']);
$userId = mysqli_real_escape_string($link,$jsonRequest['userId']);
$productNameEnglish = mysqli_real_escape_string($link,$jsonRequest['productNameEnglish']);
$productNameMarathi = mysqli_real_escape_string($link,$jsonRequest['productNameMarathi']);
$productDescription = mysqli_real_escape_string($link,$jsonRequest['productDescription']);
$productCategoryName = mysqli_real_escape_string($link,$jsonRequest['productCategoryName']);
$shopCategoryName = mysqli_real_escape_string($link,$jsonRequest['shopCategoryName']);
$productOrderUnit = mysqli_real_escape_string($link,$jsonRequest['productOrderUnit']);
$productPriceForUnits = mysqli_real_escape_string($link,$jsonRequest['productPriceForUnits']);
$productAvailability = mysqli_real_escape_string($link,$jsonRequest['productAvailability']); 
$dailySubscriptionPrice = mysqli_real_escape_string($link,$jsonRequest['dailySubscriptionPrice']); 
$weeklySubscriptionPrice = mysqli_real_escape_string($link,$jsonRequest['weeklySubscriptionPrice']); 
$monthlySubscriptionPrice = mysqli_real_escape_string($link,$jsonRequest['monthlySubscriptionPrice']); 
$shopId = mysqli_real_escape_string($link,$jsonRequest['shopId']); 


try {
if($applicationId=="")
throw new Exception($applicationId_MISSING);

if($userId=="")
throw new Exception($userId_MISSING);

if($productNameEnglish=="")
throw new Exception($productNameEnglish_MISSING);

if($productNameMarathi=="")
throw new Exception($productNameMarathi_MISSING);

if($productDescription=="")
throw new Exception($productDescription_MISSING);

if($productCategoryName=="")
throw new Exception($productCategoryName_MISSING);

if($shopCategoryName=="")
throw new Exception($shopCategoryName_MISSING);

if($productOrderUnit=="")
throw new Exception($productOrderUnit_MISSING);

if($productPriceForUnits=="")
throw new Exception($productPriceForUnits_MISSING);

if($productAvailability=="")
throw new Exception($productAvailability_MISSING);

if($dailySubscriptionPrice=="")
throw new Exception($dailySubscriptionPrice_MISSING);

if($weeklySubscriptionPrice=="")
throw new Exception($weeklySubscriptionPrice_MISSING);

if($monthlySubscriptionPrice=="")
throw new Exception($monthlySubscriptionPrice_MISSING);

if($shopId =="")
throw new Exception($shopId_MISSING);

$sql = "INSERT INTO `Shop` (`ShopName`, `ShopRegistrationStatus`, `ShopOwnerName`, `ShopDescription`, `ShopAddress`, `ShopAddressAreaSector`, `ShopAddressCity`, `ShopAddressPincode`, `ShopAddressState`, `ShopAddressCountry`, `ShopAddressLandmark`, `ShopOwnerContactNumber`,`ShopSupportContactNumber`,  `ShopOrderProcessingContactNumber`,`ShopEmailId`, `ShopMinimumAcceptedOrder`, `ShopDeliveryCharges`, `ShopOwnerIDType`, `ShopOwnerIDNumber`, `ShopOwnerIDDocumentName`,`ShopCreatedBy`, `ShopCreatedFor`, `ShopDeliveryTypeSupported`, `ShopPaymentMethodSupported`, `ShopCreationTimestamp`) VALUES (`ShopName`, `ShopRegistrationStatus`, `ShopOwnerName`, `ShopDescription`, `ShopAddress`, `ShopAddressAreaSector`, `ShopAddressCity`, `ShopAddressPincode`, `ShopAddressState`, `ShopAddressCountry`, `ShopAddressLandmark`, `ShopOwnerContactNumber`,`ShopSupportContactNumber`,  `ShopOrderProcessingContactNumber`,`ShopEmailId`, `ShopMinimumAcceptedOrder`, `ShopDeliveryCharges`, `ShopOwnerIDType`, `ShopOwnerIDNumber`, `ShopOwnerIDDocumentName`,`ShopCreatedBy`, `ShopCreatedFor`, `ShopDeliveryTypeSupported`, `ShopPaymentMethodSupported`, `ShopCreationTimestamp`);";
