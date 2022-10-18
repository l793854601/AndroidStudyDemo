package com.tkm.activitybuilderdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.tkm.activitybuilder.annotations.Builder;
import com.tkm.activitybuilder.annotations.Optional;
import com.tkm.activitybuilder.annotations.Required;

@Builder
public class UserActivity extends AppCompatActivity {
    private static final String TAG = "TAG";

    @Required
    private String name;

    @Required
    private String owner;

    @Optional(stringValue = "")
    private String url;

    @Optional
    private long createAt;

    @Optional(booleanValue = true)
    private boolean isNormal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Bundle extras = getIntent().getExtras();
        name = extras.getString("NAME");
        owner = extras.getString("OWNER");
        url = extras.getString("URL");
        createAt = extras.getLong("CREATEAT");

        Log.d(TAG, "name: " + name +
                ", owner: " + owner +
                ", url: " + url +
                ", createAt: " + createAt);
    }
}