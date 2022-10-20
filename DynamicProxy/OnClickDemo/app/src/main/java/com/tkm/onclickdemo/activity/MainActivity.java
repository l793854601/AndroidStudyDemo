package com.tkm.onclickdemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tkm.onclickdemo.R;
import com.tkm.onclickdemo.util.butterknifelite.OnClick;
import com.tkm.onclickdemo.util.butterknifelite.ButterKnifeLite;
import com.tkm.onclickdemo.util.butterknifelite.OnLongClick;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnifeLite.bind(this);
    }

    @OnClick({R.id.btn1, R.id.btn2})
    private void onButtonClick(View view) {
        if (view.getId() == R.id.btn1) {
            Toast.makeText(this, "Button1 Clicked", Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.btn2) {
            Toast.makeText(this, "Button2 Clicked", Toast.LENGTH_SHORT).show();
        }
    }

    @OnLongClick({R.id.btn1, R.id.btn2})
    private boolean onButtonLongClick(View view) {
        if (view.getId() == R.id.btn1) {
            Toast.makeText(this, "Button1 Long Clicked", Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.btn2) {
            Toast.makeText(this, "Button2 Long Clicked", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}