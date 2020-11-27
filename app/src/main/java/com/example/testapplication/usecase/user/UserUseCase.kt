package com.example.testapplication.usecase.user

import com.example.testapplication.dataBase.User
import io.reactivex.Single

interface UserUseCase {
    /**
     * Gets list of PlaceHolderUser from PlaceHolderApi, converts them into user Object and
     * inserts them into DataBase table `user`.
     * @return Single that contains list of User`s.
     **/
    fun getUsersFromApiAndSaveThemIntoDB(): Single<List<User>>
}