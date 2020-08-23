package com.hvt.ultimatespy.models.post;

import java.sql.Timestamp;

public class FacebookPostQuery {
    private Timestamp fromDate;
    private Timestamp toDate;
    private int page;
    private int pageSize;
    private String keyword;
    private String category;
    private String type;
    private String country;
    private String language;
    private String ecomSoftware;
    private String ecomPlatform;

    public FacebookPostQuery() {
    }

    public FacebookPostQuery(Timestamp fromDate, Timestamp toDate, int page, int pageSize, String keyword, String category, String type, String country, String language, String ecomSoftware, String ecomPlatform) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.page = page;
        this.pageSize = pageSize;
        this.keyword = keyword;
        this.category = category;
        this.type = type;
        this.country = country;
        this.language = language;
        this.ecomSoftware = ecomSoftware;
        this.ecomPlatform = ecomPlatform;
    }

    public Timestamp getFromDate() {
        return fromDate;
    }

    public void setFromDate(Timestamp fromDate) {
        this.fromDate = fromDate;
    }

    public Timestamp getToDate() {
        return toDate;
    }

    public void setToDate(Timestamp toDate) {
        this.toDate = toDate;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getEcomSoftware() {
        return ecomSoftware;
    }

    public void setEcomSoftware(String ecomSoftware) {
        this.ecomSoftware = ecomSoftware;
    }

    public String getEcomPlatform() {
        return ecomPlatform;
    }

    public void setEcomPlatform(String ecomPlatform) {
        this.ecomPlatform = ecomPlatform;
    }
}
