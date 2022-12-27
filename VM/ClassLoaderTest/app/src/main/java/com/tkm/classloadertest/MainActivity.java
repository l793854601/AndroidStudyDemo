package com.tkm.classloadertest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  Android中加载Framework的类的ClassLoader为java.lang.BootClassLoader
        //  区别于Java中的BootStrapClassLoader
        Log.d(TAG, String.class.getClassLoader().toString());
        Log.d(TAG, Activity.class.getClassLoader().toString());
        //  用户自己编写的类，由dalvik.system.PathClassLoader加载
        //  AppCompat不属于Framework，本质为gradle中引入的第三方库
        Log.d(TAG, AppCompatActivity.class.getClassLoader().toString());
        Log.d(TAG, MainActivity.class.getClassLoader().toString());
        //  获取整个程序的ClassLoader：dalvik.system.PathClassLoader
        Log.d(TAG, getClassLoader().toString());
        //  dalvik.system.PathClassLoader的父级类加载器为java.lang.BootClassLoader
        Log.d(TAG, getClassLoader().getParent().toString());
    }
}