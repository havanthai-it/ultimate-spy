package com.hvt.ultimatespy.models.post;

import java.sql.Timestamp;

public class FacebookPostQuery {
    private Timestamp fromDate;
    private Timestamp toDate;
    private int page;
    private int pageSize;
    private String keyword;
    private String pixelId;
    private String facebookPageId;
    private String category;
    private String type;
    private String country;
    private String language;
    private String website;
    private String platform;
    private long minLikes;
    private long maxLikes;

    public FacebookPostQuery() {
    }

    public FacebookPostQuery(Timestamp fromDate, Timestamp toDate, int page, int pageSize, String keyword, String pixelId, String facebookPageId, String category, String type, String country, String language, String website, String platform, long minLikes, long maxLikes) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.page = page;
        this.pageSize = pageSize;
        this.keyword = keyword;
        this.pixelId = pixelId;
        this.facebookPageId = facebookPageId;
        this.category = category;
        this.type = type;
        this.country = country;
        this.language = language;
        this.website = website;
        this.platform = platform;
        this.minLikes = minLikes;
        this.maxLikes = maxLikes;
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

    public String getPixelId() {
        return pixelId;
    }

    public void setPixelId(String pixelId) {
        this.pixelId = pixelId;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getFacebookPageId() {
        return facebookPageId;
    }

    public void setFacebookPageId(String facebookPageId) {
        this.facebookPageId = facebookPageId;
    }

    public long getMinLikes() {
        return minLikes;
    }

    public void setMinLikes(long minLikes) {
        this.minLikes = minLikes;
    }

    public long getMaxLikes() {
        return maxLikes;
    }

    public void setMaxLikes(long maxLikes) {
        this.maxLikes = maxLikes;
    }
}
