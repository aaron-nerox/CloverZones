package com.nero.starx.noname.model.data;

public class CloverAccount {
    private String mUserName,mEmail,mPhotoURL;

    public CloverAccount(String mUserName, String mEmail, String mPhotoURL) {
        this.mUserName = mUserName;
        this.mEmail = mEmail;
        this.mPhotoURL = mPhotoURL;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmPhotoURL() {
        return mPhotoURL;
    }

    public void setmPhotoURL(String mPhotoURL) {
        this.mPhotoURL = mPhotoURL;
    }
}
