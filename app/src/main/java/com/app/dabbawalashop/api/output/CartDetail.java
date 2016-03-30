package com.app.dabbawalashop.api.output;

public class CartDetail {

    private String cartProductSKUID;
    private String cartProductPrice;
    private String cartProductOrderedQuantity;
    private String productOrderUnit;
    private String productPriceForUnits;
    private String productNameEnglish;
    private String productNameMarathi;
    private String productDescription;
    private String productImageName;

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }



    /**
     *
     * @return
     * The cartProductSKUID
     */
    public String getCartProductSKUID() {
        return cartProductSKUID;
    }

    /**
     *
     * @param cartProductSKUID
     * The cartProductSKUID
     */
    public void setCartProductSKUID(String cartProductSKUID) {
        this.cartProductSKUID = cartProductSKUID;
    }

    /**
     *
     * @return
     * The cartProductPrice
     */
    public String getCartProductPrice() {
        return cartProductPrice;
    }

    /**
     *
     * @param cartProductPrice
     * The cartProductPrice
     */
    public void setCartProductPrice(String cartProductPrice) {
        this.cartProductPrice = cartProductPrice;
    }

    /**
     *
     * @return
     * The cartProductOrderedQuantity
     */
    public String getCartProductOrderedQuantity() {
        return cartProductOrderedQuantity;
    }

    /**
     *
     * @param cartProductOrderedQuantity
     * The cartProductOrderedQuantity
     */
    public void setCartProductOrderedQuantity(String cartProductOrderedQuantity) {
        this.cartProductOrderedQuantity = cartProductOrderedQuantity;
    }

    /**
     *
     * @return
     * The productOrderUnit
     */
    public String getProductOrderUnit() {
        return productOrderUnit;
    }

    /**
     *
     * @param productOrderUnit
     * The productOrderUnit
     */
    public void setProductOrderUnit(String productOrderUnit) {
        this.productOrderUnit = productOrderUnit;
    }

    /**
     *
     * @return
     * The productPriceForUnits
     */
    public String getProductPriceForUnits() {
        return productPriceForUnits;
    }

    /**
     *
     * @param productPriceForUnits
     * The productPriceForUnits
     */
    public void setProductPriceForUnits(String productPriceForUnits) {
        this.productPriceForUnits = productPriceForUnits;
    }

    /**
     *
     * @return
     * The productNameEnglish
     */
    public String getProductNameEnglish() {
        return productNameEnglish;
    }

    /**
     *
     * @param productNameEnglish
     * The productNameEnglish
     */
    public void setProductNameEnglish(String productNameEnglish) {
        this.productNameEnglish = productNameEnglish;
    }

    /**
     *
     * @return
     * The productNameMarathi
     */
    public String getProductNameMarathi() {
        return productNameMarathi;
    }

    /**
     *
     * @param productNameMarathi
     * The productNameMarathi
     */
    public void setProductNameMarathi(String productNameMarathi) {
        this.productNameMarathi = productNameMarathi;
    }

    /**
     *
     * @return
     * The productImageName
     */
    public String getProductImageName() {
        return productImageName;
    }

    /**
     *
     * @param productImageName
     * The productImageName
     */
    public void setProductImageName(String productImageName) {
        this.productImageName = productImageName;
    }
}