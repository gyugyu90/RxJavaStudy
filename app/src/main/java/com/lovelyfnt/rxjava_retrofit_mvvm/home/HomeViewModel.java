package com.lovelyfnt.rxjava_retrofit_mvvm.home;

import com.lovelyfnt.rxjava_retrofit_mvvm.networking.UserDataRequestManager;

import java.util.Random;

import rx.subjects.AsyncSubject;

/**
 * Created by kh2000park on 2017. 10. 20..
 */

public class HomeViewModel {

    private UserDataRequestManager userDataRequestManager;
    private AsyncSubject<Object> userDataSubject;
    private Random random;

    public HomeViewModel(UserDataRequestManager userDataRequestManager) {
        this.userDataRequestManager = userDataRequestManager;
        userDataSubject = AsyncSubject.create();
        random = new Random();
    }

    public AsyncSubject<Object> createUserDataSubject(){
        userDataSubject = AsyncSubject.create();
        return userDataSubject;
    }

    public AsyncSubject<Object> getUserDataSubject(){
        return userDataSubject;
    }

    public void getUserData(){
        userDataRequestManager.getUserData()
                .subscribe(userDataSubject);
    }

    public String generateRandomMessage(){
        double nextRandom = random.nextDouble();
        return Double.toString(nextRandom);
    }
}
