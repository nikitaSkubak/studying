package com.example.testapplication.repository

import com.example.testapplication.api.PlaceHolderUser
import com.example.testapplication.dataBase.User
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface UserRepository {
    fun getUsersFromPlaceHolderApi(): Single<List<PlaceHolderUser>>

    fun getUsersFromDB():  Single<List<User>>

    fun insertUsers(users: List<User>): Observable<List<Long>>
}