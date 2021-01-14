package com.example.testapplication.repository

import com.example.testapplication.api.PlaceHolderUser
import com.example.testapplication.dataBase.User
import io.reactivex.Observable
import io.reactivex.Single

interface UserRepository {
    /**
     *Returns list of PlaceHolderUser`s from PlaceHolderApi
     * @return Single that contains List<PlaceHolderUser>.
     **/
    fun getUsersFromPlaceHolderApi(): Single<List<PlaceHolderUser>>
    /**
     *Inserts list of PlaceHolderUser`s into DataBase
     * @return Observable that contains `id` list of successfully inserted `users`.
     * @param users List<User> to set the list of users into DataBase table `user`.
     **/
    fun insertUsers(users: List<User>): Single<List<Long>>
    /**
     *Returns list of User`s from DataBase
     * @return Single that contains User list.
     **/
    fun getUsersFromDB(): Single<List<User>>
}