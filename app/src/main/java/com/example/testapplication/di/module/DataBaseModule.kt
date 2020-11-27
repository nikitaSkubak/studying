package com.example.testapplication.di.module

import androidx.room.Room
import com.example.testapplication.api.PlaceHolderApi
import com.example.testapplication.dataBase.TwitRoomDataBase
import com.example.testapplication.dataBase.dao.CommentDao
import com.example.testapplication.dataBase.dao.PostDao
import com.example.testapplication.dataBase.dao.UserDao
import com.example.testapplication.di.MyApplication
import com.example.testapplication.repository.CommentRepository
import com.example.testapplication.repository.PostRepository
import com.example.testapplication.repository.UserRepository
import com.example.testapplication.repository.impl.CommentRepositoryImpl
import com.example.testapplication.repository.impl.PostRepositoryImpl
import com.example.testapplication.repository.impl.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule {
    @Provides
    @Singleton
    fun provideDatabase(application: MyApplication): TwitRoomDataBase {
        return Room.databaseBuilder(application, TwitRoomDataBase::class.java, "dbName")
                .fallbackToDestructiveMigration()
                .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(db: TwitRoomDataBase): UserDao = db.getUserDao()

    @Provides
    @Singleton
    fun providePostDao(db: TwitRoomDataBase): PostDao = db.getPostDao()

    @Provides
    @Singleton
    fun provideCommentDao(db: TwitRoomDataBase): CommentDao = db.getCommentDao()

    @Singleton
    @Provides
    fun provideUserRepository(dao: UserDao, placeHolderApi: PlaceHolderApi): UserRepository =
            UserRepositoryImpl(dao, placeHolderApi)

    @Singleton
    @Provides
    fun providePostRepository(dao: PostDao,placeHolderApi: PlaceHolderApi): PostRepository =
        PostRepositoryImpl(dao, placeHolderApi)

    @Singleton
    @Provides
    fun provideCommentRepository(dao: CommentDao,placeHolderApi: PlaceHolderApi): CommentRepository =
            CommentRepositoryImpl(dao, placeHolderApi)
}