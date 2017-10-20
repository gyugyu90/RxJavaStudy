package com.lovelyfnt.rxjava_retrofit_mvvm.networking.mock;

import android.content.Context;

import retrofit.RestAdapter;

/**
 * Created by admin on 2017. 10. 20..
 */

public class RestAdapterFactory {

    public static RestAdapter getAdapter(Context context){
        return createAdapter(context);
    }

    private static RestAdapter createAdapter(Context context){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setClient(new MockHttpClient(context))
                .setEndpoint("mock")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        return restAdapter;
    }

}
