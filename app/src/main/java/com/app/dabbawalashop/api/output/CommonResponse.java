package com.app.dabbawalashop.api.output;

/**
 * Created by umesh on 15/1/16.
 */
public class CommonResponse {

    private String additionalFields;

    public String getAdditionalFields() {
        return additionalFields;
    }

    public void setAdditionalFields(String additionalFields) {
        this.additionalFields = additionalFields;
    }


    private String successMessage;

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }
}
