package com.example.testapplication.util.test

import androidx.lifecycle.LiveData
import com.example.testapplication.util.test.TestObserver.test

fun <T> LiveData<T>.test(): TestObserver<T> {
    return TestObserver.test(this)
}