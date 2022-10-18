package com.tkm.logindemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.tkm.logindemo.R;
import com.tkm.logindemo.util.loginutil.LoginUtil;

import java.lang.reflect.Proxy;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onUserClicked(View view) {
        LoginUtil.startActivity(this, UserActivity.class);
    }

    public void onDetailClicked(View view) {
        LoginUtil.startActivity(this, DetailActivity.class);
    }
}