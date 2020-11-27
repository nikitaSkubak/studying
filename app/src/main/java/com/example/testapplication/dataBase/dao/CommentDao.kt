package com.example.testapplication.dataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testapplication.dataBase.Comment
import io.reactivex.Single

@Dao
abstract class CommentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    @JvmSuppressWildcards
    abstract fun insertComments(comments: List<Comment>): List<Long>

    @Query("select * from comment")
    abstract fun getComments(): Single<List<Comment>>
}