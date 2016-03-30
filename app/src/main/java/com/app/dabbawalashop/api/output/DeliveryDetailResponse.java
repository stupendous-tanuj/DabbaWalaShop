package com.app.dabbawalashop.api.output;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TANUJ on 3/11/2016.
 */
public class DeliveryDetailResponse {

    private List<DeliveryDetail> deliveryDetails = new ArrayList<DeliveryDetail>();
    private List<CartDetail> cartDetails = new ArrayList<CartDetail>();

    /**
     *
     * @return
     * The deliveryDetails
     */
    public List<DeliveryDetail> getDeliveryDetails() {
        return deliveryDetails;
    }

    /**
     *
     * @param deliveryDetails
     * The deliveryDetails
     */
    public void setDeliveryDetails(List<DeliveryDetail> deliveryDetails) {
        this.deliveryDetails = deliveryDetails;
    }

    /**
     *
     * @return
     * The cartDetails
     */
    public List<CartDetail> getCartDetails() {
        return cartDetails;
    }

    /**
     *
     * @param cartDetails
     * The cartDetails
     */
    public void setCartDetails(List<CartDetail> cartDetails) {
        this.cartDetails = cartDetails;
    }
}
