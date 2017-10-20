package com.lovelyfnt.rxjava_retrofit_mvvm.login;

import com.lovelyfnt.rxjava_retrofit_mvvm.networking.AuthenticationRequestManager;

import rx.subjects.AsyncSubject;

/**
 * Created by kh2000park on 2017. 10. 20..
 */

public class LoginViewModel {

    private AuthenticationRequestManager authenticationRequestManager;
    private AsyncSubject<Object> loginSubject;

    public LoginViewModel(AuthenticationRequestManager authenticationRequestManager) {
        this.authenticationRequestManager = authenticationRequestManager;
        loginSubject = AsyncSubject.create();
    }

    public AsyncSubject<Object> createLoginSubject(){
        loginSubject = AsyncSubject.create();
        return loginSubject;
    }

    public AsyncSubject<Object> getLoginSubject(){
        return loginSubject;
    }

    public void login(){
        authenticationRequestManager.login()
                .subscribe(loginSubject);
    }
}
