package com.lovelyfnt.rxjava_retrofit_mvvm.networking.login;

import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Header;
import rx.Observable;

/**
 * Created by kh2000park on 2017. 10. 20..
 */
//completed!
public interface ILoginAPI {

    @GET("/login")
    Observable<Response> login(@Header("nickname") String nickname, @Header("password") String password);

}
