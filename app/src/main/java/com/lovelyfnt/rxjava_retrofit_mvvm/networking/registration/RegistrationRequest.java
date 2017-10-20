package com.lovelyfnt.rxjava_retrofit_mvvm.networking.registration;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 2017. 10. 20..
 */

public class RegistrationRequest {

    @SerializedName("nickname") private String nickname;
    @SerializedName("password") private String password;

    public RegistrationRequest(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }
}
