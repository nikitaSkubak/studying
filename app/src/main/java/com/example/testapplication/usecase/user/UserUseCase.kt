package com.example.testapplication.usecase.user

import com.example.testapplication.api.PlaceHolderUser
import io.reactivex.Single

interface UserUseCase {
    fun getUsers(): Single<List<PlaceHolderUser>>
}