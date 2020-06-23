package com.nero.starx.noname.Utlis.model;

import com.google.gson.annotations.SerializedName;

public class Wilaya {
    @SerializedName("id")
    public String id;
    @SerializedName("name")
    public String name;
    @SerializedName("etat")
    public int etat;
    @SerializedName("created_at")
    public String created_at;
    @SerializedName("updated_at")
    public String updated_at;

    public Wilaya() {
    }

    public Wilaya(String id, String name,int etat) {
        this.id = id;
        this.name = name;
        this.etat = etat;
    }

    public Wilaya(String id, String name, int etat, String created_at, String updated_at) {
        this.id = id;
        this.name = name;
        this.etat = etat;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
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