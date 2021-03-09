package com.hvt.ultimatespy.models.referral;

import java.util.List;

public class ReferralSummary {
    private Double totalRevenue;
    private Double totalPaid;
    private Double totalOwed;
    private Integer totalCustomers;
    private Integer totalClicks;
    private Integer totalSignups;
    private List<Referral> payReferrals;

    public ReferralSummary() {
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Double getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(Double totalPaid) {
        this.totalPaid = totalPaid;
    }

    public Double getTotalOwed() {
        return totalOwed;
    }

    public void setTotalOwed(Double totalOwed) {
        this.totalOwed = totalOwed;
    }

    public Integer getTotalCustomers() {
        return totalCustomers;
    }

    public void setTotalCustomers(Integer totalCustomers) {
        this.totalCustomers = totalCustomers;
    }

    public Integer getTotalClicks() {
        return totalClicks;
    }

    public void setTotalClicks(Integer totalClicks) {
        this.totalClicks = totalClicks;
    }

    public Integer getTotalSignups() {
        return totalSignups;
    }

    public void setTotalSignups(Integer totalSignups) {
        this.totalSignups = totalSignups;
    }

    public List<Referral> getPayReferrals() {
        return payReferrals;
    }

    public void setPayReferrals(List<Referral> payReferrals) {
        this.payReferrals = payReferrals;
    }
}
