package com.example.coroutineapp.controllers;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.example.coroutineapp.R;
import com.example.coroutineapp.arch.MainScreenViewModel;
import com.example.coroutineapp.models.GithubUser;
import com.example.coroutineapp.utils.InternetUtils;
import dagger.android.AndroidInjection;

import javax.inject.Inject;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // FOR DATA
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private MainScreenViewModel mainScreenViewModel;

    ConstraintLayout rootLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        rootLayout = findViewById(R.id.root_layout);
        this.configureDagger();
        this.configureViewModel();
    }

    // - Configure Dagger2
    private void configureDagger(){
        AndroidInjection.inject(this);
    }

    private void configureViewModel(){
        mainScreenViewModel = ViewModelProviders.of(this, viewModelFactory).get( MainScreenViewModel.class);
        mainScreenViewModel.getFollowersList().observe(this, this::updateLog);
        rootLayout.setOnClickListener(v -> {
            if(InternetUtils.Companion.isInternetAvailable(this)){
                mainScreenViewModel.fetchFollowers();
            } else {
                showSnackBar();
            }
        });
    }

    private void showSnackBar(){
        String message = "No internet connection available";
        Snackbar.make(rootLayout, message, Snackbar.LENGTH_SHORT).show();
    }

    private void updateLog(List<GithubUser> followers){
        for(GithubUser follow : followers){
            Log.e("MainActivity", "Followers = " + follow.getLogin());
        }
    }

    @Override
    protected void onDestroy() {
        mainScreenViewModel.cancelAllRequests();
        super.onDestroy();
    }
}
