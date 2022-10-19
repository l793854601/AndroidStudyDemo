package com.tkm.retrofitlite_dynamicproxy;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.tkm.retrofitlite_dynamicproxy.util.retrofitlite.Retrofit;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com/")
                .build();
        api = retrofit.create(Api.class);
    }

    public void onGetClick(View view) {
        api.banner(1).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d(TAG, "onGetBannerFailed: " + e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onGetBannerSuccess: " + response.body());
                } else {
                    Log.d(TAG, "onGetBannerFailed, code: " + response.code());
                }
            }
        });
    }

    public void onPostClick(View view) {
        api.login("", "").enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d(TAG, "onLoginFailed: " + e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onLoginSuccess: " + response.body());
                } else {
                    Log.d(TAG, "onLoginFailed, code: " + response.code());
                }
            }
        });
    }
}