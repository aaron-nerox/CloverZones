package com.nero.starx.noname.Utlis.model;

import com.google.gson.annotations.SerializedName;

public class ResponseEtat {
    @SerializedName("success")
    private String success;
    @SerializedName("wilaya")
    public Wilaya wilaya=new Wilaya();



    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Wilaya getWilaya() {
        return wilaya;
    }

    public void setWilaya(Wilaya wilaya) {
        this.wilaya = wilaya;
    }

   }
