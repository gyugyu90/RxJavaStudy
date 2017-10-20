package com.lovelyfnt.rxjava_retrofit_mvvm.networking;

import android.content.Context;

import com.lovelyfnt.rxjava_retrofit_mvvm.data.AuthenticationManager;
import com.lovelyfnt.rxjava_retrofit_mvvm.data.PrivateSharedPreferencesManager;
import com.lovelyfnt.rxjava_retrofit_mvvm.networking.login.LoginAPIService;
import com.lovelyfnt.rxjava_retrofit_mvvm.networking.login.LoginRequest;
import com.lovelyfnt.rxjava_retrofit_mvvm.networking.login.LoginResponse;
import com.lovelyfnt.rxjava_retrofit_mvvm.networking.login.exception.LoginInternalException;
import com.lovelyfnt.rxjava_retrofit_mvvm.networking.mock.RestAdapterFactory;
import com.lovelyfnt.rxjava_retrofit_mvvm.networking.registration.RegistrationAPIService;
import com.lovelyfnt.rxjava_retrofit_mvvm.networking.registration.RegistrationRequest;
import com.lovelyfnt.rxjava_retrofit_mvvm.networking.registration.RegistrationResponse;
import com.lovelyfnt.rxjava_retrofit_mvvm.networking.registration.exception.RegistrationInternalException;

import retrofit.RestAdapter;
import rx.Observable;

/**
 * Created by admin on 2017. 10. 20..
 */

public class AuthenticationRequestManager {

    private static AuthenticationRequestManager instance;

    private AuthenticationManager authenticationManager;
    private PrivateSharedPreferencesManager privateSharedPreferencesManager;

    private RegistrationAPIService registrationAPIService;
    private LoginAPIService loginAPIService;


    private UserDataRequestManager userDataRequestManager;

    private AuthenticationRequestManager(Context context){
        this.authenticationManager = AuthenticationManager.getInstance();

        privateSharedPreferencesManager = PrivateSharedPreferencesManager.getInstance(context);
        RestAdapter restAdapter = RestAdapterFactory.getAdapter(context);

        this.registrationAPIService = new RegistrationAPIService(restAdapter, privateSharedPreferencesManager);
        this.loginAPIService = new LoginAPIService(restAdapter, authenticationManager);
        this.userDataRequestManager = UserDataRequestManager.getInstance(context);
    }

    public static AuthenticationRequestManager getInstance(Context context){
        synchronized (AuthenticationRequestManager.class){
            if(instance == null){
                instance = new AuthenticationRequestManager(context);
            }
            return instance;
        }
    }

    public Observable<Object> register() {
        return registrationAPIService.register(createBodyForRegistration())
                .flatMap(this::makeLoginRequest);
    }

    public Observable<Object> login(){
        return loginAPIService.login(createLoginRequest())
                .flatMap(this::makeGetUserDataRequest);
    }

    private Observable<Object> makeLoginRequest(RegistrationResponse registrationResponse){
        return login();
    }


    private Observable<Object> makeGetUserDataRequest(LoginResponse loginResponse){
        return userDataRequestManager.getUserData();
    }

    private RegistrationRequest createBodyForRegistration() {
        String nickname = authenticationManager.getNickname();
        String password = authenticationManager.getPassword();

        if(nickname == null || nickname.isEmpty() || password == null || password.isEmpty()){
            throw new RegistrationInternalException();
        }
        return new RegistrationRequest(nickname, password);
    }

    private LoginRequest createLoginRequest() {

        String nickname = privateSharedPreferencesManager.getUserNickname();
        String password = authenticationManager.getPassword();

        if (nickname == null || nickname.isEmpty() ||
                password == null || password.isEmpty()) {
            throw new LoginInternalException();
        }

        return new LoginRequest(nickname, password);
    }
}
