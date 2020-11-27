package com.example.testapplication.dataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testapplication.dataBase.Post
import io.reactivex.Single

@Dao
abstract class PostDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    @JvmSuppressWildcards
    abstract fun insertPosts(listOfUsers: List<Post>): List<Long>

    @Query("select * from post")
    abstract fun getPosts(): Single<List<Post>>
}