package com.example.testapplication.usecase.user.impl

import com.example.testapplication.dataBase.User
import com.example.testapplication.repository.UserRepository
import com.example.testapplication.usecase.user.UserUseCase
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserUseCaseImpl @Inject constructor(private val repository: UserRepository) : UserUseCase {

    override fun getUsersFromApiAndSaveThemIntoDB(): Single<List<User>> =
            repository
                    .getUsersFromPlaceHolderApi()
                    .toObservable()
                    .flatMapIterable { it }
                    .map {
                        val user = User(
                                id = it.id,
                                name = it.name,
                                username = it.username,
                                email = it.email,
                                address = it.address.toString()
                        )
                        user
                    }.toList().map {
                        repository.insertUsers(it)
                        it
                    }.subscribeOn(Schedulers.io())

}