package com.tkm.logindemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.tkm.logindemo.R;
import com.tkm.logindemo.util.loginutil.LoginUtil;
import com.tkm.logindemo.util.loginutil.NeedLogin;
import com.tkm.logindemo.util.loginutil.SPUtils;

@NeedLogin
public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
    }

    public void onLogoutClick(View view) {
        LoginUtil.logout(this);
    }
}