package com.tkm.lifecycle.usecase2

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

//  OnLifecycleEvent已经废弃，不建议使用
class MyListener2 : LifecycleEventObserver {
    companion object {
        private const val TAG = "MyListener2"
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        Log.d(TAG, "onStateChanged: $event")
    }
}