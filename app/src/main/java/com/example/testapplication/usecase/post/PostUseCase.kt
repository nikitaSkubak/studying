package com.example.testapplication.usecase.post

import com.example.testapplication.api.PlaceHolderPost
import io.reactivex.Single

interface PostUseCase {
    fun getPosts(id: Int): Single<List<PlaceHolderPost>>
}