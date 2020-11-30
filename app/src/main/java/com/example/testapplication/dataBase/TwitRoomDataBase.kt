package com.example.testapplication.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testapplication.dataBase.dao.CommentDao
import com.example.testapplication.dataBase.dao.PostDao
import com.example.testapplication.dataBase.dao.UserDao

@Database(entities = [User::class, Post::class, Comment::class], version = 2, exportSchema = false)
abstract class TwitRoomDataBase : RoomDatabase() {

    abstract fun getUserDao(): UserDao
    abstract fun getPostDao(): PostDao
    abstract fun getCommentDao(): CommentDao

    companion object {
        private var INSTANCE: TwitRoomDataBase? = null
        fun getDatabase(context: Context): TwitRoomDataBase? {
            if (INSTANCE == null) {
                synchronized(TwitRoomDataBase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                                context.applicationContext,
                                TwitRoomDataBase::class.java,
                                "twit_database")
                                .fallbackToDestructiveMigration()
                                .build()
                    }

                }
            }
            return INSTANCE
        }
    }
}