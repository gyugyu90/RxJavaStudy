package com.lovelyfnt.rxjava_retrofit_mvvm.registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lovelyfnt.rxjava_retrofit_mvvm.HomeActivity;
import com.lovelyfnt.rxjava_retrofit_mvvm.R;
import com.lovelyfnt.rxjava_retrofit_mvvm.base.BaseFragment;
import com.lovelyfnt.rxjava_retrofit_mvvm.data.AuthenticationManager;
import com.lovelyfnt.rxjava_retrofit_mvvm.networking.AuthenticationRequestManager;
import com.lovelyfnt.rxjava_retrofit_mvvm.networking.registration.exception.RegistrationInternalException;
import com.lovelyfnt.rxjava_retrofit_mvvm.networking.registration.exception.RegistrationNicknameAlreadyExistsException;
import com.lovelyfnt.rxjava_retrofit_mvvm.networking.registration.exception.RegistrationTechFailureException;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by admin on 2017. 10. 19..
 */

public class RegistrationFragment extends BaseFragment{

    private RegistrationViewModel registrationViewModel;
    private Subscription registrationSubscription;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AuthenticationRequestManager authenticationRequestManager = AuthenticationRequestManager.getInstance(getActivity().getApplicationContext());
        registrationViewModel = new RegistrationViewModel(authenticationRequestManager);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_registration, container, false);

        rootView.findViewById(R.id.register).setOnClickListener(this::registerButtonTap);

        return rootView;
    }

    @Override
    protected void subscribeForNetworkRequests() {
        registrationSubscription = registrationViewModel.getRegistrationSubject()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RegistrationSubscriber());
    }

    @Override
    protected void unsubscribeForNetworkRequests() {
        if(registrationSubscription != null){
            registrationSubscription.unsubscribe();
        }
    }

    @Override
    protected void reconnectWithNetworkRequests() {
        registrationSubscription = registrationViewModel.createRegistrationSubject()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RegistrationSubscriber());
    }

    private void showMessage(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private void launchHomeActivity(){
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    public void registerButtonTap(View view){
        Log.d("register" , "button tap");
        AuthenticationManager.getInstance().setNickname("nickname");
        AuthenticationManager.getInstance().setPassword("password");
        registrationViewModel.register();

        progressDialog = ProgressDialog.show(getActivity(), "Registering", "...", true);

    }



    private class RegistrationSubscriber extends Subscriber<Object> {
        @Override
        public void onCompleted() {
            hideProgressDialog();
            launchHomeActivity();
        }

        @Override
        public void onError(Throwable e) {
            hideProgressDialog();
            reconnectWithNetworkRequests();

            if(e instanceof RegistrationInternalException || e instanceof RegistrationTechFailureException){
                showMessage("Registration Failure");
            }else if(e instanceof RegistrationNicknameAlreadyExistsException){
                showMessage("Registration Nickname already exists");
            }
//            else if(e instanceof Login)
        }

        @Override
        public void onNext(Object o) {

        }
    }



}
