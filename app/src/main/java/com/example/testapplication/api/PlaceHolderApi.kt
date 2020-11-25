package com.example.testapplication.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface PlaceHolderApi {
    @GET("users")
    fun getUsers(): Single<List<PlaceHolderUser>>

    @GET("posts")
    fun getPosts(): Single<List<PlaceHolderPost>>

    @GET("posts/{id}/comments")
    fun getComments(@Path("id") id: Int): Single<List<PlaceHolderComment>>
}