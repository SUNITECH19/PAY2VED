package com.pay2ved.recharge.service.callmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CallCommission {

    public CallCommission() {
    }

    @SerializedName("message")
    private String message;

    // ------ Common Params ----

    @SerializedName("data")
    private List<ModelCommission> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ModelCommission> getData() {
        return data;
    }

    public void setData(List<ModelCommission> data) {
        this.data = data;
    }

    //---------------------------------------------------
    public static class ModelCommission {
        public ModelCommission() {
        }

        @SerializedName("id")
        private String id;
        @SerializedName("name")
        private String name;
        @SerializedName("service")
        private String service;
        @SerializedName("commission")
        private String commission;
        @SerializedName("surcharge")
        private String surcharge;

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

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public String getCommission() {
            return commission;
        }

        public void setCommission(String commission) {
            this.commission = commission;
        }

        public String getSurcharge() {
            return surcharge;
        }

        public void setSurcharge(String surcharge) {
            this.surcharge = surcharge;
        }
    }


}
