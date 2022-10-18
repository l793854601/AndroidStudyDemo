package com.tkm.activitybuilderdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onTextClicked(View view) {
        Bundle extra = new UserArgsBuilder()
                .setName("TKM")
                .setOwner("JY")
                .setUrl("https://www.google.com")
                .setCreateAt(System.currentTimeMillis())
                .toBundle();
        Intent intent = new Intent(this, UserActivity.class);
        intent.putExtras(extra);
        startActivity(intent);
    }
}