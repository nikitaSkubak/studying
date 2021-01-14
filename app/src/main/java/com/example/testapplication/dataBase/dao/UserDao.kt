package com.example.testapplication.dataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testapplication.dataBase.User
import io.reactivex.Single

@Dao
abstract class UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    @JvmSuppressWildcards
    abstract fun insertUsers(user: List<User>): Single<List<Long>>

    @Query("select * from User")
    abstract fun getUsers(): Single<List<User>>
}