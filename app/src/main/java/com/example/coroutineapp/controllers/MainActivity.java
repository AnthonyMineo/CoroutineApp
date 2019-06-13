package com.example.coroutineapp.controllers;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.coroutineapp.R;
import com.example.coroutineapp.arch.MainScreenViewModel;
import com.example.coroutineapp.models.GithubUser;
import com.example.coroutineapp.utils.InternetUtils;
import com.example.coroutineapp.views.UserAdapter;
import dagger.android.AndroidInjection;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // FOR DATA
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private MainScreenViewModel mainScreenViewModel;
    private UserAdapter userAdapter;

    @BindView(R.id.root_layout) LinearLayout rootLayout;
    @BindView(R.id.user_entry) EditText userChoice;
    @BindView(R.id.search_button) Button searchButton;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.spinner_progress_bar) ProgressBar spinnerProgress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        this.configureDagger();
        this.configureViewModel();
        this.configureRecyclerView();
        searchButton.setOnClickListener(v -> {
            fetchNetwork();
        });
    }

    // - Configure Dagger2
    private void configureDagger(){
        AndroidInjection.inject(this);
    }

    private void configureViewModel(){
        mainScreenViewModel = ViewModelProviders.of(this, viewModelFactory).get( MainScreenViewModel.class);
        mainScreenViewModel.getFollowersList().observe(this, this::updateLog);
        mainScreenViewModel.getSpinner().observe(this, this::showProgressBar);
    }

    private void showSnackBar(){
        String message = "No internet connection available";
        Snackbar.make(rootLayout, message, Snackbar.LENGTH_SHORT).show();
    }

    private void updateLog(List<GithubUser> followers){
        if(followers == null){
            followers = new ArrayList<>();
        }
        this.userAdapter.updateData(followers);
    }

    private void showProgressBar(Boolean state){
        if (state){
            spinnerProgress.setVisibility(View.VISIBLE);
        } else {
            spinnerProgress.setVisibility(View.GONE);
        }
    }

    private void configureRecyclerView(){
        this.userAdapter = new UserAdapter();
        this.recyclerView.setAdapter(userAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void fetchNetwork(){
        if(InternetUtils.Companion.isInternetAvailable(this)){
            String user = userChoice.getText().toString();
            if(!user.equals("")){
                mainScreenViewModel.fetchFollowers(user);
            }
        } else {
            showSnackBar();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userAdapter.cancelAllRequests();
    }
}
