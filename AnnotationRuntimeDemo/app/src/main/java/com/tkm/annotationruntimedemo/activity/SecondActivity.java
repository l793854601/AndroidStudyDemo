package com.tkm.annotationruntimedemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.tkm.annotationruntimedemo.R;
import com.tkm.annotationruntimedemo.model.Dog;
import com.tkm.annotationruntimedemo.model.Lady;
import com.tkm.annotationruntimedemo.util.BindField;
import com.tkm.annotationruntimedemo.util.InjectUtil;

import java.util.Arrays;

public class SecondActivity extends AppCompatActivity {
    private static final String TAG = "SecondActivity";

    @BindField
    private int aInt;

    @BindField
    private boolean aBoolean;

    @BindField
    private long aLong;

    @BindField("aString")
    private String word;

    @BindField
    private Lady lady;

    @BindField
    private Dog dog;

    @BindField
    private Dog[] dogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        InjectUtil.inject(this);

        Log.d(TAG, "onCreate: " + "SecondActivity{" +
                "aInt=" + aInt +
                ", aBoolean=" + aBoolean +
                ", aLong=" + aLong +
                ", word='" + word + '\'' +
                ", lady=" + lady +
                ", dog=" + dog +
                ", dogs=" + Arrays.toString(dogs) +
                '}');
    }
}