package com.lovelyfnt.rxjava_retrofit_mvvm.networking.account;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kh2000park on 2017. 10. 20..
 */

public class AccountRequest {

    @SerializedName("nickname") private String nickname;

    public AccountRequest(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
