package com.pay2ved.recharge.service.callmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CallHomeMessage {

    public CallHomeMessage() {
    }

    /**
     // ------ Common Params ----
     @SerializedName("error")
     private int error;

     @SerializedName("message")
     private String message;

     // ------ Common Params ----
     */

    @SerializedName("text")
    private String text;

    @SerializedName("image")
    private List<ImageInfo> data = null;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<ImageInfo> getData() {
        return data;
    }

    public void setData(List<ImageInfo> data) {
        this.data = data;
    }

    // -------- Helper Class --------------
    public static class ImageInfo{
        public ImageInfo() {
        }

        @SerializedName("url")
        public String url;
    }


}
