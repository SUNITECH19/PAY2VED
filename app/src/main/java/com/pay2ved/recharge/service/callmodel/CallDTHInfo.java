package com.pay2ved.recharge.service.callmodel;

import com.google.gson.annotations.SerializedName;

public class CallDTHInfo {
    public CallDTHInfo() {
    }

    @SerializedName("msg")
    private String message;

    @SerializedName("status")
    private int responseCode;

    @SerializedName("number")
    private String number;

    @SerializedName("operator")
    private String operator;

    @SerializedName("info")
    private Info info;

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    //-----------------------------------------

    public static class Info{
        public Info() {
        }

        @SerializedName("customer_name")
        private String customer_name;
        @SerializedName("balance")
        private String balance;
        @SerializedName("plan_name")
        private String plan_name;
        @SerializedName("next_recharge_date")
        private String next_recharge_date;
        @SerializedName("monthly_recharge")
        private String monthly_recharge;

        public String getCustomer_name() {
            return customer_name;
        }

        public void setCustomer_name(String customer_name) {
            this.customer_name = customer_name;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getPlan_name() {
            return plan_name;
        }

        public void setPlan_name(String plan_name) {
            this.plan_name = plan_name;
        }

        public String getNext_recharge_date() {
            return next_recharge_date;
        }

        public void setNext_recharge_date(String next_recharge_date) {
            this.next_recharge_date = next_recharge_date;
        }

        public String getMonthly_recharge() {
            return monthly_recharge;
        }

        public void setMonthly_recharge(String monthly_recharge) {
            this.monthly_recharge = monthly_recharge;
        }
    }


}
