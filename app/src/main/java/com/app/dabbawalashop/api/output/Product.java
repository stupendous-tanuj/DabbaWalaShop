package com.app.dabbawalashop.api.output;

public class Product {


    private String productSKUID;
    private String productNameEnglish;
    private String productNameMarathi;
    private String productDescription;
    private String productOrderUnit;
    private String productPriceForUnits;
    private String dailySubscriptionPrice;
    private String weeklySubscriptionPrice;
    private String monthlySubscriptionPrice;
    private String productImageName;

    private String additionalFields;

    public String getAdditionalFields() {
        return additionalFields;
    }

    public void setAdditionalFields(String additionalFields) {
        this.additionalFields = additionalFields;
    }

    public String getProductRegistrationStatus() {
        return productRegistrationStatus;
    }

    public void setProductRegistrationStatus(String productRegistrationStatus) {
        this.productRegistrationStatus = productRegistrationStatus;
    }

    private String productRegistrationStatus;

    public String getProductSKUID() {
        return productSKUID;
    }

    /**
     *
     * @param productSKUID
     * The productSKUID
     */
    public void setProductSKUID(String productSKUID) {
        this.productSKUID = productSKUID;
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
     * The productDescription
     */
    public String getProductDescription() {
        return productDescription;
    }

    /**
     *
     * @param productDescription
     * The productDescription
     */
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
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
     * The dailySubscriptionPrice
     */
    public String getDailySubscriptionPrice() {
        return dailySubscriptionPrice;
    }

    /**
     *
     * @param dailySubscriptionPrice
     * The dailySubscriptionPrice
     */
    public void setDailySubscriptionPrice(String dailySubscriptionPrice) {
        this.dailySubscriptionPrice = dailySubscriptionPrice;
    }

    /**
     *
     * @return
     * The weeklySubscriptionPrice
     */
    public String getWeeklySubscriptionPrice() {
        return weeklySubscriptionPrice;
    }

    /**
     *
     * @param weeklySubscriptionPrice
     * The weeklySubscriptionPrice
     */
    public void setWeeklySubscriptionPrice(String weeklySubscriptionPrice) {
        this.weeklySubscriptionPrice = weeklySubscriptionPrice;
    }

    /**
     *
     * @return
     * The monthlySubscriptionPrice
     */
    public String getMonthlySubscriptionPrice() {
        return monthlySubscriptionPrice;
    }

    /**
     *
     * @param monthlySubscriptionPrice
     * The monthlySubscriptionPrice
     */
    public void setMonthlySubscriptionPrice(String monthlySubscriptionPrice) {
        this.monthlySubscriptionPrice = monthlySubscriptionPrice;
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