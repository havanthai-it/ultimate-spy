package com.hvt.ultimatespy.models.payment;

import java.sql.Timestamp;

public class Payment {
    private String id;
    private String userId;
    private String paymentMethod;
    private String cardNumber;
    private String cardExpMonth;
    private String cardExpYear;
    private String cardCvc;
    private String cardHolderName;
    private Double amount;
    private Double fee;
    private Double tax;
    private Double discount;
    private Double originAmount;
    private String currency;
    private Timestamp createDate;
    private Timestamp updateDate;
    private String status;
    private String planId;
    private String paypalSubscriptionId;
    private String paypalPlanId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardExpMonth() {
        return cardExpMonth;
    }

    public void setCardExpMonth(String cardExpMonth) {
        this.cardExpMonth = cardExpMonth;
    }

    public String getCardExpYear() {
        return cardExpYear;
    }

    public void setCardExpYear(String cardExpYear) {
        this.cardExpYear = cardExpYear;
    }

    public String getCardCvc() {
        return cardCvc;
    }

    public void setCardCvc(String cardCvc) {
        this.cardCvc = cardCvc;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getOriginAmount() {
        return originAmount;
    }

    public void setOriginAmount(Double originAmount) {
        this.originAmount = originAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getPaypalSubscriptionId() {
        return paypalSubscriptionId;
    }

    public void setPaypalSubscriptionId(String paypalSubscriptionId) {
        this.paypalSubscriptionId = paypalSubscriptionId;
    }

    public String getPaypalPlanId() {
        return paypalPlanId;
    }

    public void setPaypalPlanId(String paypalPlanId) {
        this.paypalPlanId = paypalPlanId;
    }
}
