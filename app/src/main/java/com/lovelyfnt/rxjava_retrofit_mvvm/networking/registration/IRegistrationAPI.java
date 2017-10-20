package com.lovelyfnt.rxjava_retrofit_mvvm.networking.registration;

import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by admin on 2017. 10. 20..
 */

public interface IRegistrationAPI {

    @POST("/registration")
    Observable<RegistrationResponse> register(@Body RegistrationRequest request);



}
