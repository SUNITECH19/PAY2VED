package com.pay2ved.recharge.service.callmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CallPlans {

    public CallPlans() {
    }

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<ModelPlanCategory> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ModelPlanCategory> getData() {
        return data;
    }

    public void setData(List<ModelPlanCategory> data) {
        this.data = data;
    }

    // Model Helper Class... ----------------------------
    public static class ModelPlanCategory{

        public ModelPlanCategory() {
        }

        @SerializedName("category")
        private String category;

        @SerializedName("plan")
        private List<ModelPlanList> plan;

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public List<ModelPlanList> getPlan() {
            return plan;
        }

        public void setPlan(List<ModelPlanList> plan) {
            this.plan = plan;
        }
    }
    public static class ModelPlanList{

        public ModelPlanList() {
        }
        @SerializedName("amount")
        private String amount;
        @SerializedName("validity")
        private String validity;
        @SerializedName("description")
        private String description;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getValidity() {
            return validity;
        }

        public void setValidity(String validity) {
            this.validity = validity;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

}
