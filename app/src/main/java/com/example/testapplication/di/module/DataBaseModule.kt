package com.example.testapplication.di.module

import com.example.testapplication.repository.UserRepository
import com.example.testapplication.repository.impl.UserRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface DataBaseModule {
    @Binds
    fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository
}