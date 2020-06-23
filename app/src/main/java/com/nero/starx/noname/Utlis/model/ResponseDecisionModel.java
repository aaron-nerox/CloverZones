package com.nero.starx.noname.Utlis.model;

import com.google.gson.annotations.SerializedName;

public class ResponseDecisionModel {

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;
    @SerializedName("content")
    private String content;
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("wilaya_id")
    private String wilaya;

    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;
    @SerializedName("user")
    private User user;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getWilaya() {
        return wilaya;
    }

    public void setWilaya(String wilaya) {
        this.wilaya = wilaya;
    }

    public ResponseDecisionModel(String id, String title, String content, String user_id, String wilaya, String created_at, String updated_at) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user_id = user_id;
        this.wilaya = wilaya;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public ResponseDecisionModel(String id, String title, String content, String user_id, String wilaya, String created_at, String updated_at, User user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user_id = user_id;
        this.wilaya = wilaya;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.user = user;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
