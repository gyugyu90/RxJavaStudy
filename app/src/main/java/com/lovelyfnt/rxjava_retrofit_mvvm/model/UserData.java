package com.lovelyfnt.rxjava_retrofit_mvvm.model;

import java.util.List;

/**
 * Created by kh2000park on 2017. 10. 20..
 */

public class UserData {
    private List<String> games;
    private String accountInformation;

    public UserData() {
    }

    public List<String> getGames() {
        return games;
    }

    public void setGames(List<String> games) {
        this.games = games;
    }

    public String getAccountInformation() {
        return accountInformation;
    }

    public void setAccountInformation(String accountInformation) {
        this.accountInformation = accountInformation;
    }
}
