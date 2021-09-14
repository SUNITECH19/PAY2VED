package com.pay2ved.recharge.service.callmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CallRechargeReport {

    public CallRechargeReport() {
    }

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<ModelRechargeRpt> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ModelRechargeRpt> getData() {
        return data;
    }

    public void setData(List<ModelRechargeRpt> data) {
        this.data = data;
    }

    //------- Model Class ------------------
    public static class ModelRechargeRpt{

        public ModelRechargeRpt() {
        }

        @SerializedName("id")
        private String id;
        @SerializedName("name")
        private String name;
        @SerializedName("amount")
        private String amount;
        @SerializedName("status")
        private String status;
        @SerializedName("date")
        private String date;
        @SerializedName("ref_no")
        private String ref_no;
        @SerializedName("account")
        private String account;
        @SerializedName("retailer")
        private String retailer;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getRef_no() {
            return ref_no;
        }

        public void setRef_no(String ref_no) {
            this.ref_no = ref_no;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getRetailer() {
            return retailer;
        }

        public void setRetailer(String retailer) {
            this.retailer = retailer;
        }
    }

}
