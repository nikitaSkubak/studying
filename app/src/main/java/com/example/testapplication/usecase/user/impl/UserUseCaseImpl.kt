package com.example.testapplication.usecase.user.impl

import com.example.testapplication.dataBase.User
import com.example.testapplication.repository.UserRepository
import com.example.testapplication.usecase.user.UserUseCase
import com.example.testapplication.util.toUser
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserUseCaseImpl @Inject constructor(private val repository: UserRepository) : UserUseCase {

    override fun getUsersFromApiAndSaveThemIntoDB(): Single<List<User>> =
            repository
                    .getUsersFromPlaceHolderApi()
                    .toObservable()
                    .flatMapSingle { repository.insertUsers(it.map { it.toUser() })  }.toList()
                    .flatMap { repository.getUsersFromDB() }
                    .subscribeOn(Schedulers.io())
}