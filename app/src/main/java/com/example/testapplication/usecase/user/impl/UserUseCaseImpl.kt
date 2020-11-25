package com.example.testapplication.usecase.user.impl

import com.example.testapplication.api.PlaceHolderUser
import com.example.testapplication.repository.UserRepository
import com.example.testapplication.usecase.user.UserUseCase
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserUseCaseImpl @Inject constructor(private val repository: UserRepository): UserUseCase {

    override fun getUsers(): Single<List<PlaceHolderUser>> =
        repository.getUsers().subscribeOn(Schedulers.io())

}