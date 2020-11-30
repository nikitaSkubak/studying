package com.example.testapplication.repository.impl

import com.example.testapplication.api.PlaceHolderApi
import com.example.testapplication.api.PlaceHolderPost
import com.example.testapplication.dataBase.Post
import com.example.testapplication.dataBase.dao.PostDao
import com.example.testapplication.repository.PostRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
        private val dao: PostDao,
        private val placeholder: PlaceHolderApi
) : PostRepository {

    override fun getPostsFromPlaceHolderAPi(): Single<List<PlaceHolderPost>> =
            placeholder.getPosts()

    override fun insertPosts(posts: List<Post>) = dao.insertPosts(posts)
}