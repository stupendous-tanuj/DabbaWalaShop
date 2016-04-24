package com.app.dabbawalashop.api.output;

/**
 * Created by TANUJ on 3/11/2016.
 */
public class DeliveryDetail {

    private String orderId;
    private String deliveryDate;
    private String deliveryStatus;
    private String deliveryPerson;
    private String invoiceAmount;
    private String balanceAmount;
    private String deliveryCancellationReason;
    private String amountReceivedByShop;
    private String toDeliveryStatus;
    private String amountAdjustmentDone;
    private String orderPlacedTo;
    private String orderPlacedBy;

    private String additionalFields;

    public String getAdditionalFields() {
        return additionalFields;
    }

    public void setAdditionalFields(String additionalFields) {
        this.additionalFields = additionalFields;
    }


    public String getOrderPlacedTo() {
        return orderPlacedTo;
    }

    public void setOrderPlacedTo(String orderPlacedTo) {
        this.orderPlacedTo = orderPlacedTo;
    }

    public String getOrderPlacedBy() {
        return orderPlacedBy;
    }

    public void setOrderPlacedBy(String orderPlacedBy) {
        this.orderPlacedBy = orderPlacedBy;
    }

    public String getAmountReceivedByShop() {
        return amountReceivedByShop;
    }

    public void setAmountReceivedByShop(String amountReceivedByShop) {
        this.amountReceivedByShop = amountReceivedByShop;
    }

    public String getToDeliveryStatus() {
        return toDeliveryStatus;
    }

    public void setToDeliveryStatus(String toDeliveryStatus) {
        this.toDeliveryStatus = toDeliveryStatus;
    }

    public String getAmountAdjustmentDone() {
        return amountAdjustmentDone;
    }

    public void setAmountAdjustmentDone(String amountAdjustmentDone) {
        this.amountAdjustmentDone = amountAdjustmentDone;
    }


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
     * The deliveryDate
     */
    public String getDeliveryDate() {
        return deliveryDate;
    }

    /**
     *
     * @param deliveryDate
     * The deliveryDate
     */
    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    /**
     *
     * @return
     * The deliveryStatus
     */
    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    /**
     *
     * @param deliveryStatus
     * The deliveryStatus
     */
    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    /**
     *
     * @return
     * The deliveryPerson
     */
    public String getDeliveryPerson() {
        return deliveryPerson;
    }

    /**
     *
     * @param deliveryPerson
     * The deliveryPerson
     */
    public void setDeliveryPerson(String deliveryPerson) {
        this.deliveryPerson = deliveryPerson;
    }

    /**
     *
     * @return
     * The invoiceAmount
     */
    public String getInvoiceAmount() {
        return invoiceAmount;
    }

    /**
     *
     * @param invoiceAmount
     * The invoiceAmount
     */
    public void setInvoiceAmount(String invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    /**
     *
     * @return
     * The balanceAmount
     */
    public String getBalanceAmount() {
        return balanceAmount;
    }

    /**
     *
     * @param balanceAmount
     * The balanceAmount
     */
    public void setBalanceAmount(String balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    /**
     *
     * @return
     * The deliveryCancellationReason
     */
    public String getDeliveryCancellationReason() {
        return deliveryCancellationReason;
    }

    /**
     *
     * @param deliveryCancellationReason
     * The deliveryCancellationReason
     */
    public void setDeliveryCancellationReason(String deliveryCancellationReason) {
        this.deliveryCancellationReason = deliveryCancellationReason;
    }

}
