package com.lovelyfnt.rxjava_retrofit_mvvm.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lovelyfnt.rxjava_retrofit_mvvm.R;
import com.lovelyfnt.rxjava_retrofit_mvvm.base.BaseFragment;
import com.lovelyfnt.rxjava_retrofit_mvvm.networking.UserDataRequestManager;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by kh2000park on 2017. 10. 20..
 */

public class HomeFragment extends BaseFragment {

    private HomeViewModel homeViewModel;
    private Subscription userDataSubscription;

    private TextView userDataText;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UserDataRequestManager userDataRequestManager =
                UserDataRequestManager.getInstance(getActivity().getApplicationContext());
        homeViewModel = new HomeViewModel(userDataRequestManager);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        userDataText = (TextView)rootView.findViewById(R.id.user_data);
        swipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_refresh);

        showRandomSuccessfulMessage();
        setupRefreshLayout();

        return rootView;
    }

    @Override
    protected void subscribeForNetworkRequests() {
        userDataSubscription = homeViewModel.getUserDataSubject()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new UserDataSubscriber());
    }

    @Override
    protected void unsubscribeForNetworkRequests() {
        if (userDataSubscription != null) {
            userDataSubscription.unsubscribe();
        }
    }

    @Override
    protected void reconnectWithNetworkRequests() {
        userDataSubscription = homeViewModel.createUserDataSubject()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new UserDataSubscriber());
    }

    private void showMessage(String message) {

        // Because we subscribe on the Main Thread, we can do this
        userDataText.setText(message);
    }

    private void showRandomSuccessfulMessage() {
        showMessage(homeViewModel.generateRandomMessage());
    }

    private void setupRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(() -> homeViewModel.getUserData());
    }

    private void hideRefreshLayout() {

        swipeRefreshLayout.setRefreshing(false);
    }

    private class UserDataSubscriber extends Subscriber<Object> {

        @Override
        public void onCompleted() {

            hideRefreshLayout();

            // To be able to pull to refresh (and make another request),
            // we have to reset the Subject in the VM
            reconnectWithNetworkRequests();
        }

        @Override
        public void onError(Throwable e) {

            hideRefreshLayout();

            // To be able to make another UserData request when it fails,
            // we have to reset the Subject in the VM
            reconnectWithNetworkRequests();

            showMessage("Error");
        }

        @Override
        public void onNext(Object userDataResponse) {

            showRandomSuccessfulMessage();
        }
    }
}
