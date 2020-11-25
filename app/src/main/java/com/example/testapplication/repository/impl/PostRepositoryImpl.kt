package com.example.testapplication.repository.impl

import com.example.testapplication.api.PlaceHolderApi
import com.example.testapplication.api.PlaceHolderPost
import com.example.testapplication.repository.PostRepository
import io.reactivex.Single
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(private val placeholder: PlaceHolderApi): PostRepository {
    override fun getPosts(): Single<List<PlaceHolderPost>> = placeholder.getPosts()
}