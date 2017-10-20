package com.lovelyfnt.rxjava_retrofit_mvvm.networking.login;

/**
 * Created by kh2000park on 2017. 10. 20..
 */

public class LoginResponse {

    private int loginStatusResponse;

    public LoginResponse() {
    }

    public int getLoginStatusResponse() {
        return loginStatusResponse;
    }

    public void setLoginStatusResponse(int loginStatusResponse) {
        this.loginStatusResponse = loginStatusResponse;
    }
}
