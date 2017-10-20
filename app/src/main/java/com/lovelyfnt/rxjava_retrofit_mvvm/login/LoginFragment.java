package com.lovelyfnt.rxjava_retrofit_mvvm.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lovelyfnt.rxjava_retrofit_mvvm.HomeActivity;
import com.lovelyfnt.rxjava_retrofit_mvvm.R;
import com.lovelyfnt.rxjava_retrofit_mvvm.base.BaseFragment;
import com.lovelyfnt.rxjava_retrofit_mvvm.data.AuthenticationManager;
import com.lovelyfnt.rxjava_retrofit_mvvm.networking.AuthenticationRequestManager;
import com.lovelyfnt.rxjava_retrofit_mvvm.networking.login.exception.LoginInternalException;
import com.lovelyfnt.rxjava_retrofit_mvvm.networking.login.exception.LoginTechFailureException;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by kh2000park on 2017. 10. 20..
 */

public class LoginFragment extends BaseFragment {

    private LoginViewModel loginViewModel;
    private Subscription loginSubscription;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AuthenticationRequestManager authenticationRequestManager =
                AuthenticationRequestManager.getInstance(getActivity().getApplicationContext());

        loginViewModel = new LoginViewModel(authenticationRequestManager);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        rootView.findViewById(R.id.login).setOnClickListener(view -> {
            AuthenticationManager.getInstance().setPassword("password");
            loginViewModel.login();
            progressDialog = ProgressDialog.show(getActivity(), "Login", "...", true);
        });


        return rootView;
    }

    private void showMessage(String message) {

        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void subscribeForNetworkRequests() {
        loginSubscription = loginViewModel.getLoginSubject()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LoginSubscriber());

    }


    @Override
    protected void unsubscribeForNetworkRequests() {
        if (loginSubscription != null) {
            loginSubscription.unsubscribe();
        }
    }

    @Override
    protected void reconnectWithNetworkRequests() {
        loginSubscription = loginViewModel.createLoginSubject()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LoginSubscriber());
    }

    private void launchHomeActivity() {

        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private class LoginSubscriber extends Subscriber<Object> {
        @Override
        public void onCompleted() {
            hideProgressDialog();
            launchHomeActivity();
        }

        @Override
        public void onError(Throwable e) {
            hideProgressDialog();
            reconnectWithNetworkRequests();

            if (e instanceof LoginInternalException
                    || e instanceof LoginTechFailureException) {

                showMessage("Login Failure");

            }
//            else if (e instanceof AccountTechFailureException) {
//
//                showMessage("Account failed");
//                launchHomeActivity();
//            } else if (e instanceof GamesTechFailureException) {
//
//                showMessage("Games failed");
//                launchHomeActivity();
//            }
        }

        @Override
        public void onNext(Object o) {

        }
    }



}
