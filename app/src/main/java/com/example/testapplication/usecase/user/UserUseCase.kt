package com.example.testapplication.usecase.user

import com.example.testapplication.dataBase.User
import io.reactivex.Single

interface UserUseCase {
    fun getUsersFromApiAndSaveThemIntoDB(): Single<List<User>>
}