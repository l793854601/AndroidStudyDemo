package com.tkm.lifecycle.usecase2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.tkm.lifecycle.R

class MainActivity : AppCompatActivity() {

    private val listener: MyListener = MyListener()
    private val listener2: MyListener2 = MyListener2()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycle.addObserver(listener)
        lifecycle.addObserver(listener2)
    }
}