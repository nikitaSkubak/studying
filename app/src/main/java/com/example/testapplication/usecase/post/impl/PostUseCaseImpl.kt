package com.example.testapplication.usecase.post.impl

import com.example.testapplication.api.PlaceHolderPost
import com.example.testapplication.repository.PostRepository
import com.example.testapplication.usecase.post.PostUseCase
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostUseCaseImpl @Inject constructor(val repository: PostRepository) : PostUseCase {
    override fun getPosts(id: Int): Single<List<PlaceHolderPost>> =
            repository
                    .getPosts()
                    .subscribeOn(Schedulers.io())
                    .toObservable()
                    .flatMapIterable { it }
                    .filter { post -> post.userId == id }
                    .flatMap({ post ->
                        repository.getComments(post.id).toObservable()
                    }, { post, comments ->
                        post.comments = comments
                        return@flatMap Observable.just(post)
                    }).flatMap { it }
                    .toList()

}