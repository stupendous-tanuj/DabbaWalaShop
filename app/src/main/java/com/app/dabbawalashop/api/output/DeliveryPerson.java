package com.app.dabbawalashop.api.output;

public class DeliveryPerson {

    private String deliveryPersonName;
    private String deliveryPersonMobileNumber;

    public String getDeliveryPersonRegistrationStatus() {
        return deliveryPersonRegistrationStatus;
    }

    public void setDeliveryPersonRegistrationStatus(String deliveryPersonRegistrationStatus) {
        this.deliveryPersonRegistrationStatus = deliveryPersonRegistrationStatus;
    }

    public String getDeliveryPersonResidentialAddress() {
        return deliveryPersonResidentialAddress;
    }

    public void setDeliveryPersonResidentialAddress(String deliveryPersonResidentialAddress) {
        this.deliveryPersonResidentialAddress = deliveryPersonResidentialAddress;
    }

    public String getDeliveryPersonIDType() {
        return deliveryPersonIDType;
    }

    public void setDeliveryPersonIDType(String deliveryPersonIDType) {
        this.deliveryPersonIDType = deliveryPersonIDType;
    }

    public String getDeliveryPersonIDNumber() {
        return deliveryPersonIDNumber;
    }

    public void setDeliveryPersonIDNumber(String deliveryPersonIDNumber) {
        this.deliveryPersonIDNumber = deliveryPersonIDNumber;
    }

    private String deliveryPersonRegistrationStatus;
    private String deliveryPersonResidentialAddress;
    private String deliveryPersonIDType;
    private String deliveryPersonIDNumber;

    /**
     * @return The deliveryPersonName
     */
    public String getDeliveryPersonName() {
        return deliveryPersonName;
    }

    /**
     * @param deliveryPersonName The deliveryPersonName
     */
    public void setDeliveryPersonName(String deliveryPersonName) {
        this.deliveryPersonName = deliveryPersonName;
    }

    /**
     * @return The deliveryPersonMobileNumber
     */
    public String getDeliveryPersonMobileNumber() {
        return deliveryPersonMobileNumber;
    }

    /**
     * @param deliveryPersonMobileNumber The deliveryPersonMobileNumber
     */
    public void setDeliveryPersonMobileNumber(String deliveryPersonMobileNumber) {
        this.deliveryPersonMobileNumber = deliveryPersonMobileNumber;
    }

}