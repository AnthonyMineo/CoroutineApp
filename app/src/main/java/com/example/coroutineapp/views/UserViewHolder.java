package com.example.coroutineapp.views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.coroutineapp.R;
import com.example.coroutineapp.models.GithubUser;

public class UserViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.login)
    TextView login;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithUser(GithubUser user){
        login.setText(user.getLogin());
    }
}
