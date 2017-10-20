package com.lovelyfnt.rxjava_retrofit_mvvm.registration;

import com.lovelyfnt.rxjava_retrofit_mvvm.networking.AuthenticationRequestManager;

import rx.subjects.AsyncSubject;

/**
 * Created by admin on 2017. 10. 19..
 */

public class RegistrationViewModel {

    private AuthenticationRequestManager authenticationRequestManager;
    private AsyncSubject<Object> registrationSubject;

    public RegistrationViewModel(AuthenticationRequestManager authenticationRequestManager) {
        this.authenticationRequestManager = authenticationRequestManager;
        registrationSubject = AsyncSubject.create();
    }

    public AsyncSubject<Object> createRegistrationSubject(){
        registrationSubject = AsyncSubject.create();
        return registrationSubject;
    }

    public AsyncSubject<Object> getRegistrationSubject(){
        return registrationSubject;
    }

    public void register(){
        authenticationRequestManager.register()
                .subscribe(registrationSubject);
    }
}
