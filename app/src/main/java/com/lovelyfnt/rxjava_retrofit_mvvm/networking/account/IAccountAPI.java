package com.lovelyfnt.rxjava_retrofit_mvvm.networking.account;

import retrofit.http.GET;
import retrofit.http.Header;
import rx.Observable;

/**
 * Created by kh2000park on 2017. 10. 20..
 */
//completed
public interface IAccountAPI {

    @GET("/account")
    Observable<AccountResponse> getAccountInformation(@Header("nickname") String nickname);

}
