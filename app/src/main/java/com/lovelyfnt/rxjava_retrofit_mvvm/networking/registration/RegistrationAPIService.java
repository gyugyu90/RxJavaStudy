package com.lovelyfnt.rxjava_retrofit_mvvm.networking.registration;

import com.lovelyfnt.rxjava_retrofit_mvvm.data.PrivateSharedPreferencesManager;
import com.lovelyfnt.rxjava_retrofit_mvvm.networking.registration.exception.RegistrationNicknameAlreadyExistsException;
import com.lovelyfnt.rxjava_retrofit_mvvm.networking.registration.exception.RegistrationTechFailureException;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import rx.Observable;

/**
 * Created by admin on 2017. 10. 20..
 */

public class RegistrationAPIService {

    private IRegistrationAPI registrationAPI;
    private PrivateSharedPreferencesManager privateSharedPreferencesManager;
    private boolean isRequestingRegistration;

    public RegistrationAPIService(RestAdapter restAdapter, PrivateSharedPreferencesManager privateSharedPreferencesManager) {
        this.registrationAPI = restAdapter.create(IRegistrationAPI.class);
        this.privateSharedPreferencesManager = privateSharedPreferencesManager;
    }

    public boolean isRequestingRegistration() {
        return isRequestingRegistration;
    }

    public void setRegistrationAPI(IRegistrationAPI registrationAPI) {
        this.registrationAPI = registrationAPI;
    }

    public Observable<RegistrationResponse> register(RegistrationRequest request){
        return registrationAPI.register(request)
                .doOnSubscribe(() -> isRequestingRegistration = true)
                .doOnTerminate(() -> isRequestingRegistration = false)
                .doOnError(this::handleRegistrationError)
                .doOnNext(registrationResponse -> processRegistrationResponse(request, registrationResponse));

    }

    private void handleRegistrationError(Throwable throwable){
        if(throwable instanceof RetrofitError){
            int status = ((RetrofitError) throwable).getResponse().getStatus();

            if(status == 401){
                throw new RegistrationNicknameAlreadyExistsException();
            }else{
                throw new RegistrationTechFailureException();
            }
        }else{
            throw new RegistrationTechFailureException();
        }
    }

    private void processRegistrationResponse(RegistrationRequest registrationRequest, RegistrationResponse registrationResponse){
        privateSharedPreferencesManager.storeUserNickname(registrationRequest.getNickname());
    }


}
