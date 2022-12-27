package com.tkm.hotfixdemo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.tkm.hotfixdemo.R;
import com.tkm.hotfixdemo.utils.Calculator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvShow = findViewById(R.id.tv_show);
        TextView tvVersion = findViewById(R.id.tv_version);

        tvShow.setText(Calculator.showResult());
        tvVersion.setText(Calculator.version());
    }
}