package com.hvt.ultimatespy.models.post;

import java.sql.Timestamp;

public class FacebookPost {
    private String id;
    private String postId;
    private String adsId;
    private String pixelId;
    private String facebookPageId;
    private String images;
    private String videos;
    private String content;
    private String type;
    private String category;
    private String country;
    private String language;
    private long likes;
    private long comments;
    private long shares;
    private long views;
    private String status;
    private String links;
    private String website;
    private String platform;
    private Timestamp publishDate;
    private Timestamp createDate;
    private Timestamp updateDate;

    // Page information
    private String pageName;
    private String pageUsername;
    private String pageThumbnail;
    private long pageLikes;
    private long pageFollows;
    private Timestamp pagePublishDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getAdsId() {
        return adsId;
    }

    public void setAdsId(String adsId) {
        this.adsId = adsId;
    }

    public String getPixelId() {
        return pixelId;
    }

    public void setPixelId(String pixelId) {
        this.pixelId = pixelId;
    }

    public String getFacebookPageId() {
        return facebookPageId;
    }

    public void setFacebookPageId(String facebookPageId) {
        this.facebookPageId = facebookPageId;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getVideos() {
        return videos;
    }

    public void setVideos(String videos) {
        this.videos = videos;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public long getComments() {
        return comments;
    }

    public void setComments(long comments) {
        this.comments = comments;
    }

    public long getShares() {
        return shares;
    }

    public void setShares(long shares) {
        this.shares = shares;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
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

    public Timestamp getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Timestamp publishDate) {
        this.publishDate = publishDate;
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

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPageUsername() {
        return pageUsername;
    }

    public void setPageUsername(String pageUsername) {
        this.pageUsername = pageUsername;
    }

    public String getPageThumbnail() {
        return pageThumbnail;
    }

    public void setPageThumbnail(String pageThumbnail) {
        this.pageThumbnail = pageThumbnail;
    }

    public long getPageLikes() {
        return pageLikes;
    }

    public void setPageLikes(long pageLikes) {
        this.pageLikes = pageLikes;
    }

    public long getPageFollows() {
        return pageFollows;
    }

    public void setPageFollows(long pageFollows) {
        this.pageFollows = pageFollows;
    }

    public Timestamp getPagePublishDate() {
        return pagePublishDate;
    }

    public void setPagePublishDate(Timestamp pagePublishDate) {
        this.pagePublishDate = pagePublishDate;
    }
}
