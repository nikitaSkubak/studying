package com.example.testapplication.usecase.post

import com.example.testapplication.api.PlaceHolderPost
import io.reactivex.Single

interface PostUseCase {
    fun getPostsFromPlaceHolderApi(id: Int): Single<List<PlaceHolderPost>>
}