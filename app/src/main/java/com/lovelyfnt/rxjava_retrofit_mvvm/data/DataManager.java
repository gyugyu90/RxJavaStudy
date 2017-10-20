package com.lovelyfnt.rxjava_retrofit_mvvm.data;

import com.lovelyfnt.rxjava_retrofit_mvvm.model.UserData;

/**
 * Created by kh2000park on 2017. 10. 20..
 */
//completed
public class DataManager {
    private static DataManager instance;
    private UserData userData;

    private DataManager() {
        userData = new UserData();
    }

    public static DataManager getInstance(){
        synchronized (DataManager.class){
            if(instance == null){
                instance = new DataManager();
            }
            return instance;
        }
    }

    public UserData getUserData() {
        return userData;
    }
}
