package com.tkm.annotationruntimedemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tkm.annotationruntimedemo.R;
import com.tkm.annotationruntimedemo.model.Dog;
import com.tkm.annotationruntimedemo.model.Lady;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButtonClicked(View view) {
        Intent intent = new Intent(this, SecondActivity.class)
                .putExtra("aInt", 10)
                .putExtra("aBoolean", true)
                .putExtra("aLong", Long.MAX_VALUE)
                .putExtra("aString", "Hello")
                .putExtra("lady", new Lady("Jenny", 19))
                .putExtra("dog", new Dog("Kirky"))
                .putExtra("dogs", new Dog[] { new Dog("Husky"), new Dog("Teddy") });
        startActivity(intent);
    }
}