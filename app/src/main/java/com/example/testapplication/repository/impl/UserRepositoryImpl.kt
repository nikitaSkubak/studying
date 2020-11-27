package com.example.testapplication.repository.impl

import com.example.testapplication.api.PlaceHolderApi
import com.example.testapplication.api.PlaceHolderUser
import com.example.testapplication.dataBase.User
import com.example.testapplication.dataBase.dao.UserDao
import com.example.testapplication.repository.UserRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class UserRepositoryImpl
@Inject constructor(
        private val dao: UserDao,
        private val placeHolder: PlaceHolderApi
) : UserRepository {

    override fun getUsersFromPlaceHolderApi(): Single<List<PlaceHolderUser>> =
            placeHolder.getUsers()

    override fun insertUsers(users: List<User>) = Observable.just(dao.insertUsers(users))
}