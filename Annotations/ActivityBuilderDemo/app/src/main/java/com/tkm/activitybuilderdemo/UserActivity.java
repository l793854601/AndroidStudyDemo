package com.tkm.activitybuilderdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.tkm.activitybuilder.Builder;
import com.tkm.activitybuilder.Args;

@Builder
public class UserActivity extends AppCompatActivity {
    private static final String TAG = "TAG";

    @Args
    private String name;

    @Args
    private String owner;

    @Args
    private String url;

    @Args
    private long createAt;

    private boolean isNormal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        UserArgsAnalyser.analyse(this);

        Log.d(TAG, "name: " + name +
                ", owner: " + owner +
                ", url: " + url +
                ", createAt: " + createAt);
    }
}