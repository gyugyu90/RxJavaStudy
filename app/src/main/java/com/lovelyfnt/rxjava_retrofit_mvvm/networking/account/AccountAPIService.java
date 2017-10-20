package com.lovelyfnt.rxjava_retrofit_mvvm.networking.account;

import com.lovelyfnt.rxjava_retrofit_mvvm.networking.account.exception.AccountTechFailureException;
import com.lovelyfnt.rxjava_retrofit_mvvm.networking.login.LoginResponse;

import retrofit.RestAdapter;
import rx.Observable;

/**
 * Created by kh2000park on 2017. 10. 20..
 */

public class AccountAPIService {

    private IAccountAPI accountAPI;
    private boolean isRequestingAccount;

    public AccountAPIService(RestAdapter restAdapter){
        this.accountAPI = restAdapter.create(IAccountAPI.class);
    }

    public boolean isRequestingAccount() {
        return isRequestingAccount;
    }

    public Observable<AccountResponse> getAccount(AccountRequest request){
        return accountAPI.getAccountInformation(request.getNickname())
                .doOnSubscribe(() -> isRequestingAccount = true)
                .doOnTerminate(() -> isRequestingAccount = false)
                .doOnError(this::handleAccountError);
    }


    private void handleAccountError(Throwable throwable){
        throw new AccountTechFailureException();
    }
}
