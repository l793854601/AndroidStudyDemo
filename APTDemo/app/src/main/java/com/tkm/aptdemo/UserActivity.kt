package com.tkm.aptdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tkm.aptdemo.annotations.Builder
import com.tkm.aptdemo.annotations.Optional
import com.tkm.aptdemo.annotations.Required

@Builder
class UserActivity : AppCompatActivity() {
    @Required
    lateinit var name: String

    @Required
    lateinit var owner: String

    @Optional(stringValue = "")
    lateinit var url: String

    @Optional
    var createAt: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
    }
}