package com.lovelyfnt.rxjava_retrofit_mvvm.base;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;

/**
 * Created by admin on 2017. 10. 19..
 */

public abstract class BaseFragment extends Fragment {

    protected ProgressDialog progressDialog;

    @Override
    public void onResume() {
        super.onResume();
        subscribeForNetworkRequests();
    }

    @Override
    public void onPause() {
        super.onPause();
        unsubscribeForNetworkRequests();
    }

    protected void hideProgressDialog(){
        if(progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    protected abstract void subscribeForNetworkRequests();
    protected abstract void unsubscribeForNetworkRequests();
    protected abstract void reconnectWithNetworkRequests();


}
