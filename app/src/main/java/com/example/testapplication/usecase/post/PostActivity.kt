package com.example.testapplication.usecase.post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.testapplication.R
import com.example.testapplication.databinding.ActivityPostBinding

class PostActivity : AppCompatActivity() {
    lateinit var binding: ActivityPostBinding
    private val REPLY = "com.example.testapplication.USER"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       binding = DataBindingUtil.setContentView(this, R.layout.activity_post)
       val id = intent.getCharSequenceExtra(REPLY)
    }
}