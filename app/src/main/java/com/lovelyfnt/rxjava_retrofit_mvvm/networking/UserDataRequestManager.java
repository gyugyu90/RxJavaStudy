package com.lovelyfnt.rxjava_retrofit_mvvm.networking;

import android.content.Context;

import com.lovelyfnt.rxjava_retrofit_mvvm.data.DataManager;
import com.lovelyfnt.rxjava_retrofit_mvvm.model.UserData;
import com.lovelyfnt.rxjava_retrofit_mvvm.networking.account.AccountAPIService;
import com.lovelyfnt.rxjava_retrofit_mvvm.networking.account.AccountRequest;
import com.lovelyfnt.rxjava_retrofit_mvvm.networking.account.AccountResponse;
import com.lovelyfnt.rxjava_retrofit_mvvm.networking.games.GamesAPIService;
import com.lovelyfnt.rxjava_retrofit_mvvm.networking.games.GamesRequest;
import com.lovelyfnt.rxjava_retrofit_mvvm.networking.games.GamesResponse;
import com.lovelyfnt.rxjava_retrofit_mvvm.networking.mock.RestAdapterFactory;

import java.util.Collections;

import retrofit.RestAdapter;
import rx.Observable;

/**
 * Created by kh2000park on 2017. 10. 20..
 */

public class UserDataRequestManager {

    private static UserDataRequestManager instance;

    private DataManager dataManager;

    private AccountAPIService accountAPIService;
    private GamesAPIService gamesAPIService;

    private UserDataRequestManager(Context context){
        dataManager = DataManager.getInstance();

        RestAdapter restAdapter = RestAdapterFactory.getAdapter(context);
        accountAPIService = new AccountAPIService(restAdapter);
        gamesAPIService = new GamesAPIService(restAdapter);
    }

    public static UserDataRequestManager getInstance(Context context){
        synchronized (UserDataRequestManager.class){
            if(instance == null){
                instance = new UserDataRequestManager(context);
            }
            return instance;
        }
    }

    public Observable<Object> getUserData() {

        return Observable.zip(
                getAccount(),
                getGames(),
                this::processUserDataResult);
    }

    private Object processUserDataResult(AccountResponse accountResponse, GamesResponse gamesResponse) {

        UserData userData = dataManager.getUserData();
        // TODO: do this for real
        userData.setAccountInformation("get information from the account response");
        userData.setGames(Collections.EMPTY_LIST);

        return Observable.just(new Object());
    }

    private Observable<AccountResponse> getAccount() {

        return accountAPIService.getAccount(createAccountRequest());
    }

    private Observable<GamesResponse> getGames() {

        return gamesAPIService.getGames(createGamesRequest());
    }

    private GamesRequest createGamesRequest() {

        return new GamesRequest("nickname");
    }

    private AccountRequest createAccountRequest() {

        return new AccountRequest("nickname");
    }
}
