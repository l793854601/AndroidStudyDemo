package com.tkm.lifecycle.usecase1

import android.util.Log

class MyListener {
    companion object {
        private const val TAG = "MyListener"
    }

    fun create() = Log.d(TAG, "create")

    fun start() = Log.d(TAG, "start")

    fun resume() = Log.d(TAG, "resume")

    fun pause() = Log.d(TAG, "pause")

    fun stop() = Log.d(TAG, "stop")

    fun destroy() = Log.d(TAG, "destroy")
}