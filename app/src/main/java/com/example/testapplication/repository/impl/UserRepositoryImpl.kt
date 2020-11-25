package com.example.testapplication.repository.impl

import com.example.testapplication.api.PlaceHolderApi
import com.example.testapplication.api.PlaceHolderUser
import com.example.testapplication.repository.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val placeHolder: PlaceHolderApi
    ): UserRepository {

    override fun getUsers(): Single<List<PlaceHolderUser>> {
        placeHolder.
    }
}