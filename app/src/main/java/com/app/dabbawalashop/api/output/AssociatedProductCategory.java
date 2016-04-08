package com.app.dabbawalashop.api.output;

/**
 * Created by TANUJ on 4/6/2016.
 */
public class AssociatedProductCategory {

    private String shopCategoryName;
    private String productCategoryName;
    private String shopId;
    private String fromDeliveryTime;
    private String toDeliveryTime;

    public String getShopCategoryName() {
        return shopCategoryName;
    }

    public void setShopCategoryName(String shopCategoryName) {
        this.shopCategoryName = shopCategoryName;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getFromDeliveryTime() {
        return fromDeliveryTime;
    }

    public void setFromDeliveryTime(String fromDeliveryTime) {
        this.fromDeliveryTime = fromDeliveryTime;
    }

    public String getToDeliveryTime() {
        return toDeliveryTime;
    }

    public void setToDeliveryTime(String toDeliveryTime) {
        this.toDeliveryTime = toDeliveryTime;
    }
}
