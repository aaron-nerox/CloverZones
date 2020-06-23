package com.nero.starx.noname.Utlis.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseDecisions {
    @SerializedName("success")
    private String success;
    @SerializedName("content")
    private List<ResponseDecisionModel> Decisions;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<ResponseDecisionModel> getDecisions() {
        return Decisions;
    }

    public void setDecisions(List<ResponseDecisionModel> decisions) {
        Decisions = decisions;
    }
}
