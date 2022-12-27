package com.tkm.lifecycle.usecase1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tkm.lifecycle.R

class MainActivity : AppCompatActivity() {

    private val listener = MyListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listener.create()
    }

    override fun onStart() {
        super.onStart()
        listener.start()
    }

    override fun onResume() {
        super.onResume()
        listener.resume()
    }

    override fun onPause() {
        super.onPause()
        listener.pause()
    }

    override fun onStop() {
        super.onStop()
        listener.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        listener.destroy()
    }
}