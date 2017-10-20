package com.lovelyfnt.rxjava_retrofit_mvvm.networking.games;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kh2000park on 2017. 10. 20..
 */

public class GamesRequest {

    @SerializedName("nickname") private String nickname;

    public GamesRequest(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
