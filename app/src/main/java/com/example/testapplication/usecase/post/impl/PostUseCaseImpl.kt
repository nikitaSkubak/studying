package com.example.testapplication.usecase.post.impl

import com.example.testapplication.api.PlaceHolderPost
import com.example.testapplication.repository.PostRepository
import com.example.testapplication.usecase.post.PostUseCase
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostUseCaseImpl @Inject constructor(val repository: PostRepository): PostUseCase {
    override fun getPosts(id: Int): Single<List<PlaceHolderPost>> =
    repository.getPosts()
        .map { posts -> posts.filter { post -> post.userId == id } }
        .subscribeOn(Schedulers.io())

//    override fun getPosts(id: Int): Single<List<PlaceHolderPost>> =
//        repository.getPosts()
//            .map { posts -> posts.filter { post -> post.userId == id } }
//            .subscribeOn(Schedulers.io())
}