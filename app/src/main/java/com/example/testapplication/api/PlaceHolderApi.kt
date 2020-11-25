package com.example.testapplication.api

import io.reactivex.Single
import retrofit2.http.GET

interface PlaceHolderApi {
    @GET("users")
    fun getUsers(): Single<List<PlaceHolderUser>>

    @GET("posts")
    fun getPosts(): Single<List<PlaceHolderPost>>
}