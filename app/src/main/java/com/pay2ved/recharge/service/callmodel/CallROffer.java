package com.pay2ved.recharge.service.callmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CallROffer {
    public CallROffer() {
    }

    @SerializedName("message")
    private String message;

    @SerializedName("error")
    private int error;

    @SerializedName("operator")
    private String operator;

    //------------
    @SerializedName("tel")
    private String tel;

    @SerializedName("roffer")
    private List<ROfferItem> roffer;
    //------------

    //------------
    @SerializedName("number")
    private String number;

    @SerializedName("data")
    private List<DataItem> data;
    //------------


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public List<ROfferItem> getRoffer() {
        return roffer;
    }

    public void setRoffer(List<ROfferItem> roffer) {
        this.roffer = roffer;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public List<DataItem> getData() {
        return data;
    }

    public void setData(List<DataItem> data) {
        this.data = data;
    }

    // ---------------------------------------------------------------------------------------------
    public static class ROfferItem {
        public ROfferItem() {
        }
        @SerializedName("rs")
        private String rs;
        @SerializedName("desc")
        private String desc;

        public String getRs() {
            return rs;
        }

        public void setRs(String rs) {
            this.rs = rs;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public static class DataItem {
        public DataItem() {
        }
        @SerializedName("amount")
        private String amount;
        @SerializedName("description")
        private String description;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

}
