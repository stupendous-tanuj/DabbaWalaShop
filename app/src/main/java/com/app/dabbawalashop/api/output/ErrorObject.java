package com.app.dabbawalashop.api.output;

/**
 * Created by umesh on 17/12/15.
 */
public class ErrorObject {

    private String additionalFields;

    public String getAdditionalFields() {
        return additionalFields;
    }

    public void setAdditionalFields(String additionalFields) {
        this.additionalFields = additionalFields;
    }


    private Integer errorCode;
    private String errorMessage;
    private String supportContactNumber;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSupportContactNumber() {
        return supportContactNumber;
    }

    public void setSupportContactNumber(String supportContactNumber) {
        this.supportContactNumber = supportContactNumber;
    }
}