package com.example.testapplication.di.module

import com.example.testapplication.api.PlaceHolderApi
import com.example.testapplication.repository.PostRepository
import com.example.testapplication.repository.UserRepository
import com.example.testapplication.repository.impl.PostRepositoryImpl
import com.example.testapplication.repository.impl.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule {
    @Singleton
    @Provides
    fun bindUserRepository(placeHolderApi: PlaceHolderApi): UserRepository =
            UserRepositoryImpl(placeHolderApi)

    @Singleton
    @Provides
    fun bindPostRepository(placeHolderApi: PlaceHolderApi): PostRepository =
        PostRepositoryImpl(placeHolderApi)
}