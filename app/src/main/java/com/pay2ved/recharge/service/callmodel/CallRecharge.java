package com.pay2ved.recharge.service.callmodel;

import com.google.gson.annotations.SerializedName;


public class CallRecharge {
    public CallRecharge() {
    }

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private Recharge data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Recharge getData() {
        return data;
    }

    public void setData(Recharge data) {
        this.data = data;
    }

    //---------------------
    public static class Recharge{
        public Recharge() {
        }

        @SerializedName("status")
        private String status;
        @SerializedName("detail")
        private String detail;
        @SerializedName("balance")
        private String balance;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }
    }

}
