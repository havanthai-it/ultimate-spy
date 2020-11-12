package com.hvt.ultimatespy.models.post;

import java.sql.Timestamp;

public class FacebookPostStatistic {
    private String facebookPostId;
    private Long likes;
    private Long comments;
    private Long shares;
    private Long views;
    private Timestamp date;

    public String getFacebookPostId() {
        return facebookPostId;
    }

    public void setFacebookPostId(String facebookPostId) {
        this.facebookPostId = facebookPostId;
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

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
