package com.example.testapplication.repository.impl

import com.example.testapplication.api.PlaceHolderApi
import com.example.testapplication.api.PlaceHolderComment
import com.example.testapplication.api.PlaceHolderPost
import com.example.testapplication.dataBase.Post
import com.example.testapplication.dataBase.dao.CommentDao
import com.example.testapplication.dataBase.dao.PostDao
import com.example.testapplication.repository.PostRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
        private val dao: PostDao,
        private val placeholder: PlaceHolderApi
) : PostRepository {

    override fun getPosts(): Single<List<PlaceHolderPost>> =
            placeholder.getPosts()

    override fun insertPosts(posts: List<Post>) = Observable.just(dao.insertPosts(posts))

    override fun convertListOFPlaceHolderPostToListOfPost(
            listOfPlaceHolderPost: List<PlaceHolderPost>
    ) = listOfPlaceHolderPost.map { apiPost ->
        Post(apiPost.userId, apiPost.id, apiPost.title, apiPost.body)
    }


}