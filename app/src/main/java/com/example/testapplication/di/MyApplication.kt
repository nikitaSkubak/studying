package com.example.testapplication.di

import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class MyApplication: DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<MyApplication> =
        DaggerAppComponent.builder().create(this)

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}