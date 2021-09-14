package com.pay2ved.recharge.model;

import com.google.gson.internal.$Gson$Preconditions;

public class ModelSearchableItem {

    private String image;
    private String title;
    private String code;
    private String remark;


    public ModelSearchableItem(String image, String title, String code, String remark) {
        this.image = image;
        this.title = title;
        this.code = code;
        this.remark = remark;
    }

    public ModelSearchableItem() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
