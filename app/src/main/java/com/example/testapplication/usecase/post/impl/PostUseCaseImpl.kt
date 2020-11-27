package com.example.testapplication.usecase.post.impl

import com.example.testapplication.api.PlaceHolderPost
import com.example.testapplication.dataBase.Comment
import com.example.testapplication.dataBase.Post
import com.example.testapplication.repository.CommentRepository
import com.example.testapplication.repository.PostRepository
import com.example.testapplication.usecase.post.PostUseCase
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostUseCaseImpl @Inject constructor(
        private val postRepository: PostRepository,
        private val commentRepository: CommentRepository
) : PostUseCase {
    override fun getPostsFromPlaceHolderApi(id: Int): Single<List<PlaceHolderPost>> =
            postRepository
                    .getPostsFromPlaceHolderAPi()
                    .subscribeOn(Schedulers.io())
                    .toObservable()
                    .flatMapIterable { it }
                    .filter { post -> post.userId == id }.toList()
                    .map {
                        postRepository.insertPosts(it.map { apiPost ->
                            Post(apiPost.userId, apiPost.id, apiPost.title, apiPost.body)
                        })
                        it
                    }
                    .toObservable()
                    .flatMapIterable { it }
                    .flatMap({ post ->
                        commentRepository.getCommentsFromPlaceHolderApi(post.id)
                                .toObservable()
                                .flatMapIterable { it }
                                .map { apiComment ->
                                    Comment(
                                            apiComment.postId,
                                            apiComment.id,
                                            apiComment.name,
                                            apiComment.email,
                                            apiComment.body
                                    )
                                }.toList().toObservable()
                    }, { apiPost, comments ->
                        commentRepository.insertComments(comments)
                        //Post(apiPost.userId, apiPost.id, apiPost.title, apiPost.body)
                        apiPost.comments = comments
                        return@flatMap Observable.just(apiPost)
                    }).flatMap { it }
                    .toList()
    //вставить список в insert
}