package com.example.testapplication.di.module

import com.example.testapplication.ui.post.PostFragment
import com.example.testapplication.ui.user.UserFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeUserFragment(): UserFragment

    @ContributesAndroidInjector
    abstract fun contributePostFragment(): PostFragment
}