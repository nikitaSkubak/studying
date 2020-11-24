package com.example.testapplication.di.module

import com.example.testapplication.usecase.user.UserUseCase
import com.example.testapplication.usecase.user.impl.UserUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface UseCasesModule {
@Binds
fun bindUserUseCase(userUseCase: UserUseCaseImpl): UserUseCase
}