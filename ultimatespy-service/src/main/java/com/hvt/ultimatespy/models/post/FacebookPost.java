package com.hvt.ultimatespy.models.post;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class FacebookPost {
    @SerializedName(value = "s_id")
    private String id;

    @SerializedName(value = "s_post_id")
    private String postId;

    @SerializedName(value = "s_ads_id")
    private String adsId;

    @SerializedName(value = "s_pixel_id")
    private String pixelId;

    @SerializedName(value = "s_facebook_page_username")
    private String facebookPageUsername;

    @SerializedName(value = "s_images")
    private String images;

    @SerializedName(value = "s_videos")
    private String videos;

    @SerializedName(value = "s_content")
    private String content;

    @SerializedName(value = "s_type")
    private String type;

    @SerializedName(value = "s_category")
    private String category;

    @SerializedName(value = "s_country")
    private String country;

    @SerializedName(value = "s_language")
    private String language;

    @SerializedName(value = "n_likes")
    private Long likes;

    @SerializedName(value = "n_comments")
    private Long comments;

    @SerializedName(value = "n_shares")
    private Long shares;

    @SerializedName(value = "n_views")
    private Long views;

    @SerializedName(value = "s_status")
    private String status;

    @SerializedName(value = "s_links")
    private String links;

    @SerializedName(value = "s_website")
    private String website;

    @SerializedName(value = "s_platform")
    private String platform;

    @SerializedName(value = "d_publish")
    private Timestamp publishDate;

    @SerializedName(value = "d_create")
    private Timestamp createDate;

    @SerializedName(value = "d_update")
    private Timestamp updateDate;

    // Page information
    @SerializedName(value = "s_page_name")
    private String pageName;

    @SerializedName(value = "s_page_username")
    private String pageUsername;

    @SerializedName(value = "s_page_thumbnail")
    private String pageThumbnail;

    @SerializedName(value = "s_page_category")
    private String pageCategory;

    @SerializedName(value = "n_page_likes")
    private Long pageLikes;

    @SerializedName(value = "n_page_follows")
    private Long pageFollows;

    @SerializedName(value = "d_page_publish")
    private Timestamp pagePublishDate;

    // Statistic
    private Float lastAvgTrack;
    private Float lastLikeTrack;
    private Float lastCommentTrack;
    private Float lastShareTrack;
    private List<Map<String, Object>> statistics;
    private String searchType;
    private boolean tracked;
    private boolean saved;

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

    public String getFacebookPageUsername() {
        return facebookPageUsername;
    }

    public void setFacebookPageUsername(String facebookPageUsername) {
        this.facebookPageUsername = facebookPageUsername;
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

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public Long getComments() {
        return comments;
    }

    public void setComments(Long comments) {
        this.comments = comments;
    }

    public Long getShares() {
        return shares;
    }

    public void setShares(Long shares) {
        this.shares = shares;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
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

    public String getPageCategory() {
        return pageCategory;
    }

    public void setPageCategory(String pageCategory) {
        this.pageCategory = pageCategory;
    }

    public Long getPageLikes() {
        return pageLikes;
    }

    public void setPageLikes(Long pageLikes) {
        this.pageLikes = pageLikes;
    }

    public Long getPageFollows() {
        return pageFollows;
    }

    public void setPageFollows(Long pageFollows) {
        this.pageFollows = pageFollows;
    }

    public Timestamp getPagePublishDate() {
        return pagePublishDate;
    }

    public void setPagePublishDate(Timestamp pagePublishDate) {
        this.pagePublishDate = pagePublishDate;
    }

    public Float getLastAvgTrack() {
        return lastAvgTrack;
    }

    public void setLastAvgTrack(Float lastAvgTrack) {
        this.lastAvgTrack = lastAvgTrack;
    }

    public Float getLastLikeTrack() {
        return lastLikeTrack;
    }

    public void setLastLikeTrack(Float lastLikeTrack) {
        this.lastLikeTrack = lastLikeTrack;
    }

    public Float getLastCommentTrack() {
        return lastCommentTrack;
    }

    public void setLastCommentTrack(Float lastCommentTrack) {
        this.lastCommentTrack = lastCommentTrack;
    }

    public Float getLastShareTrack() {
        return lastShareTrack;
    }

    public void setLastShareTrack(Float lastShareTrack) {
        this.lastShareTrack = lastShareTrack;
    }

    public List<Map<String, Object>> getStatistics() {
        return statistics;
    }

    public void setStatistics(List<Map<String, Object>> statistics) {
        this.statistics = statistics;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public boolean isTracked() {
        return tracked;
    }

    public void setTracked(boolean tracked) {
        this.tracked = tracked;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }
}
