package com.example.testapplication.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testapplication.main.ViewModelKey
import com.example.testapplication.main.ViewModelProviderFactory
import com.example.testapplication.ui.post.PostViewModel
import com.example.testapplication.ui.user.UserViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    fun  bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(value = UserViewModel::class)
    fun userViewModel(userViewModel: UserViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(value = PostViewModel::class)
    fun postViewModel(postViewModel: PostViewModel): ViewModel
}