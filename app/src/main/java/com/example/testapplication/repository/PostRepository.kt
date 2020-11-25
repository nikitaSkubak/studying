package com.example.testapplication.repository

import com.example.testapplication.api.PlaceHolderPost
import io.reactivex.Single

interface PostRepository {
    fun getPosts(): Single<List<PlaceHolderPost>>
}