package com.lovelyfnt.rxjava_retrofit_mvvm;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.lovelyfnt.rxjava_retrofit_mvvm.data.PrivateSharedPreferencesManager;
import com.lovelyfnt.rxjava_retrofit_mvvm.registration.RegistrationFragment;


public class LandingActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(savedInstanceState != null){
            return;
        }

        String userNickname = PrivateSharedPreferencesManager.getInstance(getApplicationContext()).getUserNickname();
        Fragment initialFragment;


        if(userNickname == null || userNickname.isEmpty()){
            Log.d("hello", "world");
            setTitle("Registration");
            initialFragment = new RegistrationFragment();
        }else{
            setTitle("Login");
            Log.d("hello", "world2");
            initialFragment = new RegistrationFragment();
        }

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, initialFragment).commit();
    }
}
