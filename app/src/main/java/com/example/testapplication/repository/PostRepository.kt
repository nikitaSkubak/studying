package com.example.testapplication.repository

import com.example.testapplication.api.PlaceHolderComment
import com.example.testapplication.api.PlaceHolderPost
import io.reactivex.Single

interface PostRepository {
    fun getPosts(): Single<List<PlaceHolderPost>>
    fun getComments(id: Int): Single<List<PlaceHolderComment>>
}