package com.example.testapplication.usecase.user.impl

import com.example.testapplication.api.PlaceHolderUser
import com.example.testapplication.dataBase.User
import com.example.testapplication.repository.UserRepository
import com.example.testapplication.usecase.user.UserUseCase
import com.example.testapplication.util.toUser
import com.example.testapplication.util.toUserTest
import io.reactivex.schedulers.Schedulers

class FakeUserUseCase(private val repository: UserRepository): UserUseCase {

    override fun getUsersFromApiAndSaveThemIntoDB() =
        repository
            .getUsersFromPlaceHolderApi()
            .toObservable()
            .flatMapSingle { repository.insertUsers(it.map { it.toUser() }) }.toList()
            .flatMap { repository.getUsersFromDB() }
            .subscribeOn(Schedulers.io())

    fun getUsersFromApi() =
        repository.getUsersFromPlaceHolderApi().subscribeOn(Schedulers.io())

    fun insertUsersIntoDb(userslist: List<User>) =
        repository.insertUsers(userslist).subscribeOn(Schedulers.io())

    fun insertUsersIntoDbWithConverting(userslist: List<PlaceHolderUser>) =
        repository.insertUsers(userslist.map { it.toUserTest() }).subscribeOn(Schedulers.io())

    fun getUsersFromDb() = repository.getUsersFromDB().subscribeOn(Schedulers.io())
}