package com.pay2ved.recharge.model;

public class ItemData {

    String text;
    String id;
    Integer imageId;

    public ItemData(String text,String id, Integer imageId) {
        this.text = text;
        this.id = id;
        this.imageId = imageId;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Integer getImageId() {
        return imageId;
    }

}
