package com.example.testapplication.repository

import com.example.testapplication.api.PlaceHolderUser
import io.reactivex.Single

interface UserRepository {
    fun getUsers(): Single<List<PlaceHolderUser>>
}