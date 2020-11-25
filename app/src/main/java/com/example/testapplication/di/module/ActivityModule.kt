package com.example.testapplication.di.module

import com.example.testapplication.ui.post.PostActivity
import com.example.testapplication.ui.user.UserActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeUserActivity(): UserActivity

    @ContributesAndroidInjector
    abstract fun contributePostActivity(): PostActivity
}