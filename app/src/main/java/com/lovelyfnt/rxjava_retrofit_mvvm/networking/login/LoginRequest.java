package com.lovelyfnt.rxjava_retrofit_mvvm.networking.login;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kh2000park on 2017. 10. 20..
 */

public class LoginRequest {
    @SerializedName("nickname") private String nickname;
    @SerializedName("password") private String password;

    public LoginRequest(String nickname, String password) {
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
