package com.app.dabbawalashop.api.output;

public class OrderDetail {

    private String orderId;
    private String orderPlacedTo;
    private String orderPlacedBy;
    private String orderQuotedAmount;
    private String orderDeliveryType;
    private String orderPaymentMethod;
    private String orderStatus;
    private String paymentStatus;
    private String orderCreationTimestamp;
    private String orderDeliveryAddressIdentifier;
    private String deliveryDates;
    private String orderSubscriptionType;
    private String amountAdjustmentDone;
    private String orderBalanceAmount;
    private String orderInvoiceAmount;
    private String orderCancellationReason;

    private String additionalFields;

    public String getAdditionalFields() {
        return additionalFields;
    }

    public void setAdditionalFields(String additionalFields) {
        this.additionalFields = additionalFields;
    }

    public String getToOrderValues() {
        return toOrderValues;
    }

    public void setToOrderValues(String toOrderValues) {
        this.toOrderValues = toOrderValues;
    }

    private String toOrderValues;

    /**
     *
     * @return
     * The orderId
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     *
     * @param orderId
     * The orderId
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     *
     * @return
     * The orderPlacedTo
     */
    public String getOrderPlacedTo() {
        return orderPlacedTo;
    }

    /**
     *
     * @param orderPlacedTo
     * The orderPlacedTo
     */
    public void setOrderPlacedTo(String orderPlacedTo) {
        this.orderPlacedTo = orderPlacedTo;
    }

    /**
     *
     * @return
     * The orderPlacedBy
     */
    public String getOrderPlacedBy() {
        return orderPlacedBy;
    }

    /**
     *
     * @param orderPlacedBy
     * The orderPlacedBy
     */
    public void setOrderPlacedBy(String orderPlacedBy) {
        this.orderPlacedBy = orderPlacedBy;
    }

    /**
     *
     * @return
     * The orderQuotedAmount
     */
    public String getOrderQuotedAmount() {
        return orderQuotedAmount;
    }

    /**
     *
     * @param orderQuotedAmount
     * The orderQuotedAmount
     */
    public void setOrderQuotedAmount(String orderQuotedAmount) {
        this.orderQuotedAmount = orderQuotedAmount;
    }

    /**
     *
     * @return
     * The orderDeliveryType
     */
    public String getOrderDeliveryType() {
        return orderDeliveryType;
    }

    /**
     *
     * @param orderDeliveryType
     * The orderDeliveryType
     */
    public void setOrderDeliveryType(String orderDeliveryType) {
        this.orderDeliveryType = orderDeliveryType;
    }

    /**
     *
     * @return
     * The orderPaymentMethod
     */
    public String getOrderPaymentMethod() {
        return orderPaymentMethod;
    }

    /**
     *
     * @param orderPaymentMethod
     * The orderPaymentMethod
     */
    public void setOrderPaymentMethod(String orderPaymentMethod) {
        this.orderPaymentMethod = orderPaymentMethod;
    }

    /**
     *
     * @return
     * The orderStatus
     */
    public String getOrderStatus() {
        return orderStatus;
    }

    /**
     *
     * @param orderStatus
     * The orderStatus
     */
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     *
     * @return
     * The paymentStatus
     */
    public String getPaymentStatus() {
        return paymentStatus;
    }

    /**
     *
     * @param paymentStatus
     * The paymentStatus
     */
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    /**
     *
     * @return
     * The orderCreationTimestamp
     */
    public String getOrderCreationTimestamp() {
        return orderCreationTimestamp;
    }

    /**
     *
     * @param orderCreationTimestamp
     * The orderCreationTimestamp
     */
    public void setOrderCreationTimestamp(String orderCreationTimestamp) {
        this.orderCreationTimestamp = orderCreationTimestamp;
    }

    /**
     *
     * @return
     * The orderDeliveryAddressIdentifier
     */
    public String getOrderDeliveryAddressIdentifier() {
        return orderDeliveryAddressIdentifier;
    }

    /**
     *
     * @param orderDeliveryAddressIdentifier
     * The orderDeliveryAddressIdentifier
     */
    public void setOrderDeliveryAddressIdentifier(String orderDeliveryAddressIdentifier) {
        this.orderDeliveryAddressIdentifier = orderDeliveryAddressIdentifier;
    }

    /**
     *
     * @return
     * The deliveryDates
     */
    public String getDeliveryDates() {
        return deliveryDates;
    }

    /**
     *
     * @param deliveryDates
     * The deliveryDates
     */
    public void setDeliveryDates(String deliveryDates) {
        this.deliveryDates = deliveryDates;
    }

    /**
     *
     * @return
     * The orderSubscriptionType
     */
    public String getOrderSubscriptionType() {
        return orderSubscriptionType;
    }

    /**
     *
     * @param orderSubscriptionType
     * The orderSubscriptionType
     */
    public void setOrderSubscriptionType(String orderSubscriptionType) {
        this.orderSubscriptionType = orderSubscriptionType;
    }

    /**
     *
     * @return
     * The amountAdjustmentDone
     */
    public String getAmountAdjustmentDone() {
        return amountAdjustmentDone;
    }

    /**
     *
     * @param amountAdjustmentDone
     * The amountAdjustmentDone
     */
    public void setAmountAdjustmentDone(String amountAdjustmentDone) {
        this.amountAdjustmentDone = amountAdjustmentDone;
    }

    /**
     *
     * @return
     * The orderBalanceAmount
     */
    public String getOrderBalanceAmount() {
        return orderBalanceAmount;
    }

    /**
     *
     * @param orderBalanceAmount
     * The orderBalanceAmount
     */
    public void setOrderBalanceAmount(String orderBalanceAmount) {
        this.orderBalanceAmount = orderBalanceAmount;
    }

    /**
     *
     * @return
     * The orderInvoiceAmount
     */
    public String getOrderInvoiceAmount() {
        return orderInvoiceAmount;
    }

    /**
     *
     * @param orderInvoiceAmount
     * The orderInvoiceAmount
     */
    public void setOrderInvoiceAmount(String orderInvoiceAmount) {
        this.orderInvoiceAmount = orderInvoiceAmount;
    }

    /**
     *
     * @return
     * The orderCancellationReason
     */
    public String getOrderCancellationReason() {
        return orderCancellationReason;
    }

    /**
     *
     * @param orderCancellationReason
     * The orderCancellationReason
     */
    public void setOrderCancellationReason(String orderCancellationReason) {
        this.orderCancellationReason = orderCancellationReason;
    }

}