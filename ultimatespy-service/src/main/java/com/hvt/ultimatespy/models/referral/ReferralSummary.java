package com.hvt.ultimatespy.models.referral;

import java.util.List;

public class ReferralSummary {
    private Integer totalClick;
    private Integer totalSignup;
    private Integer totalPay;
    private List<Referral> payReferrals;

    public ReferralSummary() {
    }

    public ReferralSummary(Integer totalClick, Integer totalSignup, Integer totalPay, List<Referral> payReferrals) {
        this.totalClick = totalClick;
        this.totalSignup = totalSignup;
        this.totalPay = totalPay;
        this.payReferrals = payReferrals;
    }

    public Integer getTotalClick() {
        return totalClick;
    }

    public void setTotalClick(Integer totalClick) {
        this.totalClick = totalClick;
    }

    public Integer getTotalSignup() {
        return totalSignup;
    }

    public void setTotalSignup(Integer totalSignup) {
        this.totalSignup = totalSignup;
    }

    public Integer getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(Integer totalPay) {
        this.totalPay = totalPay;
    }

    public List<Referral> getPayReferrals() {
        return payReferrals;
    }

    public void setPayReferrals(List<Referral> payReferrals) {
        this.payReferrals = payReferrals;
    }
}
