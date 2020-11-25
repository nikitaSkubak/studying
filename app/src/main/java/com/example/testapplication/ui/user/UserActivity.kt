package com.example.testapplication.ui.user

import android.os.Bundle
import com.example.testapplication.R
import dagger.android.support.DaggerAppCompatActivity

class UserActivity : DaggerAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}