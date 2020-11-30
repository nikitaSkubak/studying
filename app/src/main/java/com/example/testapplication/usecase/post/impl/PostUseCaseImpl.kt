package com.example.testapplication.usecase.post.impl

import com.example.testapplication.api.PlaceHolderPost
import com.example.testapplication.repository.CommentRepository
import com.example.testapplication.repository.PostRepository
import com.example.testapplication.usecase.post.PostUseCase
import com.example.testapplication.util.toComment
import com.example.testapplication.util.toPost
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
                    .filter { it.userId == id }.toList()
                    .map { listOfApiPosts ->
                        postRepository.insertPosts(listOfApiPosts.map { it.toPost() })
                        listOfApiPosts
                    }
                    .toObservable()
                    .flatMapIterable { it }
                    .flatMap({ post ->
                        commentRepository.getCommentsFromPlaceHolderApi(post.id)
                                .toObservable()
                    }, { apiPost, comments ->
                        val commentsToDb = comments.map { it.toComment() }
                        commentRepository.insertComments(commentsToDb)
                        apiPost.comments = commentsToDb
                        return@flatMap Observable.just(apiPost)
                    }).flatMap { it }
                    .toList()
}