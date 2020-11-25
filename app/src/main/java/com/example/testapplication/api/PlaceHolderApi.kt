package com.example.testapplication.api

import io.reactivex.Single
import retrofit2.http.GET

interface PlaceHolderApi {
    @GET("users")
    fun getUsers(): Single<PlaceHolderUser>
}