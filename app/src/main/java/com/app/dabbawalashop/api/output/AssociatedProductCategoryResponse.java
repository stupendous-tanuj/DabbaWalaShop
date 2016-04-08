package com.app.dabbawalashop.api.output;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TANUJ on 4/6/2016.
 */
public class AssociatedProductCategoryResponse {

    private List<AssociatedProductCategory> associatedProductCategory = new ArrayList<AssociatedProductCategory>();

    /**
     *
     * @return
     * The associatedProductCategory
     */
    public List<AssociatedProductCategory> getAssociatedProductCategory() {
        return associatedProductCategory;
    }

    /**
     *
     * @param associatedProductCategory
     * The associatedProductCategory
     */
    public void setAssociatedProductCategory(List<AssociatedProductCategory> associatedProductCategory) {
        this.associatedProductCategory = associatedProductCategory;
    }


}
