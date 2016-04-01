package com.app.dabbawalashop.api.output;

/**
 * Created by TANUJ on 3/31/2016.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopOperationalTimeResponse {

    private List<ShopOperationalTime> shopOperationalTime = new ArrayList<ShopOperationalTime>();

    /**
     *
     * @return
     * The shopOperationalTime
     */
    public List<ShopOperationalTime> getShopOperationalTime() {
        return shopOperationalTime;
    }

    /**
     *
     * @param shopOperationalTime
     * The shopOperationalTime
     */
    public void setShopOperationalTime(List<ShopOperationalTime> shopOperationalTime) {
        this.shopOperationalTime = shopOperationalTime;
    }

}
