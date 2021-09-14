package com.pay2ved.recharge.service.callmodel;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CallDTHPlans {

    public CallDTHPlans() {
    }

    @SerializedName("message")
    private String message;

    @SerializedName("error")
    private int error;

    @SerializedName("operator")
    private String operator;

    @SerializedName("data")
    private List<DTHPlanItem> data;

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

    public List<DTHPlanItem> getData() {
        return data;
    }

    public void setData(List<DTHPlanItem> data) {
        this.data = data;
    }

    // ---------------------------------

    public static class DTHPlanItem{

        public DTHPlanItem() {
        }

        @SerializedName("category")
        private String category;

        @SerializedName("plan")
        private List<Plan> plan;

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public List<Plan> getPlan() {
            return plan;
        }

        public void setPlan(List<Plan> plan) {
            this.plan = plan;
        }
    }

    public static class Plan{
        public Plan() {
        }

        @SerializedName("plan_name")
        private String plan_name;
        @SerializedName("description")
        private String description;
        @SerializedName("amount")
        private List<Amount> amount;

        public String getPlan_name() {
            return plan_name;
        }

        public void setPlan_name(String plan_name) {
            this.plan_name = plan_name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<Amount> getAmount() {
            return amount;
        }

        public void setAmount(List<Amount> amount) {
            this.amount = amount;
        }
    }

    public static class Amount{
        public Amount() {
        }

        @SerializedName("price")
        private String price;
        @SerializedName("duration")
        private String duration;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }
    }

}
