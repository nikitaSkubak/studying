package com.example.testapplication.usecase.user.impl

import com.example.testapplication.repository.UserRepository
import com.example.testapplication.usecase.user.UserUseCase
import javax.inject.Inject

class UserUseCaseImpl @Inject constructor(private val repository: UserRepository): UserUseCase {
}