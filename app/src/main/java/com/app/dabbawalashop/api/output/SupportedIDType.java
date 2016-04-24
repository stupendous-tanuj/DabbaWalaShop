package com.app.dabbawalashop.api.output;

public class SupportedIDType {

    private String additionalFields;

    public String getAdditionalFields() {
        return additionalFields;
    }

    public void setAdditionalFields(String additionalFields) {
        this.additionalFields = additionalFields;
    }

    private String idType;

    /**
     * @return The idType
     */
    public String getIdType() {
        return idType;
    }

    /**
     * @param idType The idType
     */
    public void setIdType(String idType) {
        this.idType = idType;
    }
}