package com.pay2ved.recharge.service.callmodel;

import com.google.gson.annotations.SerializedName;

public class CallObject {

    public CallObject() {
    }

    @SerializedName("message")
    private String message;

    @SerializedName("error")
    private int responseCode;

    @SerializedName("data")
    private Object data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
