package com.example.testapplication.repository.impl

import com.example.testapplication.api.PlaceHolderApi
import com.example.testapplication.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val placeHolder: PlaceHolderApi
    ): UserRepository {
}