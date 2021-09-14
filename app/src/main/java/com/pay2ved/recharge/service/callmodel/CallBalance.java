package com.pay2ved.recharge.service.callmodel;

import com.google.gson.annotations.SerializedName;

public class CallBalance {
    public CallBalance() {
    }

    // ------ Common Params ----
//     @SerializedName("error")
//     private int error;

     @SerializedName("message")
     private String message;

     // ------ Common Params ----

    @SerializedName("text")
    private String text;

    @SerializedName("data")
    private Data data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    //---------------------------
    public static class Data {
        public Data() {
        }

        @SerializedName("balance")
        private String balance;

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }
    }

}
