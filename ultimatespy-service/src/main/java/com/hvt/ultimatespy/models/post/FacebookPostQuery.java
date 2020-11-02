package com.hvt.ultimatespy.models.post;

import java.sql.Timestamp;

public class FacebookPostQuery {
    private Timestamp fromDate;
    private Timestamp toDate;
    private Integer page;
    private Integer pageSize;
    private String keyword;
    private String pixelId;
    private String facebookPageUsername;
    private String category;
    private String type;
    private String country;
    private String language;
    private String website;
    private String platform;
    private Integer minLikes;
    private Integer maxLikes;
    private Integer minComments;
    private Integer maxComments;

    public FacebookPostQuery() {
    }

    public FacebookPostQuery(Timestamp fromDate, Timestamp toDate, Integer page, Integer pageSize, String keyword, String pixelId, String facebookPageUsername, String category, String type, String country, String language, String website, String platform, Integer minLikes, Integer maxLikes, Integer minComments, Integer maxComments) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.page = page;
        this.pageSize = pageSize;
        this.keyword = keyword;
        this.pixelId = pixelId;
        this.facebookPageUsername = facebookPageUsername;
        this.category = category;
        this.type = type;
        this.country = country;
        this.language = language;
        this.website = website;
        this.platform = platform;
        this.minLikes = minLikes;
        this.maxLikes = maxLikes;
        this.minComments = minComments;
        this.maxComments = maxComments;
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

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getPixelId() {
        return pixelId;
    }

    public void setPixelId(String pixelId) {
        this.pixelId = pixelId;
    }

    public String getFacebookPageUsername() {
        return facebookPageUsername;
    }

    public void setFacebookPageId(String facebookPageId) {
        this.facebookPageUsername = facebookPageUsername;
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

    public Integer getMinLikes() {
        return minLikes;
    }

    public void setMinLikes(Integer minLikes) {
        this.minLikes = minLikes;
    }

    public Integer getMaxLikes() {
        return maxLikes;
    }

    public void setMaxLikes(Integer maxLikes) {
        this.maxLikes = maxLikes;
    }

    public Integer getMinComments() {
        return minComments;
    }

    public void setMinComments(Integer minComments) {
        this.minComments = minComments;
    }

    public Integer getMaxComments() {
        return maxComments;
    }

    public void setMaxComments(Integer maxComments) {
        this.maxComments = maxComments;
    }
}
